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
		String type="";

		for(int i=0;i<num;i++){
			str = din.readLine();
			string = str.split(" ");
			if (Integer.parseInt(string[4]) > maxCore) type=string[0];
			System.out.println("DATA: "+str);
		}

		System.out.println("TYPE: "+type);

		dout.write(("OK\n").getBytes());
		dout.flush();
		System.out.println("Message: "+din.readLine());
		
		dout.write(("SCHD 0 "+ type +" 0\n").getBytes());
		dout.flush();
		
		System.out.println("Message: "+din.readLine());

		dout.write(("OK\n").getBytes());
                dout.flush();

                System.out.println("Message: "+din.readLine());

		dout.write(("QUIT\n").getBytes());
		dout.flush();

		dout.close();  
		s.close();  
	}	
}
