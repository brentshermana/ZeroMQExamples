import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**

Synchronized publisher.
*/
public class SyncPub {

    public static void main (String[] args) throws InterruptedException {
        Context context = ZMQ.context(1);

        //  Socket to talk to clients
        Socket publisher = context.socket(ZMQ.PUB);
        publisher.setLinger(5000);
        // In 0MQ 3.x pub socket could drop messages if sub can follow the generation of pub messages
        publisher.setSndHWM(0);
        publisher.bind("tcp://*:5561");

        //  Socket to receive signals
        Socket syncservice = context.socket(ZMQ.REP);
        syncservice.bind("tcp://*:5562");

        System.out.println("Waiting for subscriber");
        syncservice.recv();

        ZMQ.Poller syncPoller = new ZMQ.Poller(1);
        syncPoller.register(syncservice, ZMQ.Poller.POLLIN);

        boolean synced = false;
        while (!synced) {
System.out.println("wait loop");
           syncPoller.poll(1);
           if (syncPoller.pollin(0)) {
System.out.println("sync");
               syncservice.recvStr();
               syncservice.send("Okay here goes");
               synced = true;
           }
           else {
System.out.println("still waiting");
             publisher.send("SYNC");
             Thread.sleep(1);
           }
        }

       System.out.println("Synced! Sending 10 messages");
       for (int i = 0; i < 10; i++) {
          publisher.send("ACTUAL DATA");
       }
       publisher.send("END");

       syncservice.recvStr();
       syncservice.send("That's All");

       System.out.println("Pub closing");

        // clean up
        publisher.close();
        syncservice.close();
        context.term();
    }
}
