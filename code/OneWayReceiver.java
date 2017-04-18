import java.util.StringTokenizer;
import org.zeromq.ZMQ;

public class OneWayReceiver {

    public static void main (String[] args) {
        ZMQ.Context context = ZMQ.context(1);

        //  Socket to talk to server
        System.out.println("Collecting 100 values");
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect("tcp://localhost:5556");

        int sum = 0;
        for (int i = 0; i < 100; i++) {
           sum += Integer.parseInt(subscriber);
        }

        System.out.println("Average is " + sum/10.0);
        subscriber.close();
        context.term();
    }
}
