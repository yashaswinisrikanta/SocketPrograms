package multiThreadServer;

import java.io.IOException;

public class Client{
public static void main(String [] agrs) throws IOException{
ServerMultithreaded newServer = new ServerMultithreaded(8080);
new Thread(newServer).start();

try {
    Thread.sleep(20 * 10000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
System.out.println("Stopping newServer");
newServer.stop();
}
}