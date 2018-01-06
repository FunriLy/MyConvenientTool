package com.qg.fangrui.cet.database;

import lombok.AllArgsConstructor;

/**
 * IP爬取线程
 * @author FunriLy
 * Created by FunriLy on 2018/1/5.
 * From small beginnings comes great things.
 */
@AllArgsConstructor
public class IpThread extends Thread {

    private IpPool pool;

    @Override
    public void run() {
        pool.run();
    }
}
