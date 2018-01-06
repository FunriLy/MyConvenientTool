package com.qg.fangrui.cet.utils;

/**
 * 常量类
 * @author FunriLy
 * Created by FunriLy on 2018/1/6.
 * From small beginnings comes great things.
 */
public interface Constant {
    /**
     * IP 过滤规则 : 必须是 https 连接
     */
    String HTTPS = "HTTPS";
    /**
     * IP 过滤规则 : 速度限制
     */
    double SPEED = 2.0;
    /**
     * 代理爬取网站
     */
    String XICI_URL = "http://www.xicidaili.com/nn/";
    /**
     * 访问成功标准
     */
    int SUCCESS_STATUS = 200;
    /**
     * 四六级查询连接超时 : 10s
     */
    int CET_CONNECT_TIMEOUT_LIMIT = 1000;
    /**
     * 四六级查询读取超时 : 10s
     */
    int CET_SOCKET_TIMEOUT_LIMIT = 1000;
    /**
     * 代理IP连接超时 : 10s
     */
    int IP_CONNECT_TIMEOUT_LIMIT = 1000;
    /**
     * 代理IP会话超时 : 10s
     */
    int IP_SOCKET_TIMEOUT_LIMIT = 1000;
    /**
     * 测试IP连接超时 : 8s
     */
    int TEST_CONNECT_TIMEOUT_LIMIT = 800;
    /**
     * 测试IP会话超时 : 8s
     */
    int TEST_SOCKET_TIMEOUT_LIMIT = 800;
}
