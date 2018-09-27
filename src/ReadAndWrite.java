/**
 * This class will be responsible for reading and writing using txt file.
 * This class is under development 
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;


public class ReadAndWrite  {
	
	FileOutputStream fileOut ;
	FileInputStream fileIn ;
	
	public ReadAndWrite() throws FileNotFoundException
	{
		fileOut = new FileOutputStream("DataFile.txt");
		fileIn = new FileInputStream("DataFile.txt");
	}
	
	public void writeToFile(Map<String,List<Task>> ListOfTasks) 
		{
		try
		{
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(ListOfTasks.toString());
		
		out.close();
		fileOut.close();
		System.out.println("\nSerialization Successful... Checkout your specified output file..\n");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		}
		
	

	public void readFromFile() throws IOException, ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(fileIn);
		System.out.println("Deserialized Data: \n" + in.readObject().toString());
		in.close();
		fileIn.close();
	}
	
	}

