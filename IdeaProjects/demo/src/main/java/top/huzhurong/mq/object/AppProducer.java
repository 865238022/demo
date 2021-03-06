package top.huzhurong.queueMq.object;

import org.apache.activemq.ActiveMQConnectionFactory;
import top.huzhurong.queueMq.object.bean.User;

import javax.jms.*;

/**
 * Created by 竹 on 2017/10/14.
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
            User user = new User();user.setAge(i);
            user.setName("chenshun"+i);
            ObjectMessage objectMessage = session.createObjectMessage(user);
            //8.发布消息
            producer.send(objectMessage);
            System.out.println("消息发送成功" + objectMessage.getObject().toString());
        }
        //9. 关闭链接
        connection.close();
    }

}
