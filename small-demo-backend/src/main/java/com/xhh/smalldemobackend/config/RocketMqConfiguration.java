package com.xhh.smalldemobackend.config;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.*;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.xhh.smalldemobackend.service.rocket.check.DemoLocalTransactionChecker;
import com.xhh.smalldemobackend.service.rocket.consumer.normal.MessageConsumerListener;
import com.xhh.smalldemobackend.service.rocket.consumer.order.OrderMessageConsumerListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class RocketMqConfiguration {
    
    @Resource
    private MessageConsumerListener messageListener;
    
    @Resource
    private DemoLocalTransactionChecker demoLocalTransactionChecker;
    
    @Resource
    private OrderMessageConsumerListener orderMessageConsumerListener;
    
    @Value("${rocketmq.accessKey}")
    private String accessKey;
    
    @Value("${rocketmq.secretKey}")
    private String secretKey;
    
    @Value("${rocketmq.nameSrvAddr}")
    private String nameSrvAddr;
    
    @Value("${rocketmq.topic}")
    private String msgTopic;
    
    @Value("${rocketmq.groupId}")
    private String groupId;


    public Properties getMqProperties() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, this.accessKey);
        properties.setProperty(PropertyKeyConst.SecretKey, this.secretKey);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, this.nameSrvAddr);
        return properties;
    }



    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean buildProducer() {
        ProducerBean producer = new ProducerBean();
        producer.setProperties(getMqProperties());
        return producer;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public OrderProducerBean buildOrderProducer() {
        OrderProducerBean orderProducerBean = new OrderProducerBean();
        orderProducerBean.setProperties(getMqProperties());
        return orderProducerBean;
    }


    // 事务发送
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public TransactionProducerBean buildTransactionProducer() {
        TransactionProducerBean producer = new TransactionProducerBean();
        producer.setProperties(getMqProperties());
        producer.setLocalTransactionChecker(demoLocalTransactionChecker);
        return producer;
    }


    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ConsumerBean buildConsumer() {
        ConsumerBean consumerBean = new ConsumerBean();
        //配置文件
        Properties properties = getMqProperties();
        properties.setProperty(PropertyKeyConst.GROUP_ID, groupId);
        //将消费者线程数固定为20个 20为默认值
        //properties.setProperty(PropertyKeyConst.ConsumeThreadNums, "20");
        consumerBean.setProperties(properties);
        //订阅关系
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<Subscription, MessageListener>();
        Subscription subscription = new Subscription();
        // topic根据业务去阿里云控制台创建
        subscription.setTopic(msgTopic);
        // tag可以在业务代码中指定
        //subscription.setExpression(mqConfig.getTag());
        // messageListener 是 MessageListener 的实现
        subscriptionTable.put(subscription, messageListener);
        //订阅多个topic如上面设置

        consumerBean.setSubscriptionTable(subscriptionTable);
        return consumerBean;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public OrderConsumerBean buildOrderConsumer() {
        OrderConsumerBean orderConsumerBean = new OrderConsumerBean();
        //配置文件
        Properties properties = getMqProperties();
        properties.setProperty(PropertyKeyConst.GROUP_ID, groupId);
        orderConsumerBean.setProperties(properties);
        //订阅关系
        Map<Subscription, MessageOrderListener> subscriptionTable = new HashMap<Subscription, MessageOrderListener>();
        Subscription subscription = new Subscription();
        subscription.setTopic("");
        // type 默认，可批量设置
        subscription.setExpression("tag");
        subscriptionTable.put(subscription, orderMessageConsumerListener);
        //订阅多个topic如上面设置

        orderConsumerBean.setSubscriptionTable(subscriptionTable);
        return orderConsumerBean;
    }
}
