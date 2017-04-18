import org.zeromq.ZMQ;
//request reply model, matches server paradigm
public class HWServer {

    public static void main(String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);

        //  Socket to talk to clients
        ZMQ.Socket responder = context.socket(ZMQ.REP);
        responder.bind("tcp://*:5555");

	int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            // Wait for next request from the client
            String in = responder.recvStr();
            System.out.println("Received " + in + " " + count++);

            // Do some 'work'
            Thread.sleep(500);

            // Send reply back to client
            String reply = "World";
            responder.send(reply.getBytes(), 0);
        }
        responder.close();
        context.term();
    }
}
