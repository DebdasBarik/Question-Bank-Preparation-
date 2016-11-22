import java.io.*;
import java .net.*;
import java.lang.*;
import java.util.*;
public class servr
{
String pass_word,user_name;
String question ,option;
String correct_option;
String file_name, string_choice;
int count_lines=0,count_option=0;

	public void connect()                                                                                                       //Server connection
	{
		
		try
		{
			Socket start_client=null;
			ServerSocket start_server=new ServerSocket(6020);
			start_client=start_server.accept();        			                                                                 //Connection established
			System.out.println("\n\nCONNECTION ESTABLISHED");
			DataOutputStream out = new DataOutputStream(start_client.getOutputStream());                                         //Streams are created
			DataInputStream in = new DataInputStream(start_client.getInputStream());
			user_name=in.readUTF();                                                                                              //Receive data from username 
			pass_word=in.readUTF();                                                                                              //Receive data from password
			FileInputStream fis = new FileInputStream("F:/Document/3rd Sem/OOPs/FileHandling exp/admin.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));                                      // admin.txt=user_name
			FileInputStream fi = new FileInputStream("F:/Document/3rd Sem/OOPs/FileHandling exp/admin1.txt");
			BufferedReader bs = new BufferedReader(new InputStreamReader(fi));                                     //   admin1.txt=pass_word
			String line = null;
			String line1 = null; 
			line = br.readLine();                                                                          //To read from file admin.txt
			line1=bs.readLine();                                                                           //To read from file admin1.txt
			if(line.equals(user_name)&& line1.equals(pass_word)) 
				{ 
				out.writeInt(1);                                                             //Checking username & password
				out.writeUTF("\n\nSUCCESSFULLY LOGIN");
				}
			else
				{
				out.writeInt(0);
				out.writeUTF("\n\nWRONG LOGIN"); 
				System.out.println("\n\nCONNECTION TERMINATED");
				System.exit(0);
				}
			int choice;
			choice=in.readInt();  			// getting choice from client 
			string_choice=Integer.toString(choice);
			if(string_choice.equals("1")|| string_choice.equals("2")|| string_choice.equals("3"))
				{
				out.writeInt(1);     				                             // valid choice 
				file_name=in.readUTF();
				File file =new File(file_name+".txt");
				if(!file.exists())
					{
					 file.createNewFile();
					}
				FileWriter fw = new FileWriter(file,true);
				BufferedWriter bw = new BufferedWriter(fw);
				FileReader fread=new FileReader(file);
				LineNumberReader lread=new LineNumberReader(fread);          // Function to read number of Lines in the file
				while(lread.readLine()!=null)
				{
				count_lines++;                                              // counter used to count the no. of lines in the file
				}
				int store_LineNo=(count_lines-1)/6;                          // converting the number of lines to number of question       
				String previous_question=Integer.toString(store_LineNo);     // convert to String            
				out.writeUTF(previous_question);                             // sending the number of lines to client
				int no_of_question=in.readInt();                             // receiving the number of question from client                                           
				for(int a=0;a<no_of_question;a++)
				{
				question=in.readUTF();
				bw.append(System.lineSeparator());
				bw.write("Question"+" "+(store_LineNo+1)+":"+question);
				store_LineNo++;
				for(int j=0;j<4;j++)
				{
				option=in.readUTF();
				bw.append(System.lineSeparator());
				bw.write("Option"+" "+(char)(65+j)+":"+option);
				count_option++;                                      
				if(count_option%4==0)                                // getting correct option
				{
				correct_option=in.readUTF();
				bw.append(System.lineSeparator());
				bw.write("Correct Option"+":"+correct_option);
				}

				}
				}
				bw.close();
				out.writeUTF("DATA SUCCESSFULLY UPDATED");

				}
			else
				{
				out.writeInt(0);                                       //sending the choice back to client
				out.writeUTF("WRONG CHOICE");
				System.out.println("\n\nCONNECTION TERMINATED");
				System.exit(0);
				} 
		}
			catch(Exception e)
			{
			 System.out.println(e);
			}
	  }
public static void main(String arg[])    // main starts
{
servr sr=new servr();
sr.connect();                              // connect() call
}
}

