package multiThreadServer;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;

public class WrkrRunnable implements Runnable{
    protected Socket clntSocket = null;
    protected String txtFrmSrvr   = null;

    public WrkrRunnable(Socket clntSocket, String txtFrmSrvr) {
        this.clntSocket = clntSocket;
        this.txtFrmSrvr   = txtFrmSrvr;
        
    }
    public void run() {
        try {
            InputStream inputstrm  = clntSocket.getInputStream();
            OutputStream outputstrm = clntSocket.getOutputStream();
            long timetaken = System.currentTimeMillis();
            outputstrm.write(("OK\n\nWrkrRunnable: " + this.txtFrmSrvr + " - " +timetaken +"").getBytes());
            System.out.println("OK\n\nWrkrRunnable: " + this.txtFrmSrvr + " - " +Thread.currentThread() +timetaken +"");
            outputstrm.close();
            inputstrm.close();
            System.out.println("Your request has processed in time : " + timetaken);
        } catch (IOException e) {           
            e.printStackTrace();
        }
    }
}