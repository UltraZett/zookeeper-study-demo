package cn.com.pzliu.zookeeper.domian;

import cn.com.pzliu.zookeeper.manager.ZookeeperManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Component
@Slf4j
public class MasterWorker implements InitializingBean, DisposableBean {


    private final ZookeeperManager zkClient;

    @Lazy
    @Autowired
    private Master master;

    @Lazy
    @Autowired
    private Worker worker;

    private String basePath = "/demo";

    private String instanceInfo = "myComputer";

    private String masterPath;

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 60, TimeUnit.MINUTES, new ArrayBlockingQueue<>(5), r -> {
        Thread thread = new Thread(r);
        thread.setName("Master-Worker线程");
        return thread;
    },new ThreadPoolExecutor.DiscardPolicy());

    public MasterWorker(ZookeeperManager zkClient) {
        this.zkClient = zkClient;
        masterPath = this.basePath + "/master";
    }


    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        createWorker();
    }

    public void createWorker() throws Exception {
        if (createMasterNode()){
            log.debug("创建Master成功,注册监听器,启动Master流程");
            registerWatcher(this.master);
            this.threadPoolExecutor.execute(this.master);
        }else {
            log.debug("创建Worker成功,注册监听器,启动Worker流程");
            this.registerWatcher(this.worker);
            this.threadPoolExecutor.execute(this.worker);
        }
    }

    private boolean createMasterNode() throws Exception {
        String masterPath = this.basePath + "/master";
        String path = zkClient.getClient()
                .create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(masterPath, instanceInfo.getBytes(StandardCharsets.UTF_8));
        log.debug("创建节点:{}完成,创建结果:{}",this.masterPath,path);
        Stat state = zkClient.getClient()
                .checkExists()
                .forPath(masterPath);

        return Objects.nonNull(state);
    }

    private void registerWatcher(CuratorWatcher curatorWatcher) throws Exception {
        zkClient.getClient()
                .getData()
                .usingWatcher(curatorWatcher)
                .forPath(this.masterPath);
    }

}
