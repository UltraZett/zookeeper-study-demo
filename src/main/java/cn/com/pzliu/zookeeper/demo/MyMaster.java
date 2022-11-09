package cn.com.pzliu.zookeeper.demo;

import cn.com.pzliu.zookeeper.domian.Master;
import cn.com.pzliu.zookeeper.domian.MasterWorker;
import org.springframework.stereotype.Component;

/**
 * @author Naive
 * @date 2022-11-09 21:34
 */
@Component
public class MyMaster extends Master {

    @Override
    public void run() {
        System.out.println("启用Master线程");
        System.out.println(Thread.currentThread().getName());
    }
}
