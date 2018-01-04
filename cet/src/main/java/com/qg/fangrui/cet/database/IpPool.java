package com.qg.fangrui.cet.database;

import com.qg.fangrui.cet.http.MyUrlParse;
import com.qg.fangrui.cet.model.IpMessage;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * IP代理池
 * @author Funrily
 * Created by FunriLy on 2017/12/28.
 * From small beginnings comes great things.
 */
@AllArgsConstructor
public class IpPool {

    private List<IpMessage> ipLists;

    /**
     * 构造IP代理池
     */
    private void createIpPool() {
        if (ipLists == null || ipLists.isEmpty()) {
            ipLists = new ArrayList<>();
            /*
            这里再次采用本地ip爬取代理ip
             */
            ipLists = MyUrlParse.urlParse(ipLists);
        }
    }

    /**
     * 获取一个代理IP类
     * @return
     */
    public IpMessage getIP() {
        if (ipLists == null || ipLists.isEmpty()) {
            createIpPool();
        }
        return ipLists.get(0);
    }
}
