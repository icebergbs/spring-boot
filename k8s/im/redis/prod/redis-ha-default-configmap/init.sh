HOSTNAME="$(hostname)"
INDEX="${HOSTNAME##*-}"
MASTER="$(redis-cli -h redis-ha-default -p 26379 sentinel get-master-addr-by-name mymaster | grep -E '[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}')"
MASTER_GROUP="mymaster"
QUORUM="2"
REDIS_CONF=/data/conf/redis.conf
REDIS_PORT=6379
SENTINEL_CONF=/data/conf/sentinel.conf
SENTINEL_PORT=26379
SERVICE=redis-ha-default
set -eu

sentinel_update() {
    echo "Updating sentinel config with master $MASTER"
    eval MY_SENTINEL_ID="\${SENTINEL_ID_$INDEX}"
    sed -i "1s/^/sentinel myid $MY_SENTINEL_ID\\n/" "$SENTINEL_CONF"
    sed -i "2s/^/sentinel monitor $MASTER_GROUP $1 $REDIS_PORT $QUORUM \\n/" "$SENTINEL_CONF"
    echo "sentinel announce-ip $ANNOUNCE_IP" >> $SENTINEL_CONF
    echo "sentinel announce-port $SENTINEL_PORT" >> $SENTINEL_CONF
}

redis_update() {
    echo "Updating redis config"
    echo "slaveof $1 $REDIS_PORT" >> "$REDIS_CONF"
    echo "slave-announce-ip $ANNOUNCE_IP" >> $REDIS_CONF
    echo "slave-announce-port $REDIS_PORT" >> $REDIS_CONF
}

copy_config() {
    cp /readonly-config/redis.conf "$REDIS_CONF"
    cp /readonly-config/sentinel.conf "$SENTINEL_CONF"
}

setup_defaults() {
    echo "Setting up defaults"
    if [ "$INDEX" = "0" ]; then
        echo "Setting this pod as the default master"
        redis_update "$ANNOUNCE_IP"
        sentinel_update "$ANNOUNCE_IP"
        sed -i "s/^.*slaveof.*//" "$REDIS_CONF"
    else
        DEFAULT_MASTER="$(getent hosts "$SERVICE-announce-0" | awk '{ print $1 }')"
        if [ -z "$DEFAULT_MASTER" ]; then
            echo "Unable to resolve host"
            exit 1
        fi
        echo "Setting default slave config.."
        redis_update "$DEFAULT_MASTER"
        sentinel_update "$DEFAULT_MASTER"
    fi
}

find_master() {
    echo "Attempting to find master"
    if [ "$(redis-cli -h "$MASTER" -a "$AUTH" ping)" != "PONG" ]; then
       echo "Can't ping master, attempting to force failover"
       if redis-cli -h "$SERVICE" -p "$SENTINEL_PORT" sentinel failover "$MASTER_GROUP" | grep -q 'NOGOODSLAVE' ; then
           setup_defaults
           return 0
       fi
       sleep 10
       MASTER="$(redis-cli -h $SERVICE -p $SENTINEL_PORT sentinel get-master-addr-by-name $MASTER_GROUP | grep -E '[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}')"
       if [ "$MASTER" ]; then
           sentinel_update "$MASTER"
           redis_update "$MASTER"
       else
          echo "Could not failover, exiting..."
          exit 1
       fi
    else
        echo "Found reachable master, updating config"
        sentinel_update "$MASTER"
        redis_update "$MASTER"
    fi
}

mkdir -p /data/conf/

echo "Initializing config.."
copy_config

ANNOUNCE_IP=$(getent hosts "$SERVICE-announce-$INDEX" | awk '{ print $1 }')
if [ -z "$ANNOUNCE_IP" ]; then
    "Could not resolve the announce ip for this pod"
    exit 1
elif [ "$MASTER" ]; then
    find_master
else
    setup_defaults
fi

if [ "${AUTH:-}" ]; then
    echo "Setting auth values"
    ESCAPED_AUTH=$(echo "$AUTH" | sed -e 's/[\/&]/\\&/g');
    sed -i "s/replace-default-auth/${ESCAPED_AUTH}/" "$REDIS_CONF" "$SENTINEL_CONF"
fi

echo "Ready..."