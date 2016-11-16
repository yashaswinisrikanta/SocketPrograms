package multipleChatRoom;



import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class ThreadPoolManager {
	private int maximumCapacity;
	private static ThreadPoolManager instance; 
	private Set<PooledThread>  pool;
	
	private ThreadPoolManager(){
		maximumCapacity = 4;		
		pool = new HashSet<PooledThread>(maximumCapacity);
	}
	
	public static ThreadPoolManager getInstance(){
		if(instance == null){
			instance = new ThreadPoolManager();
		}		
		return instance;
	}
	
	public int getMaximumPoolSize(){
		return maximumCapacity;
	}
	
	public int getCurrentPoolSize(){
		return pool.size();
	}
	
	public int getAvailableThreads(){
		int counter = 0;
		for (Iterator<PooledThread> iterator = pool.iterator(); iterator.hasNext();) {
			PooledThread thread = (PooledThread) iterator.next();
			if(thread.getRunnbingStatus().equals(PooledThread.STATUS_AVAILABLE)){
				++counter;
			}			
		}
		return counter;
	}
	
	public int getBusyThreads(){
		int counter = 0;
		for (Iterator<PooledThread> iterator = pool.iterator(); iterator.hasNext();) {
			PooledThread thread = (PooledThread) iterator.next();
			if(thread.getRunnbingStatus().equals(PooledThread.STATUS_BUSY)){
				++counter;
			}			
		}
		return counter;
	}

	public void assignTask(Socket clientSocket){
		PooledThread requestThread = getThreadFromPool();
		requestThread.setTask(new RequestJob()); 
		requestThread.setClientSocket(clientSocket);
    	System.out.println("Setting socket and update requestThread status from "+requestThread.getRunnbingStatus()+" of thread "+requestThread.getName()+" to "+PooledThread.STATUS_BUSY);
    	requestThread.setRunningStatus(PooledThread.STATUS_BUSY);
   	}
	
	public void releaseTask(){
		PooledThread requestThread = (PooledThread)Thread.currentThread();
		System.out.println("Changing thread "+requestThread.getName()+" status from "+requestThread.getRunnbingStatus()+" to "+PooledThread.STATUS_AVAILABLE);
		requestThread.setRunningStatus(PooledThread.STATUS_AVAILABLE);
	}
	
	
	public PooledThread getThreadFromPool(){
		System.out.println("Current size of pool "+pool.size());
		// ask client to wait for some time and try again
		if(pool.size() == maximumCapacity){
			System.out.println("Running out of resource. Can not create new thread please wait for some time.");
			return null;
		}
		
		// if threads exist in pool then check which one is free and return that
		for (Iterator<PooledThread> iterator = pool.iterator(); iterator.hasNext();) {
			PooledThread thread = (PooledThread) iterator.next();
			String threadStatus = thread.getRunnbingStatus();
			if(threadStatus.equals(PooledThread.STATUS_AVAILABLE)){
				System.out.println("Thread name "+thread.getName());
				System.out.println("Thread is available, returning "+thread.getName()+ "with status "+thread.getRunnbingStatus());
				System.out.println("This thread will wait for status change from "+thread.getRunnbingStatus()+" to "+PooledThread.STATUS_BUSY);
				return thread;
			}
		}
		
		// if no thread exist in pool then create one add to pool and return back
		PooledThread newThread = new PooledThread();
		newThread.setRunningStatus(PooledThread.STATUS_AVAILABLE);
		newThread.start();
		pool.add(newThread);
		System.out.println("Thread name "+newThread.getName());
		System.out.println("Created, set to busy and started new thread "+newThread.getName()); 
		System.out.println("This thread will wait for status change from "+newThread.getRunnbingStatus()+" to "+PooledThread.STATUS_BUSY);
		
		return newThread;
	}
	
}
