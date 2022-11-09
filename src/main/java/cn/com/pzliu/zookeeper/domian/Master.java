package cn.com.pzliu.zookeeper.domian;

import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Naive
 * @date 2022-11-09 20:39
 */
@Component
public abstract class Master implements Runnable, CuratorWatcher {

    @Autowired
    private MasterWorker masterWorker;


    @Override
    public void process(WatchedEvent event) throws Exception {
        if (event.getType().equals(Watcher.Event.EventType.NodeDeleted)) {
            // Master节点被删除了
            masterWorker.createWorker();
        }
    }
}
