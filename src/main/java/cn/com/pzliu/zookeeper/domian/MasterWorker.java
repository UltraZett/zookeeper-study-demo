package cn.com.pzliu.zookeeper.domian;

import cn.com.pzliu.zookeeper.config.ZookeeperManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 *
 */
@Component
public class MasterWorker implements InitializingBean, DisposableBean {


    private ZookeeperManager zkClient;

    private String basePath;

    private String instanceInfo;

    public MasterWorker(ZookeeperManager zkClient) {
        this.zkClient = zkClient;
    }


    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String path = zkClient.getClient()
                .create()
                .forPath(this.basePath + "/master", instanceInfo.getBytes(StandardCharsets.UTF_8));
    }
}
