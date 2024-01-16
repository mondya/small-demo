package com.xhh.smalldemocommon.utils;


import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.util.Map;

@Slf4j
public class HttpClient3Util {

    /**
     * httpClient的get请求方式
     *
     * @param url
     * @param charset
     * @return
     */
    public static String doGet(String url, String charset) {
        HttpClient httpClient = new HttpClient(new SimpleHttpConnectionManager(true));

        // 设置http连接超时时间 5s
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

        GetMethod getMethod = new GetMethod(url);

        // 设置get请求超时时间 5s
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);

        // 设置请求重试次数，默认重试次数，3次
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        String response = "";

        try {
            int code = httpClient.executeMethod(getMethod);

            if (code != HttpStatus.SC_OK) {
                log.error("请求出错:{}", getMethod.getStatusLine());
            }

            Header[] responseHeaders = getMethod.getResponseHeaders();
            Map<String, String> headerMap = Maps.newHashMap();
            for (Header responseHeader : responseHeaders) {
                headerMap.put(responseHeader.getName(), responseHeader.getValue());
            }

            log.info("---------------------> get method 响应头部信息:{}", JSON.toJSONString(headerMap));

            response = getMethod.getResponseBodyAsString();
            // 在网页内容数据量大的时候推荐使用InputStream
            // InputStream inputStream = getMethod.getResponseBodyAsStream();
            log.info("-----response :{}", response);
        } catch (Exception e) {
            log.error("请求发生异常, e:", e);
        } finally {
            getMethod.releaseConnection();
        }

        return response;
    }

    public static String doPost(String url, String str) {

        HttpClient httpClient = new HttpClient(new SimpleHttpConnectionManager(true));
        PostMethod postMethod = new PostMethod(url);

        postMethod.addRequestHeader("accept", "*/*");
        postMethod.addRequestHeader("connection", "Keep-Alive");
        //设置json格式传送
        postMethod.addRequestHeader("Content-Type", "application/json;charset=GBK");
        //必须设置下面这个Header
        postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
        //添加请求参数
        postMethod.addParameter("commentId", str);

        String res = "";
        try {
            int code = httpClient.executeMethod(postMethod);
            if (code == 200) {
                res = postMethod.getResponseBodyAsString();
                System.out.println(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }

        return res;
    }


    public static void main(String[] args) {
        System.out.println(doGet("http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13767152962", "13767152962"));
    }
}
