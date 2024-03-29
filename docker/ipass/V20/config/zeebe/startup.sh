#!/bin/bash -xeu

configFile=/usr/local/zeebe/conf/zeebe.cfg.toml
export ZEEBE_HOST=$(hostname -f)
export ZEEBE_NODE_ID="${HOSTNAME##*-}"

# We need to specify all brokers as contact points for partition healing to work correctly
# https://github.com/zeebe-io/zeebe/issues/2684
ZEEBE_CONTACT_POINTS=${HOSTNAME::-1}0.$(hostname -d):26502
for (( i=1; i<$ZEEBE_CLUSTER_SIZE; i++ ))
do
    ZEEBE_CONTACT_POINTS="${ZEEBE_CONTACT_POINTS},${HOSTNAME::-1}$i.$(hostname -d):26502"
done
export ZEEBE_CONTACT_POINTS="${ZEEBE_CONTACT_POINTS}"

exec /usr/local/zeebe/bin/broker