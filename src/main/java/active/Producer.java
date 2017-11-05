package active;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by 陈志华 on 2017-11-5.
 */
public class Producer {

    private ActiveMQConnectionFactory factory;

    private Connection connection;

    private Session session;

    private MessageProducer producer;

    public Producer() {
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

    public void send(){
        try {
            Destination destination = session.createQueue("first");
            for (int i = 0 ; i < 100; i++){
                MapMessage message = session.createMapMessage();
                int id = i;
                message.setInt("id",id);
                message.setString("name","张"+i);
                message.setString("age",""+i);
                String receiver = id%2==0 ? "A" : "B";
                message.setStringProperty("receiver",receiver);
                producer.send(destination,message,DeliveryMode.NON_PERSISTENT,2,1000*60*10L);
                System.out.println("mesage send id : "+ id);
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.send();
    }
}
