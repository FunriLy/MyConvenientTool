package com.qg.fangrui.cet.filter;

import com.qg.fangrui.cet.model.IpMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * IP 过滤器 : 对得到的IP进行筛选，将IP速度在两秒以内的并且类型是https的留下
 * @author FunriLy
 * Created by FunriLy on 2018/1/4.
 * From small beginnings comes great things.
 */
public class IpFilter {

    private static final String HTTPS = "HTTPS";
    private static final double SPEED = 2.0;

    public static List<IpMessage> filter(List<IpMessage> ipMessages) {
        List<IpMessage> newIPMessages = new ArrayList<>();

        for (IpMessage message : ipMessages) {
            String ipType = message.getIPType();
            String ipSpeed = message.getIPSpeed();

            ipSpeed = ipSpeed.substring(0, ipSpeed.indexOf('秒'));
            double speed = Double.parseDouble(ipSpeed);

            if (ipType.equals(HTTPS) && speed <= SPEED) {
                newIPMessages.add(message);
            }
        }

        return newIPMessages;
    }
}
