import java.util.Random;
import org.zeromq.ZMQ;

public class PPVent {
public static void main (String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);

        ZMQ.Socket sender = context.socket(ZMQ.PUSH);
        sender.bind("tcp://*:5557");

        ZMQ.Socket sink = context.socket(ZMQ.PUSH);
        sink.connect("tcp://localhost:5558");

        System.out.println("Start Workers, then press Enter");
        System.in.read();
        System.out.println("Sending tasks to workers\n");

        //signal start of batch
        sink.send("0", 0);

        Random rand = new Random(System.currentTimeMillis());
        //  Send 100 tasks
        int total = 0;
        for (int i = 0; i < 100; i++) {
                int workload = rand.nextInt(100) + 1;
                total += workload;
                System.out.print("Workload " + workload);
                sender.send(Integer.toString(workload));
        }

        System.out.println("Total expected cost: " + total + " millis");
        Thread.sleep(1000);

        sink.close();
        sender.close();
        context.term();
}
}
