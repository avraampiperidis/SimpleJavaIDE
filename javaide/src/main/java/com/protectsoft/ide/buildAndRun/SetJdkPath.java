package com.protectsoft.ide.buildAndRun;


import java.io.IOException;

import com.protectsoft.ide.Consts;
import com.protectsoft.ide.buildAndRun.parserStream.JavacStream;


public class SetJdkPath implements Runnable {
	

	private Thread errorStream;
	
	private Thread outputStream;
	
	
	
	
	@Override
	public void run() {
		
		
		try {
			
			
			Runtime rt = Runtime.getRuntime();
			
			Process proc;
						
			proc = rt.exec(Consts.getCommand());
			
			JavacStream errorstream = new JavacStream(proc.getErrorStream(),"error");
			
			JavacStream outputstream = new JavacStream(proc.getInputStream(),"Output");
					
			
			errorStream = new Thread(errorstream);
			
			outputStream = new Thread(outputstream);
			
			
			errorStream.start();
			
			outputStream.start();
			
			
			@SuppressWarnings("unused")
			int exitval = proc.waitFor();
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	

}
