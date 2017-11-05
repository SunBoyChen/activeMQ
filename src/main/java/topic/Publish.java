package topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


import javax.jms.*;

/**
 * 消息发送者
 * Created by 陈志华 on 2017-11-5.
 */
public class Publish {

    private ActiveMQConnectionFactory factory;

    private Connection connection;

    private Session session;

    private MessageProducer producer;

    public Publish() {
        try {
            this.factory = new ActiveMQConnectionFactory(
                    ActiveMQConnection.DEFAULT_USER,
                    ActiveMQConnection.DEFAULT_PASSWORD,
                    "tcp://localhost:61616");
            this.connection = factory.createConnection();
            connection.start();
            this.session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            this.producer = session.createProducer(null);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送
     */
    public void sendMessage() throws JMSException {
        Destination destination = session.createTopic("topic");
        TextMessage message = session.createTextMessage("消息");
        producer.send(destination,message);
    }

    public static void main(String[] args) throws JMSException {
        Publish publish = new Publish();
        publish.sendMessage();
    }
}
