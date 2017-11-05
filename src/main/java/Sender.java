import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 发送消息
 * Created by 陈志华 on 2017-11-5.
 */
public class Sender {
    public static void main(String[] args) throws JMSException, InterruptedException {
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
        MessageProducer producer = session.createProducer(null);
        for (int i = 0 ; i<100; i++){
            //创建一个消息对象
            //TextMessage message = session.createTextMessage("我的第" + i + "个消息");
            MapMessage message = session.createMapMessage();
            message.setString("age","23");
            message.setString("name","zhihua");
            //发送消息
            /**
             * 参数一:目标地址, 可以在创建发送者时设置
             * 参数二:具体消息
             * 参数三:传送数据模式
             * 优先级
             * 超时时间
             */
            producer.send(destination,message);
            Thread.sleep(1000);
        }
        //关闭连接
        if(connection != null){
            connection.close();
        }

    }
}
