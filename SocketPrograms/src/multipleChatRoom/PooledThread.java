package multipleChatRoom;



import java.net.Socket;

public class PooledThread extends Thread{
	public static final String STATUS_BUSY = "BUSY";
	public static final String STATUS_AVAILABLE = "AVAILABLE";
	
	private volatile Job task = null;
	private volatile Socket clientSocket = null;
	private volatile String status = null;

	public PooledThread(){
		status = new String();
	}
	
	public void setClientSocket(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	public Socket getClientSocket(){
		return clientSocket;
	}
	
	public void setTask(Job task){
		this.task = task;
	}
	
	public String getRunnbingStatus() {
		return status;
	}
	
	public void setRunningStatus(String status) {
		this.status = status;
	}

	public void run() {
		while(true){
			if(status.equals(STATUS_BUSY)){
				System.out.println("For thread "+getName()+" the state has changed to "+STATUS_BUSY);
				task.doJob(clientSocket);
				System.out.println("Client lost its connection.Now the thread "+getName()+" with state "+getRunnbingStatus()+" will be available for other client.");
			}
		}
	}
}
