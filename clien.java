import java.io.*;
import java .net.*;
import java.util.*;
public class clien
{
 public void display_question()                                              //Display Question
	{ 
		Scanner in=new Scanner(System.in);
		System.out.println("*************************************WELCOME************************************");
		System.out.println("\t\t\t\tCHAPTERS ARE-"+"\n"+"\t\t\t\t\t1.OOPs(oop)"+"\n"+"\t\t\t\t\t2.NETWORKING(net)"+"\n"+"\t\t\t\t\t3.PROGRAMMING(prog)");
		System.out.print("\n\t\t\t\tENTER THE CHOICE -"+" ");
	}
public void login()                                                           // Login Method 
	{
	 
		Scanner on=new Scanner(System.in);
		String password,username,question,option,correct_option,file_name;
		try
		{
			Socket start_client=new Socket("localhost",6020);
            System.out.println("\n\nCONNECTION ESTABLISHED");
			DataOutputStream out = new DataOutputStream(start_client.getOutputStream());
			DataInputStream in = new DataInputStream(start_client.getInputStream());
			System.out.println("***********************LOGIN****************************");
			System.out.print(" USERNAME"+":");
			username=on.next();
			out.writeUTF(username);                                 //Sending username to Server
			System.out.print("\n PASSWORD"+":");
			password=on.next();
			out.writeUTF(password);                                //Sending password to Server
			int success_login=in.readInt();
			if(success_login==1)
			{
				System.out.println(in.readUTF());
				display_question();
				int select_choice,No_Of_Question,count_option=0;
				select_choice=on.nextInt();                          // user giving choice
				out.writeInt(select_choice);                        // choice are send to the server
				int wrong_choice=in.readInt();                      // receiving the wrong choice and storing it to a variable
				if(wrong_choice==0)
					{
						System.out.println(in.readUTF());
						System.out.println("\n\nCONNECTION TERMINATED");
						System.exit(0);
					}
				else                                                    // valid choice
					{
						System.out.print("\n\nENTER THE FILE NAME"+":");                              
						file_name=on.next();                                                        //input File Name
						out.writeUTF(file_name);
						System.out.println("\nALREADY U HAVE "+" "+in.readUTF()+" "+"QUESTION!!!");
						System.out.println("\n\nENTER THE NUMBER OF QUESTION"+":");
						No_Of_Question=on.nextInt();                                            // asking for number of question
						out.writeInt(No_Of_Question);                                   // sending the number of question to the server for saving the questions in the file
						on.nextLine();
						for(int i=0;i<No_Of_Question;i++)                            
						{
						System.out.println("Enter the Questions"+(i+1)+" ");
						question=on.nextLine();
						out.writeUTF(question);
						for(int j=0;j<4;j++)
						{System.out.println("Enter the Option"+" "+(char)(j+65)+" ");
						option=on.nextLine();
						out.writeUTF(option);
						count_option++;
						if(count_option%4==0)
						{
						System.out.println("CORRECT OPTION :"+" ");
						correct_option=on.nextLine();
						out.writeUTF(correct_option);
						}
						}
						}
						System.out.println(in.readUTF());
					}
			}
			else
			{
				System.out.println(in.readUTF());
				System.out.println("\n\nCONNECTION TERMINATED");
				System.exit(0);
			}
		}
		catch(Exception e)
			{
			    System.out.println(e);
			}
	}
public static void main(String arg[])
	{
	clien cl=new clien();
	cl.login();                                 //Login() call
	}
}