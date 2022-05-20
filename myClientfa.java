import java.net.*;  
import java.io.*;  
import java.util.*;
import java.lang.*;
class MyClientFC{  
	public static void main(String args[])throws Exception{  
		Socket s=new Socket("localhost",50000);    
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
		BufferedReader din=new BufferedReader(new InputStreamReader(s.getInputStream()));  
		String str="";

		dout.write(("HELO\n").getBytes());  
		dout.flush();  
		
		System.out.println("Message: "+din.readLine());  

		dout.write(("AUTH root\n").getBytes());
       		dout.flush();
		
		System.out.println("Message: "+din.readLine());
		
		boolean repeat=true;

		dout.write(("REDY\n").getBytes());
		dout.flush();

		str = din.readLine();
		String[] string = str.split(" ");

		while(repeat){

			int newJobID = Integer.parseInt(string[2]);
                	int newCore = Integer.parseInt(string[4]);
			int newMem = Integer.parseInt(string[5]);
			int newDisk = Integer.parseInt(string[6]);
			System.out.println("Message: "+str);

	                dout.write(("GETS Avail "+newCore+" "+newMem+" "+newDisk+"\n").getBytes());
                	dout.flush();
		
			str = din.readLine();
			System.out.println("Message : "+str);
			string = str.split(" ");

			int num = Integer.parseInt(string[1]);

			if(num == 0){
				dout.write(("OK\n").getBytes());
	                        dout.flush();			
	
				dout.write(("GETS Capable "+newCore+" "+newMem+" "+newDisk+"\n").getBytes());
	                        dout.flush();

        	                str = din.readLine();
                	        System.out.println("Message : "+str);
                        	string = str.split(" ");
				
				str = din.readLine();
                                System.out.println("Message : "+str);
                                string = str.split(" ");

				System.out.println(string[0]);

                        	num = Integer.parseInt(string[1]);
			}

			dout.write(("OK\n").getBytes());
			dout.flush();
			
			String type="";
			int serverIndex=0;
			for(int i=0;i<num;i++){
				str = din.readLine();
				string = str.split(" ");
				if (i==0){
					type = string[0];
					serverIndex = Integer.parseInt(string[1]);
				}
				System.out.println("DATA: "+str);
			}
			System.out.println("TYPE: "+type);
			System.out.println("SERVER ID: "+serverIndex);

			dout.write(("OK\n").getBytes());
			dout.flush();

			str = din.readLine();
			System.out.println("Message : "+str);

			dout.write(("SCHD "+ newJobID +" "+ type +" "+ serverIndex +"\n").getBytes());
			dout.flush();

			System.out.println("SCHD JOB "+newJobID);

			System.out.println(din.readLine());
			
			dout.write(("REDY\n").getBytes());
                        dout.flush();

                        str=din.readLine();
                        string=str.split(" ");
                        System.out.println("Message: "+str);

			while (string[0].equals("JCPL")){
                                dout.write(("REDY\n").getBytes());
                                dout.flush();

                                str=din.readLine();
                                string=str.split(" ");
                                System.out.println("Message: "+str);
                        }

                        if (string[0].equals("NONE")){
                                repeat=false;
                        }else{
                                newJobID = Integer.parseInt(string[2]);
                        }

		}

		dout.write(("QUIT\n").getBytes());
		dout.flush();
		
		/*
		while (repeat){
			
			if(serverIndex==typeNum){
				serverIndex=0;
			}

			dout.write(("SCHD "+ newJobID  +" "+ type +" "+ serverIndex +"\n").getBytes());
			dout.flush();
			
			System.out.println("SCHD JOB "+newJobID);
			serverAvail[serverIndex] = false;
			serverIndex++;

			System.out.println("Message: "+din.readLine());

                        dout.write(("REDY\n").getBytes());
			dout.flush();

			str=din.readLine();
                	string=str.split(" "); 
			System.out.println("Message: "+str);
			
			
			while (string[0].equals("JCPL")){
				dout.write(("REDY\n").getBytes());
		              	dout.flush();

        	               	str=din.readLine();
                        	string=str.split(" ");
				System.out.println("Message: "+str);
			}

			if (string[0].equals("NONE")){
                                repeat=false;
                        }else{
				newJobID = Integer.parseInt(string[2]);
			}
		}
		*/

		dout.close();  
		s.close();  
	}	
}

