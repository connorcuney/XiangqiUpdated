import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Accounts{
	String userName;
	private String password;
	int wins;
	int loss;
	int gamesPlayed;

	Accounts(){

	}

	Accounts(String username, String password, int wins, int loss){
		this.userName = username;
		this.password = password;
		this.wins = wins;
		this.loss = loss;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLoss() {
		return loss;
	}

	public void setLoss(int loss) {
		this.loss = loss;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	File accounts = new File("accounts.txt");

	public boolean checkUsername(String username) throws FileNotFoundException{
		boolean copy = false;
		Scanner sc = new Scanner(accounts);
		while(sc.hasNext()) {
			String[] split = sc.nextLine().split(",");
			if (split[0].equals(username)) {
				copy = true;
			}
		}
		sc.close();
		return copy;
	}

	public void loadUserInfo(String username, String password) throws FileNotFoundException{
		Scanner sc = new Scanner(accounts);
		while(sc.hasNext()) {
			String[] split = sc.nextLine().split(",");
			if (split[0].equals(username) && split[1].equals(password)) {
				this.userName = split[0];
				this.password = split[1];
				this.wins = Integer.parseInt(split[2]);
				this.loss = Integer.parseInt(split[3]);
			}
		}
		sc.close();
	}

	public void loadUserInfo(String username) throws FileNotFoundException{
		Scanner sc = new Scanner(accounts);
		while(sc.hasNext()) {
			String[] split = sc.nextLine().split(",");
			if (split[0].equals(username)) {
				this.userName = split[0];
				this.password = split[1];
				this.wins = Integer.parseInt(split[2]);
				this.loss = Integer.parseInt(split[3]);
			}
		}
		sc.close();
	}
	
	public boolean userInfo(String username, String password) throws FileNotFoundException{
		boolean exists = false;
		Scanner sc = new Scanner(accounts);
		while(sc.hasNext()) {
			String[] split = sc.nextLine().split(",");
			if (split[0].equals(username) && split[1].equals(password)) {
				exists = true;
				return exists;
			}else {
				exists = false;
			}
		}
		sc.close();
		return exists;
	}

	public void deleteAccount(String lineToRemove) throws FileNotFoundException{
		try {

			File inFile = new File("accounts.txt");

			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}

			//Construct the new file that will later be renamed to the original filename.
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(accounts));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String line = null;

			//Read from the original file and write to the new
			//unless content matches data to be removed.
			while ((line = br.readLine()) != null) {

				if (!line.trim().equals(lineToRemove)) {

					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			//Delete the original file
			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			}

			//Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile))
				System.out.println("Could not rename file");

		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void updateWins(String username) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("accounts.txt"));
		String str;
		List<String> lines = new ArrayList<>();
		while ((str = in.readLine()) != null) {
			String[] temp = str.split(",");
			for(int i = 0; i < temp.length; i++) {
				lines.add(temp[i]);
			}
		}
		int userNameIndex = -1;
		for(int i = 0; i < lines.size(); i+=4) {
			if(lines.get(i).toString().equals(username)) {
				userNameIndex = i;
			}
		}
		
		if(userNameIndex >= 0) {
			lines.set(userNameIndex + 2, String.valueOf(Integer.parseInt(lines.get(userNameIndex + 2)) + 1));
		}

		FileWriter writer = new FileWriter("accounts.txt");
		for(int i = 0; i < lines.size(); i++) {
			if((i+1) % 4 == 0 && i != 0) {
				writer.write(lines.get(i) + System.lineSeparator());
			}
			else {
				writer.write(lines.get(i) + ",");
			}
			
		}
		writer.close();
		in.close();
		
	}

	public void updateLosses(String username) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("accounts.txt"));
		String str;
		List<String> lines = new ArrayList<>();
		while ((str = in.readLine()) != null) {
			String[] temp = str.split(",");
			for(int i = 0; i < temp.length; i++) {
				lines.add(temp[i]);
			}
		}

		int userNameIndex = -1;
		for(int i = 0; i < lines.size(); i+=4) {
			if(lines.get(i).toString().equals(username)) {
				userNameIndex = i;
			}
		}
		
		if(userNameIndex >= 0) {
			lines.set(userNameIndex + 3, String.valueOf(Integer.parseInt(lines.get(userNameIndex + 3))+ 1));
		}
		
		FileWriter writer = new FileWriter("accounts.txt");
		for(int i = 0; i < lines.size(); i++) {
			if((i+1) % 4 == 0 && i != 0) {
				writer.write(lines.get(i) + System.lineSeparator());
			}
			else {
				writer.write(lines.get(i) + ",");
			}
			
		}
		writer.close();
		in.close();
	}

	//	public void updateWins() {
	//		try {
	//
	//		      File inFile = new File("accounts.txt");
	//
	//		      if (!inFile.isFile()) {
	//		        System.out.println("Parameter is not an existing file");
	//		        return;
	//		      }
	//
	//		      //Construct the new file that will later be renamed to the original filename.
	//		      File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
	//
	//		      BufferedReader br = new BufferedReader(new FileReader(accounts));
	//		      PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
	//
	//		      String line = null;
	//		     
	//
	//		      //Read from the original file and write to the new
	//		      //unless content matches data to be removed.
	//		      while ((line = br.readLine()) != null) {
	//		        
	//		    	  if (!line.trim().equals(username + "," + pass + "," + win + "," + lose)) {
	//
	//		          pw.println(line);
	//		          pw.flush();
	//		        }
	//		     
	//		      }
	//		      pw.println(username + "," + pass + "," + (win + 1) + "," + lose);
	//		      
	//		      pw.close();
	//		      br.close();
	//
	//		      //Delete the original file
	//		      if (!inFile.delete()) {
	//		        System.out.println("Could not delete file");
	//		        return;
	//		      }
	//
	//		      //Rename the new file to the filename the original file had.
	//		      if (!tempFile.renameTo(inFile))
	//		        System.out.println("Could not rename file");
	//
	//		    }
	//		    catch (FileNotFoundException ex) {
	//		      ex.printStackTrace();
	//		    }
	//		    catch (IOException ex) {
	//		      ex.printStackTrace();
	//		    }
	//	}
	//	
	//	public void updateLoss() {
	//		try {
	//
	//		      File inFile = new File("accounts.txt");
	//
	//		      if (!inFile.isFile()) {
	//		        System.out.println("Parameter is not an existing file");
	//		        return;
	//		      }
	//
	//		      //Construct the new file that will later be renamed to the original filename.
	//		      File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
	//
	//		      BufferedReader br = new BufferedReader(new FileReader(accounts));
	//		      PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
	//
	//		      String line = null;
	//
	//		      //Read from the original file and write to the new
	//		      //unless content matches data to be removed.
	//		      while ((line = br.readLine()) != null) {
	//
	//		    	  if (!line.trim().equals(username + "," + pass + "," + win + "," + lose)) {
	//
	//		          pw.println(line);
	//		          pw.flush();
	//		        }
	//		      }
	//		      pw.println(this.userName + "," + this.password + "," + (this.wins + 1) + "," + (this.loss + 1));
	//		      
	//		      pw.close();
	//		      br.close();
	//
	//		      //Delete the original file
	//		      if (!inFile.delete()) {
	//		        System.out.println("Could not delete file");
	//		        return;
	//		      }
	//
	//		      //Rename the new file to the filename the original file had.
	//		      if (!tempFile.renameTo(inFile))
	//		        System.out.println("Could not rename file");
	//
	//		    }
	//		    catch (FileNotFoundException ex) {
	//		      ex.printStackTrace();
	//		    }
	//		    catch (IOException ex) {
	//		      ex.printStackTrace();
	//		    }
	//	}
}


