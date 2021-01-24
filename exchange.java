import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


public class exchange {

	public static void main(String[] args) {

		List<String> allLines;
		ArrayList<String> head_list= new ArrayList<>();
		ArrayList<String> tail_list = new ArrayList<>();
		HashMap<String, List<String>> map = new HashMap<>();
		
		LinkedBlockingQueue<String> blockque = new LinkedBlockingQueue<String>();
		long current_time;
		
		try {
			allLines = Files.readAllLines(Paths.get("calls.txt"));
			for (String line : allLines) {
				String temp = "";
				temp+= line;
				temp =temp.substring(temp.indexOf("{")+1, temp.indexOf("}"));
				
				String head="";
				String tail="";
		
				
				head=head+temp.substring(0,temp.indexOf(","));
				tail = tail+ temp.substring(temp.indexOf("[")+1,temp.indexOf("]"));
				
				map.put(head,
						Arrays.asList(temp.substring(temp.indexOf("[") + 1, temp.indexOf("]")).split("\\,")));

				head_list.add(head);
				tail_list.add(tail);
				
			}
			System.out.println("** Calls to be made **");
			for (int i = 0; i < head_list.size(); i++) {
				for (int j = i; j < tail_list.size(); j++) {
					System.out.println(head_list.get(i)+":"+"["+tail_list.get(j)+"]");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("");
		
		current_time = System.currentTimeMillis();
		ExecutorService service = Executors.newFixedThreadPool(map.size());
		for (String keys : map.keySet()) {
			calling t = new calling(keys, map.get(keys), blockque);
			service.execute(t);
		}
		service.shutdown();
		while (!service.isTerminated() || System.currentTimeMillis() < current_time + 2500) {
			
		if (!blockque.isEmpty()) 
		{
			System.out.println(blockque.poll());
		}
	}
		System.out.println("Master has received no replies for 10 seconds, ending...");			

	}

}

