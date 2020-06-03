package com.nc.config;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
@EnableConfigurationProperties(PayProperties.class)
public class PayConfig implements WXPayConfig {

    @Autowired
    private PayProperties payProperties;


    public String getAppID() {
        return payProperties.getAppId();
    }

    public String getMchID() {
        return payProperties.getMchId();
    }

    public String getKey() {
        return payProperties.getKey();
    }

    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return payProperties.getConnectTimeoutMs();
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return payProperties.getReadTimeoutMs();
    }

}