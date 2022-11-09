package cn.com.pzliu.zookeeper.demo;

import cn.com.pzliu.zookeeper.domian.MasterWorker;
import cn.com.pzliu.zookeeper.domian.Worker;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

/**
 * @author Naive
 * @date 2022-11-09 21:33
 */
@Component
public class MyWorker extends Worker {
    @Override
    public void run() {
        System.out.println("启动Worker线程");
    }
}
