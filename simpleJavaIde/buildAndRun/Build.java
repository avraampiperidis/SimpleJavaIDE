package simpleJavaIde.buildAndRun;

import java.io.IOException;

import simpleJavaIde.Consts;
import simpleJavaIde.buildAndRun.parserStream.JavacStream;

public class Build implements Runnable {
	
	private Thread errorStream;
	
	private Thread outputStream;
	
	
	
	@Override
	public void run() {
		
		
		try {
			
			
			Runtime rt = Runtime.getRuntime();
			
			Process proc;
						
			proc = rt.exec(Consts.getPath()+"/bin/javac *.java");
			
			JavacStream errorstream = new JavacStream(proc.getErrorStream(),"error");
			
			JavacStream outputstream = new JavacStream(proc.getInputStream(),"Output");
			
			
			
			errorStream = new Thread(errorstream);
			
			outputStream = new Thread(outputstream);
			
			
			errorStream.start();
			
			outputStream.start();
			
			
			//@SuppressWarnings("unused")
			//int exitval = proc.waitFor();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			
			
		}
		
		
	}
	
	
	
	

}
