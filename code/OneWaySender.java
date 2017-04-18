import java.util.Random;
import org.zeromq.ZMQ;
public class OneWaySender {

    public static void main (String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://*:5556");

        Random rand = new Random(System.currentTimeMillis());
        while (!Thread.currentThread ().isInterrupted ()) {
            publisher.send(Integer.toString(rand.nextInt(100)));
        }

        publisher.close ();
        context.term ();
    }
}
