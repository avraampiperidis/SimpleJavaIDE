package simpleJavaIde.buildAndRun.parserStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import simpleJavaIde.Consts;
import javafx.concurrent.Task;

public class CustomStream extends Task<Object>  {
	
	private InputStream in;
	@SuppressWarnings("unused")
	private String type;
	
	
	public CustomStream(InputStream in,String type) {
		this.in = in;
		this.type = type;
	}
	
	

	@Override
	protected Object call() throws Exception {
		try {
			
			synchronized (this) {
				
				InputStreamReader isr = new InputStreamReader(in);
			
				BufferedReader br = new BufferedReader(isr);
				String line = null;
			
				while((line = br.readLine()) != null) {
					Consts.setCustom(Consts.getCustom()+ line + "\n"); 
				}
						
				Consts.setStateProc(false);
				
				}
			
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	
	
	
	

}
