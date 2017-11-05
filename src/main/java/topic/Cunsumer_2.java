package topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by 陈志华 on 2017-11-5.
 */
public class Cunsumer_2 {

    private ActiveMQConnectionFactory factory;

    private Connection connection;

    private Session session;

    private MessageConsumer consumer;

    public Cunsumer_2() {

        try {
            this.factory = new ActiveMQConnectionFactory(
                    ActiveMQConnection.DEFAULT_USER,
                    ActiveMQConnection.DEFAULT_PASSWORD,
                    "tcp://localhost:61616");
            this.connection = factory.createConnection();
            connection.start();
            this.session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    /**
     * 消费
     */
    public void receiver(){
        try {
            Destination destination = session.createTopic("topic");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new Cunsumer_2.listener());
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    /**
     * 监听消息队列
     */
    class listener implements MessageListener{

        public void onMessage(Message message) {
            try {
                if(message instanceof TextMessage){
                    System.out.println("c2收到");
                    String text = ((TextMessage) message).getText();
                    System.out.println(text);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        Cunsumer_2 cunsumer_2 = new Cunsumer_2();
        cunsumer_2.receiver();
    }
}
