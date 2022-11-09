package cn.com.pzliu.zookeeper.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Naive
 * @date 2022-11-09 21:18
 */
@ConfigurationProperties(prefix = "zookeeper")
@Configuration
@Data
public class ZookeeperConfig {

    private String connectionStr;

    private Integer timeOutMs;

    private Integer retryMs;

    private Integer retryTimes;

    private String namespace;

}
