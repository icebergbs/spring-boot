package com.bingshan.springboot.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

/**
 * 在Info端点中暴露该应用的构建时间
 * @author bingshan
 * @date 2022/10/30 22:07
 */
@Component
public class CustomBuildInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("build",
                Collections.singletonMap("timestamp", new Date()));
    }
}
