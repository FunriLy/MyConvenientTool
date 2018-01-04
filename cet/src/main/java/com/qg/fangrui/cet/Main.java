package com.qg.fangrui.cet;

import com.qg.fangrui.cet.http.MyHttpClient;

/**
 * 运行主类
 * @author FunriLy
 * Created by FunriLy on 2017/12/28.
 * From small beginnings comes great things.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(MyHttpClient.getCetScore("440380171230518", "方锐", 0));
    }
}
