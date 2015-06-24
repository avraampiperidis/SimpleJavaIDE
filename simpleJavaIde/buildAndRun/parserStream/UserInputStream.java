package simpleJavaIde.buildAndRun.parserStream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Optional;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.TextInputDialog;

public class UserInputStream extends Task<Object>  {
	
	private OutputStream out;
	
	private String type =null;
	
	
	
	public UserInputStream(OutputStream out,String type) {
		this.out = out;
		this.type = null;
	}
	
	

	@Override
	protected Object call() throws Exception {
		
		try {
		    
			synchronized (this) {
				
				new Thread() {
					public void run() {
						
						Platform.runLater(new Runnable() {
				            @Override 
				            public void run() {
				               
				            	TextInputDialog dialog = new TextInputDialog("");
				            	dialog.setTitle("Text Input Dialog");
				            	dialog.setHeaderText("Look, a Text Input Dialog");
				            	dialog.setContentText("Please enter your name:");

				            	// Traditional way to get the response value.
				            	Optional<String> result = dialog.showAndWait();
				            	if (result.isPresent()){
				            	    type = result.get();
				            	}
				            	
				            }
				        });
						
					}
				}.start();
				
				
				while(type == null) {
					Thread.sleep(150);
				}
				
				OutputStreamWriter isr = new OutputStreamWriter(out);					
			
				isr.write(type);
				
			    isr.flush();
				isr.close();
				
				}
			
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		return null;
		}
	
	
	


}
