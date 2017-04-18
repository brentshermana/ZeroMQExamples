import org.zeromq.ZMQ;

//
//  Task worker in Java
//  Connects PULL socket to tcp://localhost:5557
//  Collects workloads from ventilator via that socket
//  Connects PUSH socket to tcp://localhost:5558
//  Sends results to sink via that socket
//
public class PPWorker {

    public static void main (String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);

        //  Socket to receive messages on
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);
        receiver.connect("tcp://localhost:5557");

        //  Socket to send messages to
        ZMQ.Socket sender = context.socket(ZMQ.PUSH);
        sender.connect("tcp://localhost:5558");

        // No stop condition
        while (!Thread.currentThread ().isInterrupted ()) {
            String string = new String(receiver.recv(0)).trim();
            long msec = Long.parseLong(string);
            System.out.println("Job: " + msec);

            //  Do the work
            Thread.sleep(msec);

            //  Send results to sink
            sender.send(Long.toString(msec), 0);
        }
        sender.close();
        receiver.close();
        context.term();
    }
}
