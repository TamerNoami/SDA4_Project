package readWrite;
/**
 * This class will be responsible for reading and writing using txt file.
 * This class is under development 
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import core.Task;
import core.TaskManager;

public class ReadAndWrite {

	TaskManager TM;
	FileOutputStream fileOut;
	FileInputStream fileIn;
	BufferedReader reader;

	/**
	 * Class contractor to receive the instance on the TaskManager which contains
	 * the HashMap
	 * 
	 * @param taskManager the instance of the TaskManager class uses the HashMap
	 *                    when reading and writing to a file
	 * @throws FileNotFoundException
	 */
	public ReadAndWrite(TaskManager taskManager) throws FileNotFoundException {
		this.TM = taskManager;
	}

	/**
	 * Method for writing the the tasks into a txt file using via as ArrayList of
	 * Task
	 * 
	 * @param ListOfTasks is the HashMap after been converted to ArrayList for
	 *                    writing purpose
	 * @throws FileNotFoundException
	 */
	public void writeToFile(List<Task> ListOfTasks) throws FileNotFoundException {
		fileOut = new FileOutputStream("../DataFile.txt");
		try {
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(ListOfTasks.toString().replace('=', ' ').replace(',', ' '));

			out.close();
			fileOut.close();
			// System.out.println("\nSerialization Successful... Checkout your specified
			// output file..\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to read the task from a txt file into the HashMap
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 */
	public void readFromFile() throws IOException, ClassNotFoundException, ParseException {
		// BufferedReader reader;
		try {

			reader = new BufferedReader(new FileReader("../DataFile.txt"));
			String line = reader.readLine();
			while (line != null && !line.equals("]")) {
				String line1 = reader.readLine().trim();
				String line2 = reader.readLine().trim();
				String line3 = reader.readLine().trim();
				String line4 = reader.readLine().trim();
				String line5 = reader.readLine().trim();
				boolean st = line4.equalsIgnoreCase("false") ? false : true;
				TM.AddTask(line1, line2, TM.StringToDate(line3), st);
				line = reader.readLine().trim();
			}
			System.out.flush();
			reader.close();
			// System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
