package active;

import javax.jms.MapMessage;

/**
 * Created by 陈志华 on 2017-11-5.
 */
public class MessageTask  implements  Runnable{

    private MapMessage mapMessage;

    public MessageTask() {

    }

    public MessageTask(MapMessage mapMessage) {
        this.mapMessage = mapMessage;
    }

    public void run() {
        try {
            Thread.sleep(500);
            System.out.println("当前线程: " + Thread.currentThread().getName() + "处理任务: " + mapMessage.getString("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
