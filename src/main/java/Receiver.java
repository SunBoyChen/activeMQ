import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 接收消息
 * Created by 陈志华 on 2017-11-5.
 */
public class Receiver {

    public static void main(String[] args) throws JMSException {
        System.out.println("123");
        //创建工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        //创建连接
        Connection connection = factory.createConnection();
        connection.start();
        //开启会话 参数1.是否开启事务 参数2.签收模式
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        //创建目标 ,队列
        Destination destination = session.createQueue("first");
        //创建消息发送者,以及消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //监听消息
        while (true){
           /*TextMessage message = (TextMessage) consumer.receive();
            String text = message.getText();
            System.out.println(text);*/
           MapMessage message = (MapMessage) consumer.receive();
            String age = message.getString("age");
            String name = message.getString("name");
            System.out.println(age+name);
        }

    }
}
