package top.huzhurong.queueMq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by 竹 on 2017/10/13.
 * 使用队列模式，几个消费者会瓜分生产者提交的消息，而不是每一个都能完整的得到所有的消息
 * 如果想要每一个消费者都能得到对应的消息的话，那么就必须使用主题模式
 */
public class AppProducer {
    private static final String URL = "tcp://localhost:61616";
    private static final String queueName = "queue-test";

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
        Destination destination = session.createQueue(queueName);
        //6.创建一个生产者
        MessageProducer producer = session.createProducer(destination);

        for (int i=0;i<100;i++){
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("test" + i);
            //8.发布消息
            producer.send(textMessage);
            System.out.println("消息发送成功" + textMessage.getText());
        }
        //9. 关闭链接
        connection.close();
    }

}
