package active;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.*;

/**
 * Created by 陈志华 on 2017-11-5.
 */
public class ConsumerB {
    public final  String SELECEOR = "receiver = 'B'";
    //连接工程
    private ActiveMQConnectionFactory factory;
    //连接
    private Connection connection;
    //session
    private Session session;
    //消费者
    private MessageConsumer consumer;
    //目标
    private Destination destination;

    public ConsumerB() {
        try {
            this.factory = new ActiveMQConnectionFactory(
                    ActiveMQConnection.DEFAULT_USER,
                    ActiveMQConnection.DEFAULT_PASSWORD,
                    "tcp://localhost:61616");
            this.connection = factory.createConnection();
            connection.start();
            this.session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            this.destination = session.createQueue("first");
            this.consumer = session.createConsumer(destination,SELECEOR);
            System.out.println("Consumer B start..");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void receiver() throws JMSException {
        consumer.setMessageListener(new Listener());
    }

    /**
     * 监听消息队列
     */
    class Listener implements MessageListener{
        //阻塞队列,有序容量10000
        BlockingQueue<Runnable> queuequeue = new ArrayBlockingQueue<Runnable>(10000);

        ExecutorService executor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                20,
                120L,
                TimeUnit.SECONDS,
                queuequeue
        );

        public void onMessage(Message message) {
            if(message instanceof  MapMessage){
                MapMessage ret = (MapMessage) message;
                executor.execute(new MessageTask(ret));
            }

        }
    }

    public static void main(String[] args) throws JMSException {
        new ConsumerB().receiver();
    }
}
