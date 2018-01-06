package com.qg.fangrui.cet.database;

import com.qg.fangrui.cet.exception.MyException;
import com.qg.fangrui.cet.http.MyUrlParse;
import com.qg.fangrui.cet.model.IpMessage;
import com.qg.fangrui.cet.utils.IpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FunriLy
 * Created by FunriLy on 2018/1/5.
 * From small beginnings comes great things.
 */
public class MyIpPool {

    private static List<IpMessage> ipPool;

    /**
     * 爬取线程数
     */
    private static int threadNum = 2;

    public void run() {
        List<IpMessage> ipMessages = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        ipMessages = MyUrlParse.urlParse(ipMessages);

        IpUtil.ipIsable(ipMessages);

        // 第一页没有找到符合条件的IP
        if (ipMessages == null || ipMessages.isEmpty()) {
            System.out.println("代理网站无效！！！");
            throw new MyException("代理网站无效！！！");
        }

        threadNum = (ipMessages.size() < threadNum ? ipMessages.size() : threadNum);

        IpPool pool = new IpPool(ipMessages);
        for (int i=0; i<threadNum; i++) {
            Thread ipThread = new IpThread(pool);
            threads.add(ipThread);
            ipThread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(ipMessages.size());
        ipMessages.forEach(System.out :: println);
        ipPool = ipMessages;
    }

    /**
     * 随机获得一个IP
     * @return 获得一个可用代理IP
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    public IpMessage getIP() {
        int rand = (int) (Math.random() * ipPool.size());
        IpMessage message = ipPool.get(rand);
        return message;
    }
}
