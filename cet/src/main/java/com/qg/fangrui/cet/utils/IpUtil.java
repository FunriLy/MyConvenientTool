package com.qg.fangrui.cet.utils;

import com.qg.fangrui.cet.model.IpMessage;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;

/**
 * IP 工具类
 * @author FunriLy
 * Created by FunriLy on 2018/1/4.
 * From small beginnings comes great things.
 */
public class IpUtil {

    /**
     * 测试此IP是否有效
     * @param ipMessages 有效的 IP 集合
     */
    public static void ipIsable(List<IpMessage> ipMessages) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        for(int i = 0; i < ipMessages.size(); i++) {
            String ip = ipMessages.get(i).getIpAddress();
            String port = ipMessages.get(i).getIpPort();

            HttpHost proxy = new HttpHost(ip, Integer.parseInt(port));
            RequestConfig config = RequestConfig.custom().setProxy(proxy)
                    .setConnectTimeout(Constant.TEST_CONNECT_TIMEOUT_LIMIT).
                    setSocketTimeout(Constant.TEST_SOCKET_TIMEOUT_LIMIT).build();
            HttpGet httpGet = new HttpGet("https://www.baidu.com");
            httpGet.setConfig(config);

            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
                    "q=0.9,image/webp,*/*;q=0.8");
            httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit" +
                    "/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

            try {
                response = httpClient.execute(httpGet);
            } catch (IOException e) {
                System.out.println("不可用代理已删除" + ipMessages.get(i).getIpAddress()
                        + ": " + ipMessages.get(i).getIpPort());
                ipMessages.remove(ipMessages.get(i));
                i--;
            }
        }

        close(httpClient, response);
    }

    public static void close(CloseableHttpClient httpClient, CloseableHttpResponse response) {
        try {
            if (httpClient != null) {
                httpClient.close();
            }
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
