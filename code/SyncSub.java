import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**

Synchronized subscriber.
*/
public class SyncSub {

    public static void main (String[] args) {
        Context context = ZMQ.context(1);
System.out.println("sub begin");
        //  First, connect our subscriber socket
        Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.setRcvHWM(0);
        subscriber.connect("tcp://localhost:5561");
        //subscriber.subscribe("".getBytes());

        //  Second, synchronize with publisher
        Socket syncclient = context.socket(ZMQ.REQ);
        syncclient.connect("tcp://localhost:5562");

System.out.println("waiting for a message");

        String sync = subscriber.recvStr();
        syncclient.send("Okay, now I'm getting data");
System.out.println("a");
        syncclient.recvStr();
System.out.println("b");


        while (true) {
           String data = subscriber.recvStr();
           if (data.equals("SYNC")) {
               continue;
           }
           else if (data.equals("END")) {
               syncclient.send("That's all?");
               syncclient.recvStr();
               break;
           }
           else {
               System.out.println("Sub Received: " + data);
           }
        }

        System.out.println("Sub closing");

        subscriber.close();
        syncclient.close();
        context.term();
    }
}
