import java.net.*;  
import java.io.*;  
class MyClient{  
	public static void main(String args[])throws Exception{  
		Socket s=new Socket("localhost",50000);    
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
		BufferedReader din=new BufferedReader(new InputStreamReader(s.getInputStream()));  
     
		dout.write(("HELO\n").getBytes());  
		dout.flush();  
		
		System.out.println("Message: "+din.readLine());  

		dout.write(("AUTH root\n").getBytes());
       		dout.flush();

		System.out.println("Message: "+din.readLine());

		dout.write(("REDY\n").getBytes());
		dout.flush();

                System.out.println("Message: "+din.readLine());

		dout.write(("QUIT\n").getBytes());
		dout.flush();

                System.out.println("Message: "+din.readLine());

		dout.close();  
		s.close();  
	}	
}  
