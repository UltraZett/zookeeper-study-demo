package cn.com.pzliu.zookeeper.manager;

import cn.com.pzliu.zookeeper.config.ZookeeperConfig;
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

    private final ZookeeperConfig config;
    @Getter
    private CuratorFramework client;

    public ZookeeperManager(ZookeeperConfig config) {
        this.config = config;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.client = CuratorFrameworkFactory.builder()
                .connectString(config.getConnectionStr())
                .connectionTimeoutMs(config.getTimeOutMs())
                .retryPolicy(new ExponentialBackoffRetry(config.getRetryMs(), config.getRetryTimes()))
                .namespace(config.getNamespace())
                .build();
        this.client.start();
    }

    @Override
    public void destroy() throws Exception {
        if (client != null){
            client.close();
        }
    }
}
