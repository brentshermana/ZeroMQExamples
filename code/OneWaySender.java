

import java.util.Random;
import org.zeromq.ZMQ;

//
//  Weather update server in Java
//  Binds PUB socket to tcp://*:5556
//  Publishes random weather updates
//
public class OneWaySender {

    public static void main (String[] args) throws Exception {
        //  Prepare our context and publisher
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
