package com.qg.fangrui.cet;

import com.qg.fangrui.cet.database.MyIpPool;

/**
 * 测试代理IP爬取
 * @author FunriLy
 * Created by FunriLy on 2018/1/4.
 * From small beginnings comes great things.
 */
public class IpMain {

    public static void main(String[] args) {
        new MyIpPool().run();
    }
}
