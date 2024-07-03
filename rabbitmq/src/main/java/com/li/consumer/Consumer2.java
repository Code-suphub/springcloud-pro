package com.li.consumer;

import com.li.util.RabbitUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/*HelloWord模型 消费者案例*/
public class Consumer2 {

    public static final String QUEUE_NAME = "hello";  // 队列名称

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = RabbitUtils.getConnection();      //创建连接

        Channel channel = connection.createChannel();             //创建信道

        /*消费者成功消费时的回调接口，这里为打印获取到的消息*/
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("消费者B对消息进行消费!");
            try {
                TimeUnit.SECONDS.sleep(10);  //模拟实际业务操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("消费者B接收到的信息为:" + new String(message.getBody()));
            /*
             * 参数一：  消息标记tag
             * 参数二：  是否批量消费消息（true为应答该队列中所有的消息，false为只应答接收到的消息）
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);     //手动消息应答
//            /*
//             * 参数一：  消息标记tag
//             * 参数二：  是否批量消费消息（true为应答该队列中所有的消息，false为只应答接收到的消息）
//             * 参数三：  是否重回队列
//             * */
//            channel.basicNack(message.getEnvelope().getDeliveryTag(), false, false);  //拒绝消息应答方法1
//
//            /*
//             * 参数一：  消息标记tag
//             * 参数二：  是否重回队列
//             * */
//            channel.basicReject(message.getEnvelope().getDeliveryTag(), false);       //拒绝消息应答方法2
        };

        /*消费者取消消费的回调*/
        CancelCallback callback = consumerTag -> {
            System.out.println("消息者B取消消费接口回调逻辑");
        };

        System.out.println("消费者B等待接收消息......");

        channel.basicQos(1);
        /*  消费消息
         * 参数1 ： 消费队列的名称
         * 参数2 ： 消息的自动确认机制(一获得消息就通知 MQ 消息已被消费)  true打开，false关闭 (接收到消息并消费后也不通知 MQ ，常用)
         * 参数3 ： 消费者成功消费时的回调接口
         * 参数4 ： 消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, callback);       //消费消息

        System.out.println("消费者B执行完毕");

    }

}
