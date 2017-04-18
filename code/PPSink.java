import org.zeromq.ZMQ;
public class PPSink {
public static void main (String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);
        receiver.bind("tcp://*:5558");

        String string = new String(receiver.recv(0));

        long tstart = System.currentTimeMillis();

        int total = 0;
        for (int i = 0; i < 100; i++) {
                string = new String(receiver.recv(0)).trim();
                System.out.println("Receive " + string);
                total += Integer.parseInt(string);
        }

        long tend = System.currentTimeMillis();

        System.out.println("Total elapsed time: " + (tend - tstart) + " msec");

        receiver.close();
        context.term();
}
}
