package com.qg.fangrui.cet;

import com.qg.fangrui.cet.database.MyIpPool;
import com.qg.fangrui.cet.http.MyHttpClient;
import com.qg.fangrui.cet.model.Cet;
import com.qg.fangrui.cet.model.IpMessage;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行主类
 * @author FunriLy
 * Created by FunriLy on 2017/12/28.
 * From small beginnings comes great things.
 */
public class Main {
    public static void main(String[] args) {
        MyIpPool ipPool = new MyIpPool();
        List<Thread> threadList = new ArrayList<>();
        ipPool.run();
        int num = 10;
        for (int i=0; i<num; i++) {
            Thread cetThread = new CetThread(ipPool, 0);
            threadList.add(cetThread);
            cetThread.start();
        }
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Demo 运行结束");
    }


}

@AllArgsConstructor
class CetThread extends Thread {
    MyIpPool pool;
    int time;

    @Override
    public void run() {
        if(time <= 0) {
            // 若试探次数为负数或0
            time = 10;
        }
        IpMessage ipMessage = pool.getIP();
        while (time > 0) {
            Cet cet = MyHttpClient.getCetScore("准考证号", "姓名", 0, ipMessage);
            if (cet.getResult() != null) {
                System.out.println("IP:"+ipMessage.getIpAddress()+" port:"+ipMessage.getIpPort()+" time:"+time
                        + "\n" + cet.toString()
                );
                break;
            }
            ipMessage = pool.getIP();
            time--;
        }
    }
}
