package com.qg.fangrui.cet.database;

import com.qg.fangrui.cet.filter.IpFilter;
import com.qg.fangrui.cet.http.MyUrlParse;
import com.qg.fangrui.cet.model.IpMessage;
import com.qg.fangrui.cet.utils.IpUtil;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * IP代理池
 * @author Funrily
 * Created by FunriLy on 2017/12/28.
 * From small beginnings comes great things.
 */
@AllArgsConstructor
public class IpPool {

    /**
     * 存放代理IP队列
     */
    private  List<IpMessage> ipLists;
    /**
     * 页数
     */
    private static AtomicInteger num = new AtomicInteger(2);


    /**
     * 调用方法
     */
    public void run() {
        createIpPool();
    }

    /**
     * 构造IP代理池
     */
    private void createIpPool() {

        List<IpMessage> list = new ArrayList<>();

        int number = num.getAndIncrement();
        System.out.println("爬取第 " + number + " 页代理IP");


        IpMessage ip = getIP();
        // 从第二页开始爬取，第一页已经用本机IP爬取过
        list = MyUrlParse.urlParse(list, number+1, ip.getIpAddress(), Integer.valueOf(ip.getIpPort()));

        if (list == null) {
            System.out.println("爬取代理失败，本线程强制结束");
            return ;
        } else {
            // 对ip进行过滤
            list = IpFilter.filter(list);
            IpUtil.ipIsable(list);
        }

        synchronized (this) {
            list.forEach(message -> ipLists.add(message));
        }
    }

    /**
     * 获取一个代理IP类
     * 关于代理IP的获取策略，暂时采用随机
     * @return 一个具体IP类
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    private IpMessage getIP() {
        int rand = (int) (Math.random() * ipLists.size());
        IpMessage message = ipLists.get(rand);
        return message;
    }

}
