package top.huzhurong.queueMq.object;

import org.apache.activemq.ActiveMQConnectionFactory;
import top.huzhurong.queueMq.object.bean.User;

import javax.jms.*;

public class Appconsumer {
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
        //6.创建一个消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //7.创建一个监听器
        consumer.setMessageListener(message -> {
            //得到消息并转型 
            ObjectMessage objectMessage = (ObjectMessage) message;
            try {
                User user = (User) objectMessage.getObject();
                System.out.println("接受的消息："+user);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        //8. 关闭链接
//        connection.close();

    }
}
