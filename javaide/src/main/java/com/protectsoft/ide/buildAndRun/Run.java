package com.protectsoft.ide.buildAndRun;


import java.io.IOException;

import com.protectsoft.ide.Consts;
import com.protectsoft.ide.buildAndRun.parserStream.JavacStream;


public class Run implements Runnable {

	private Thread errorStream;
	
	private Thread outputStream;
		
	
	
	@Override
	public void run() {
		
		
		try {
    		
    		
    		Consts.setResults(" ");
		 
		    Runtime  rt = Runtime.getRuntime();
 		
			Process proc = rt.exec(Consts.getPath()+"/bin/java "+Consts.getMainClass() + " "+Consts.getArgs());
			
			
			
			JavacStream errorstream = new JavacStream(proc.getErrorStream(),"error");
			
			JavacStream outputstream = new JavacStream(proc.getInputStream(),"Output");
			
						
			
			errorStream = new Thread(errorstream);
			
			outputStream = new Thread(outputstream);
			
							
			errorStream.start();
			
			outputStream.start();
			
		
			int exitval = proc.waitFor();
			
   			} catch (IOException e) {
   				
			e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				
			}
		
		
		
	}
	
	

}
