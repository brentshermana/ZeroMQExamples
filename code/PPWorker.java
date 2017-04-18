import org.zeromq.ZMQ;
public class PPWorker {
public static void main (String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);
        receiver.connect("tcp://localhost:5557");

        ZMQ.Socket sender = context.socket(ZMQ.PUSH);
        sender.connect("tcp://localhost:5558");

        // No stop condition
        while (!Thread.currentThread ().isInterrupted ()) {
<<<<<<< HEAD
                String string = new String(receiver.recv(0)).trim();
                long msec = Long.parseLong(string);
                System.out.println("Job: " + msec);
                // Do the work, which just means waiting in this case
                Thread.sleep(msec);
                // Send finished signal to sink
                sender.send("".getBytes(), 0);
=======
            String string = new String(receiver.recv(0)).trim();
            long msec = Long.parseLong(string);
            System.out.println("Job: " + msec);

            //  Do the work
            Thread.sleep(msec);

            //  Send results to sink
            sender.send(Long.toString(msec), 0);
>>>>>>> 1bfa2804c4e10c5f4b62e5a125858a678e4204a7
        }
        sender.close();
        receiver.close();
        context.term();
}
}
