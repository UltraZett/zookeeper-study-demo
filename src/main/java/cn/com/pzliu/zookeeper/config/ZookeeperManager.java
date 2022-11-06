package cn.com.pzliu.zookeeper.config;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ZookeeperManager implements InitializingBean, DisposableBean {


    private String connectionStr;

    private Integer timeOutMs;

    private Integer retryMs;

    private Integer retryTimes;

    private String namespace;
    @Getter
    private CuratorFramework client;



    @Override
    public void afterPropertiesSet() throws Exception {
        this.client = CuratorFrameworkFactory.builder()
                .connectString(connectionStr)
                .connectionTimeoutMs(timeOutMs)
                .retryPolicy(new ExponentialBackoffRetry(retryMs,retryMs))
                .namespace(StringUtils.isEmpty(namespace)?"":namespace)
                .build();
    }

    @Override
    public void destroy() throws Exception {
        if (client != null){
            client.close();
        }
    }
}
