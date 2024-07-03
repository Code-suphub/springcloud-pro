package com.li;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@SpringBootTest
class RabbitMqBootRabbitTemplateApplicationTests {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /*RabbitTemplate的API案例*/
    @Test
    public void testTemplate() {

        /*创建消息，可以指定消息具体参数*/
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc", "请求头desc参数信息描述");
        messageProperties.getHeaders().put("type", "请求头type参数信息描述");
        messageProperties.setContentType("application/json");       //发送格式
        messageProperties.setContentEncoding("UTF-8");              //UTF-8格式化

        /*封装消息
         * 参数一：消息内容
         * 参数二：消息配置
         */
        Message message = new Message("这是RabbitTemplate消息".getBytes(StandardCharsets.UTF_8), messageProperties);

        /* MessagePostProcessor：发送消息前的消息拦截器
         *    可以对消息参数进行修改，例如设置优先级、请求头等
         */
        rabbitTemplate.convertAndSend("TemplateDirectEx", "WeiXin", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                System.out.println("拦截需要发送的消息并进行二次设置");
                message.getMessageProperties().getHeaders().put("desc", "请求头desc参数信息修改");
                message.getMessageProperties().getHeaders().put("attr", "请求头额外新加attr参数");
                return message;
            }
        });


        /*创建消息，使用链式调用*/
        Message message2 = MessageBuilder.withBody("这是Template消息2".getBytes(StandardCharsets.UTF_8))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setMessageId("消息ID:" + UUID.randomUUID())
                .setContentEncoding("UTF-8")
                .setHeader("desc", "额外修改的信息描述")
                .setHeader("info", "请求头参数2")
                .build();

        rabbitTemplate.convertAndSend("TemplateTopicEx", "user.student", message2);


        /*最简单的调用方式*/
        rabbitTemplate.convertAndSend("TemplateTopicEx", "vip.student", "我是最简单的消息!");
        rabbitTemplate.send("TemplateTopicEx", "user.teacher.aa", message2);


    }

}
