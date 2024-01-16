package com.xhh.smalldemobackend.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class RestTemplateConfig {

    /**
     * 管理连接池
     * @return
     */
    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() throws Exception{
        
        // 信任全部主机名
        HostnameVerifier hostnameVerifier = new HostnameVerifier(){

            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };

        // 注册http和https请求
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", new SSLConnectionSocketFactory(createIgnoreVerifySSL(), hostnameVerifier))
                .build();

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        // 连接池最大连接数
        poolingHttpClientConnectionManager.setMaxTotal(200);
        // 每个路由的最大并发数（即允许同一个host:port最多有几个活跃连接）
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(200);

        /**
         * 指定某个路由的最大连接数，示例：
         * HttpHost localhost = new Host("www.baidu.com", 80)
         * poolingHttpClientConnectionManager.setMaxPerRoute(new HttpRoute(localhost), 50)
         */
        //在从连接池获取连接时，连接不活跃多长时间后需要进行一次验证，默认为2s
        //poolingHttpClientConnectionManager.setValidateAfterInactivity(2_000);
        //自定义线程，用于关闭池中失效的http连接
        Thread evictThread = new Thread(new IdleConnectionEvictor(poolingHttpClientConnectionManager));
        evictThread.setDaemon(true);
        evictThread.start();
        return poolingHttpClientConnectionManager;
    }


    private SSLContext createIgnoreVerifySSL() throws Exception{
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

        X509TrustManager x509TrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
        return sslContext;
    }


    /**
     * 使用连接池进行http请求
     * @param httpClientConnectionManager
     * @return
     */
    @Bean
    //@Scope("prototype") 每次@Autowired都是新的httpClient, 新的连接池（一个HttpClient对应一个连接池）
    public HttpClient httpClient(HttpClientConnectionManager httpClientConnectionManager) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager)
                // httpClient共享模式，指是否和其他httpClient共享
                .setConnectionManagerShared(false)
                // 回收过期连接
                .evictExpiredConnections()
                // 回收空闲连接,即回收所有空闲时间超过10s的连接，如果一个连接在10s内没有使用过，那么就是空闲的
                .evictIdleConnections(10, TimeUnit.SECONDS)
                // 连接存活时间，如果不设置，则根据长连接信息决定，设置连接在连接池中保持空闲的最长时间
                .setConnectionTimeToLive(20, TimeUnit.SECONDS)
                // 长连接keep-alive设置,用于定义客户端与服务器之间的连接保持活跃的时间。它决定了在每次就接收到服务器的响应后客户端是否继续保持连接打开。
                // 建议和setConnectionTimeToLive保持相同时间
                .setKeepAliveStrategy((response, context) -> Duration.ofSeconds(20).toMillis())
                
                
                
                // 禁用重试次数,重试需要保证接口幂等性。retryCount表示最大的重试次数，requestSentRetryEnabled表示是否仅对幂等请求进行重试。一般情况下GET、HEAD、DELETE、OPTIONS是幂等请求
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));

        // 设置默认请求头
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Connection", "Keep-Alive"));
        httpClientBuilder.setDefaultHeaders(headers);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(500)
                .setSocketTimeout(1000)
                .build();
        
        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        // jvm停止或者重启时，关闭连接池释放连接
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                try {
                    log.info("ShutdownHook ---> closing httpClient : {}", closeableHttpClient.toString());
                    closeableHttpClient.close();
                    log.info("ShutdownHook ---> closed httpClient");
                } catch (Exception e) {
                    log.error("ShutdownHook closing http client error", e);
                }
            }
        });


        return closeableHttpClient;
    }

    /**
     * 定义如何创建http请求
     * @param httpClient
     * @return
     */
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        // httpClient创建器 setConnectTimeout setReadTimeout 被httpClient的RequestConfig覆盖
        httpComponentsClientHttpRequestFactory.setHttpClient(httpClient);
        // 连接超时时间/毫秒（连接上服务器（握手成功）时间，超出抛出connection timeout）
        httpComponentsClientHttpRequestFactory.setConnectTimeout(500);
        // 数据读取超时时间(socketTimeout)/毫秒，（服务器返回数据response的时间，超出抛出read timeout）
        httpComponentsClientHttpRequestFactory.setReadTimeout(1000);
        // 连接池获取请求连接的超时时间，不宜过长，必须设置/毫秒（超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException:）
        httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(1000);
        return httpComponentsClientHttpRequestFactory;
    }

    @Bean
    //@Scope("prototype") 配合setConnectionManagerShared(false)使用，这样每次注入的restTemplate都是新的实例
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }


    @Slf4j
    public static class IdleConnectionEvictor extends Thread {
        private final HttpClientConnectionManager connectionManager;
        private volatile boolean stop;

        public IdleConnectionEvictor(HttpClientConnectionManager connectionManager) {
            this.connectionManager = connectionManager;
        }

        @Override
        public void run() {
            try {
                while (!stop) {
                    synchronized (this) {
                        wait(5000);
                        log.debug("IdleConnectionEvictor ---> 5s执行一次，关闭过期和空闲的http连接");
                        // 关闭失效连接
                        connectionManager.closeExpiredConnections();
                        // 关闭空闲连接
                        connectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException exception) {
                log.error("IdleConnectionEvictor thread stop error: ", exception);
                shutdown();
            }
        }

        public void shutdown() {
            stop = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }
}
