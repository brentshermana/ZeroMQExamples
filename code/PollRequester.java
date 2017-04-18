public class PollRequester {
   public static void main(String[] args) {
      ZMQ.Context context = ZMQ.context(1);

      ZMQ.Socket request = context.socket(ZMQ.REQ);
      request.connect("tcp://localhost:5558");

      String latest = "Nothing Yet";
      while (!Thread.currentThread.isInterrupted()) {
         request.send("Give me something!");
         System.out.println(request.recvStr());
      }

      request.close();
      context.term();
   }
}
