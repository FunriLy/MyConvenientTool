package com.qg.fangrui.cet.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 标签根据类
 * @author FunriLy
 * Created by FunriLy on 2017/12/28.
 * From small beginnings comes great things.
 */
public class TagUtil {

    /**
     * 定义script的正则表达式
     */
    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    /**
     * 定义style的正则表达式
     */
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    /**
     * 定义HTML标签的正则表达式
     */
    private static final String REGEX_HTML = "<[^>]+>";
    /**
     * 定义空格回车换行符
     */
    private static final String REGEX_SPACE = "\\s*|\t|\r|\n";
    /**
     * 定义所有w标签
     */
    private static final String REGEX_W = "<w[^>]*?>[\\s\\S]*?<\\/w[^>]*?>";
    /**
     * 定义一些特殊字符
     */
    private static final String REGEX_SPECIAL = "\\&[a-zA-Z]{1,10};";

    public static String delHTMLTag(String htmlStr) {
        Pattern pW = Pattern.compile(REGEX_W, Pattern.CASE_INSENSITIVE);
        Matcher mW = pW.matcher(htmlStr);
        // 过滤script标签
        htmlStr = mW.replaceAll("");


        Pattern pScript = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher mScript = pScript.matcher(htmlStr);
        // 过滤script标签
        htmlStr = mScript.replaceAll("");


        Pattern pStyle = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher mStyle = pStyle.matcher(htmlStr);
        // 过滤style标签
        htmlStr = mStyle.replaceAll("");


        Pattern pHtml = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher mHtml = pHtml.matcher(htmlStr);
        // 过滤html标签
        htmlStr = mHtml.replaceAll("");


        Pattern pSpace = Pattern.compile(REGEX_SPACE, Pattern.CASE_INSENSITIVE);
        Matcher mSpace = pSpace.matcher(htmlStr);
        // 过滤空格回车标签
        htmlStr = mSpace.replaceAll("");

        Pattern pSpecial = Pattern.compile(REGEX_SPECIAL, Pattern.CASE_INSENSITIVE);
        Matcher mSpecial = pSpecial.matcher(htmlStr);
        // 过滤特殊字符
        htmlStr = mSpecial.replaceAll("");

        //过滤
        htmlStr = htmlStr.replaceAll(" ", "");
        // 返回文本字符串
        return htmlStr.trim();
    }
}
