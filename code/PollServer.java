import org.zeromq.ZMQ;
public class PollServer {
   public static void main(String[] args) {
      ZMQ.Context context = ZMQ.context(1);

      ZMQ.Socket stream = context.socket(ZMQ.SUB);
      stream.connect("tcp://*:5557");

      ZMQ.Socket reply = context.socket(ZMQ.REP);
      reply.connect("tcp://*:5558");

      ZMQ.Poller poller = new ZMQ.Poller(2);
      poller.register(stream);
      poller.register(reply);

      String latest = "Nothing Yet";
      while (!Thread.currentThread().isInterrupted()) {
         poller.poll();
         if (poller.pollin(0)) {
            latest = stream.recvStr();
         }
         if (poller.pollin(1)) {
            reply.recvStr();
            reply.send(latest);
         }
      }

      stream.close();
      reply.close();
      context.term();
   }
}
