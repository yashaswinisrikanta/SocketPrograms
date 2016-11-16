package multipleChatRoom;



import java.net.Socket;

public interface Job {
	void doJob(Socket clientSocket);
}
