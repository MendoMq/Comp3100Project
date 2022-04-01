import java.net.*;  
import java.io.*;  
import java.util.*;
import java.lang.*;
class MyClient{  
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

		dout.write(("REDY\n").getBytes());
		dout.flush();

                System.out.println("Message: "+din.readLine());

                dout.write(("GETS All\n").getBytes());
                dout.flush();
		
		str = din.readLine();
		System.out.println("Message : "+str);
		String[] string = str.split(" ");
		int num = Integer.parseInt(string[1]);

		dout.write(("OK\n").getBytes());
		dout.flush();

		int maxCore=0;
		int typeNum=0;
		String type="";

		for(int i=0;i<num;i++){
			str = din.readLine();
			string = str.split(" ");
			if (Integer.parseInt(string[4]) > maxCore){
			       	maxCore=Integer.parseInt(string[4]);
				type=string[0];
				typeNum=0;
			}
			if (string[0].equals(type)){
				typeNum++;
			}
			System.out.println("DATA: "+str);
		}
		System.out.println("MAXCORE "+maxCore);
		System.out.println("TYPE: "+type);
		System.out.println("TYPENUM: "+typeNum);

		dout.write(("OK\n").getBytes());
		dout.flush();
		
		System.out.println("Message: "+din.readLine());
		
		dout.write(("REDY\n").getBytes());
                dout.flush();
	
		int newJobID=0;
		int serverIndex=0;
		boolean[] serverAvail = new boolean[typeNum];
		for(int i=0;i<typeNum;i++){
			serverAvail[i]=true;
		}
		str=din.readLine();
		string=str.split(" ");
		boolean repeat=true;
		while (repeat){
		       	newJobID = Integer.parseInt(string[2]);

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
			}
		}
		dout.write(("QUIT\n").getBytes());
		dout.flush();

		dout.close();  
		s.close();  
	}	
}

