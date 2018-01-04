package com.qg.fangrui.cet.http;

import com.qg.fangrui.cet.exception.MyException;
import com.qg.fangrui.cet.model.Cet;
import com.qg.fangrui.cet.utils.TagUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Http 连接类
 * @author FunriLy
 * Created by FunriLy on 2017/12/25.
 * From small beginnings comes great things.
 */
public class MyHttpClient {

    /**
     * 学信网查询网址
     */
    private static String URL = "http://www.chsi.com.cn/cet/query?zkzh=NUM&xm=NAME";

    /**
     * 查询四六级成绩
     * @param number 准考证号
     * @param name 姓名
     * @param time 替换次数
     * @return 四六级成绩查询结果
     */
    public static Cet getCetScore(String number, String name, int time) {
        if (time <= 0) {
            // 若试探次数为负数
            time = 10;
        }
        Cet cet = null;
        while (time > 0) {
            String host = "127.0.0.1";
            int port = 8080;
            cet = getCetScore(number, name, host, port);
            if (cet.getResult() != null) {
                break;
            }
            time--;
        }
        return cet;
    }

    /**
     * 查询四六级成绩
     * @param number 准考证号
     * @param name 姓名
     * @param host 代理ip
     * @param port 端口
     * @return 四六级成绩查询结果
     */
    private synchronized static Cet getCetScore(String number, String name, String host, int port) {
        URL = URL.replace("NAME", name);
        URL = URL.replace("NUM", number);

        Cet cet = new Cet();

        // 创建可关闭的HttpClient实例对象(新版本才可以)相当于创建了一个模拟浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 一般爬虫请求都用Get
        HttpGet httpGet = new HttpGet(URL);
        RequestConfig configDL = RequestConfig.custom().setConnectTimeout(1000)
                // 设置连接时间超时10秒断连
                .setSocketTimeout(1000)
                // 设置读取时间超时10秒断连
                .build();

        /*
        连接池 IP 替换
        注意 : 这里的ip与端口可能需要修改一下
         */
        HttpHost proxy = new HttpHost(host, port);
        RequestConfig configIP = RequestConfig.custom().setProxy(proxy).build();
        httpGet.setConfig(configDL);
        httpGet.setConfig(configIP);

        httpGet.setHeader("Accept", "image/png, image/svg+xml, image/jxr, image/*; q=0.8, */*; q=0.5");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Accept-Language", "zh-Hans-CN, zh-Hans; q=0.5");
        httpGet.setHeader("Connection", "Keep-Alive");
        httpGet.setHeader("Cookie", "AQAAAIhMp3BbugwAKRFC2uesy+2iO0Lw");
        httpGet.setHeader("Host", "www.chsi.com.cn");
        httpGet.setHeader("Referer", URL);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");

        CloseableHttpResponse respond = null;

        try {
            respond = httpClient.execute(httpGet);

            System.out.println("状态码 " + respond.getStatusLine().getStatusCode());

            HttpEntity entity = respond.getEntity();
            if (entity != null) {
                System.out.println("类型 " + entity.getContentType().getValue());
                // 获取我也信息
                String realHtml = EntityUtils.toString(entity, "UTF-8");
                // 去除 html 标签
                String html = TagUtil.delHTMLTag(realHtml);
                // 切取结果
                String result = html.substring(html.indexOf("成绩查询结果"), html.indexOf("口试成绩")).trim()
                        .replace("：", " ");
                // 拆分为数组
                String[] array = result.split(" ");

                cet.setName(array[1].replace("学校", ""));
                cet.setSchool(array[2].replace("考试级别", ""));
                cet.setType(array[3].replace("准考证号", ""));
                cet.setCertificateNumber(array[4].replace("总分", ""));
                cet.setResult(array[5].replace("听力", ""));
                cet.setListenScore(array[6].replace("阅读", ""));
                cet.setReadingScore(array[7].replace("写作和翻译", ""));
                cet.setTranslatingScore(array[8]);
            } else {
                System.out.println("html is null !!!");
            }

        } catch (HttpHostConnectException e) {
            // 代理IP无效，直接抛出异常
            System.out.println("代理ip无效 IP:" + host + " port:" + port);
        } catch (Exception e) {
            System.out.println("查询出错，请检查填写！");
            System.out.println("错误信息 : " + e.getMessage());
            throw new MyException("提写出错！！！");
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (respond != null) {
                    respond.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cet;
    }
}
