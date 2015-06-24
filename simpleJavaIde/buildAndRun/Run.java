package simpleJavaIde.buildAndRun;

import java.io.IOException;

import simpleJavaIde.Consts;
import simpleJavaIde.buildAndRun.parserStream.JavacStream;

public class Run implements Runnable {

	private Thread errorStream;
	
	private Thread outputStream;
	
	//private Thread userInputStream;
	
	
	
	@Override
	public void run() {
		
		
		try {
    		
    		
    		Consts.setResults(" ");
		 
		    Runtime  rt = Runtime.getRuntime();
 		
			Process proc = rt.exec(Consts.getPath()+"/bin/java "+Consts.getMainClass() + " "+Consts.getArgs());
			
			
			
			JavacStream errorstream = new JavacStream(proc.getErrorStream(),"error");
			
			JavacStream outputstream = new JavacStream(proc.getInputStream(),"Output");
			
			//UserInputStream userInput = new UserInputStream(proc.getOutputStream(),"userInput");
						
			
			errorStream = new Thread(errorstream);
			
			outputStream = new Thread(outputstream);
			
			//userInputStream = new Thread(userInput);
							
			errorStream.start();
			
			outputStream.start();
			
			//userInputStream.start();
			
			
			//@SuppressWarnings("unused")
			//int exitval = proc.waitFor();
			
			
			
   			} catch (IOException e) {
   				// TODO Auto-generated catch block
			e.printStackTrace();
			} finally {
				
			}
		
		
		
	}
	
	

}
