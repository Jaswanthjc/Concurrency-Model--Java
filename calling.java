import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class calling extends Thread{

	String key;
	List<String> values;
	LinkedBlockingQueue<String> blockingQueue;
	Queue<String> response;
	long timetaken;

	public calling(String key, List<String> values,LinkedBlockingQueue<String> blockingQueue)
	{
		this.key=key;
		this.blockingQueue=blockingQueue;
		this.values=values;
		this.response=new LinkedList<String>();	
	}
	
	public void authentication(String name) throws InterruptedException
	{
		timetaken = System.currentTimeMillis();
		while (System.currentTimeMillis() < timetaken + 1500){
			Thread.sleep(100);
		}
		System.out.println("Process " + name + " has received no calls for 5 seconds, ending...\n");
	}

	public void run()
	{

		try {
			for (String names : values) {
				Thread.sleep(50);
				long ms = System.nanoTime();

				blockingQueue.put(names + " received intro message from " + key + " [" + ms + "]");
				Thread.sleep(50);
				response.add((key + " received reply message from " + names + " [" + ms + "]"));
			}
			while(!response.isEmpty())
			{
				String temp=response.poll();
				blockingQueue.put(temp);			
			}

			authentication(key);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}