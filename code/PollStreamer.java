import org.zeromq.ZMQ;
public class PollStreamer {
   public static void main(String[] args) throws Exception {
      ZMQ.Context context = ZMQ.context(1);

      ZMQ.Socket stream = context.socket(ZMQ.PUB);
      stream.bind("tcp://*:5557");

      String[] messages = new String[]{"A", "B", "C", "D", "E"};

      int index = 0;
      while (!Thread.currentThread().isInterrupted()) {
         index++;
         if (index >= messages.length) index=0;

         stream.send(messages[index]);
         Thread.sleep(10);
      }

      stream.close();
      context.term();
   }
}
