package com.li.middleware;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

public class rabbit {
    /*创建交换机*/
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("TemplateDirectEx", false, false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("TemplateFanoutEx", false, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("TemplateTopicEx", false, false);
    }

    /*创建队列*/
    @Bean
    public Queue directQueue1() {
        return new Queue("directQueue1", true);
    }

    @Bean
    public Queue directQueue2() {
        return new Queue("directQueue2", true);
    }

    @Bean
    public Queue topicQueue1() {
        return QueueBuilder.durable("topicQueue1").build();
    }

    @Bean
    public Queue topicQueue2() {
        return QueueBuilder.durable("topicQueue2").build();
    }

    /*创建绑定关系方法一*/
    @Bean
    public Binding directBind1() {
        return new Binding("directQueue1", Binding.DestinationType.QUEUE,
                "TemplateDirectEx", "WeiXin", null);
    }

    @Bean
    public Binding directBind2() {
        return BindingBuilder.bind(new Queue("directQueue2", false))
                .to(new DirectExchange("TemplateDirectEx"))
                .with("WeiXin");
    }


    /*创建绑定关系方法二
     *   将Bean方法名称作为参数代入*/
    @Bean
    public Binding topicBind1(@Qualifier("topicQueue1") Queue queue,
                              @Qualifier("topicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("user.#");
    }

    @Bean
    public Binding topicBind2(@Qualifier("topicQueue2") Queue queue,
                              @Qualifier("topicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("vip.*");
    }
}
