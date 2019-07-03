package com.kavin.redis.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class ClusterProperties {

    private String nodes;
    private String max_redirects;

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getMax_redirects() {
        return max_redirects;
    }

    public void setMax_redirects(String max_redirects) {
        this.max_redirects = max_redirects;
    }


}
