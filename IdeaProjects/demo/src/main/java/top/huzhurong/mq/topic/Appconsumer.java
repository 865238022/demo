package top.huzhurong.topicMq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by 竹 on 2017/10/13.
 */
public class Appconsumer {
    private static final String URL = "tcp://localhost:61616";
    private static final String topicName = "topic-test";
    public static void main(String[] args) throws JMSException {
        //1.创建ConnectionFactory
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(URL);
        //2.创建链接
        Connection connection = activeMQConnectionFactory.createConnection();
        //3.启动链接
        connection.start();
        //4.创建回话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建一个目标
        Destination destination = session.createTopic(topicName);
        //6.创建一个消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //7.创建一个监听器
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("接受的消息："+textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        //8. 关闭链接
//        connection.close();

    }
}
