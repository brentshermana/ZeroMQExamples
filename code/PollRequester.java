import org.zeromq.ZMQ;
public class PollRequester {
   public static void main(String[] args) throws Exception {
      ZMQ.Context context = ZMQ.context(1);

      ZMQ.Socket request = context.socket(ZMQ.REQ);
      request.bind("tcp://127.0.0.1:5558");

      String latest = "Nothing Yet";
      while (!Thread.currentThread().isInterrupted()) {
         request.send("Give me something!");
         System.out.println(request.recvStr());

         Thread.sleep(200);
      }

      request.close();
      context.term();
   }
}
