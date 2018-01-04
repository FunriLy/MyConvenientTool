package com.qg.fangrui.cet;

import com.qg.fangrui.cet.database.IpPool;

import java.util.ArrayList;

/**
 * Created by FunriLy on 2018/1/4.
 * From small beginnings comes great things.
 */
public class IpMain {

    public static void main(String[] args) {
        IpPool pool = new IpPool(new ArrayList<>());

        System.out.println(pool.getIP());
    }
}
