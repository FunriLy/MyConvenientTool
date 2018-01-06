package com.qg.fangrui.cet.filter;

import com.qg.fangrui.cet.model.IpMessage;
import com.qg.fangrui.cet.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * IP 过滤器
 * @author FunriLy
 * Created by FunriLy on 2018/1/4.
 * From small beginnings comes great things.
 */
public class IpFilter {

    private static final String HTTPS = "HTTPS";
    private static final double SPEED = 2.0;

    /**
     * 对得到的IP进行筛选
     * @param ipMessages IP集合
     * @return 过滤后的IP集合
     */
    public static List<IpMessage> filter(List<IpMessage> ipMessages) {
       List<IpMessage> newIPMessages = new ArrayList<>();

        ipMessages.forEach(message -> {
            if (filter(message)) {
                newIPMessages.add(message);
            }
        });

        return newIPMessages;
    }

    /**
     * 筛选规则 ：将IP速度在两秒以内的并且类型是https的留下
     * @param message IP集合
     * @return 符合规则的IP集合
     */
    private static boolean filter(IpMessage message) {
        String ipType = message.getIpType();
        String ipSpeed = message.getIpSpeed();

        ipSpeed = ipSpeed.substring(0, ipSpeed.indexOf('秒'));
        double speed = Double.parseDouble(ipSpeed);

        return ipType.equals(Constant.HTTPS) && speed <= Constant.SPEED;
    }
}
