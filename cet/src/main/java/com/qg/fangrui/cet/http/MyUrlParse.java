package com.qg.fangrui.cet.http;

import com.qg.fangrui.cet.model.IpMessage;
import com.qg.fangrui.cet.utils.Constant;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * 爬取代理IP
 * @author FunriLy
 * Created by FunriLy on 2017/12/29.
 * From small beginnings comes great things.
 */
public class MyUrlParse {

    /**
     * 利用本机IP爬取代理IP
     * @param ipLists 代理IP列表
     * @return 可用代理IP列表
     */
    public static List<IpMessage> urlParse(List<IpMessage> ipLists) {
        System.out.println("利用本机IP爬取代理IP");
        return urlParse(ipLists, 1, null, 0);
    }

    /**
     * 利用代理IP爬取代理IP
     * @param ipLists 本地代理IP列表
     * @param num 页数
     * @return 更新后的代理IP列表
     */
    public static List<IpMessage> urlParse(List<IpMessage> ipLists, int num, String host, int port) {
        String html = getHtml(Constant.XICI_URL + num, host, port);
        // 解析为DOM结构
        Document document = Jsoup.parse(html);
        // 提取数据
        Elements elements = document.select("table[id=ip_list]").select("tbody").select("tr");

        for (int i=1; i<elements.size(); i++) {
            Element element = elements.get(i);
            IpMessage ipMessage = new IpMessage();
            // 提取
            ipMessage.setIpAddress(element.select("td").get(1).text());
            ipMessage.setIpPort(element.select("td").get(2).text());
            ipMessage.setIpType(element.select("td").get(5).text());
            ipMessage.setIpSpeed(element.select("td").get(6).select("div[class=bar]").attr("title"));

            ipLists.add(ipMessage);
        }

        return ipLists;
    }

    /**
     * 利用IP利用Get请求爬取网页
     * @param url 访问URL
     * @param host 代理IP host
     * @param port 代理IP port
     * @return 网页内容
     */
    private static String getHtml(String url, String host, int port) {
        String entity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置超时处理
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(Constant.IP_CONNECT_TIMEOUT_LIMIT).
                setSocketTimeout(Constant.IP_SOCKET_TIMEOUT_LIMIT).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);

        if (host != null && port != 0) {
            HttpHost proxy = new HttpHost(host, port);
            RequestConfig configIP = RequestConfig.custom().setProxy(proxy).build();
            httpGet.setConfig(configIP);
        }

        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
                "q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Cache-Control", "no-cache");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Host", "www.xicidaili.com");
        httpGet.setHeader("Pragma", "no-cache");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            //得到服务响应状态码
            if (httpResponse.getStatusLine().getStatusCode() == Constant.SUCCESS_STATUS) {
                entity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            }

            httpResponse.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entity;
    }
}
