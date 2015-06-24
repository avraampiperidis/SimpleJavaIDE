package simpleJavaIde;
	

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;



public class Main extends Application {
	
	 private int width;
	 private int height;
	 
	 static {
		 
		 FXMLSplashController.showSplashScreen();
		
	 }
	   
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			
			Parent root = FXMLLoader.load(getClass().getResource("view/FXMLDocument.fxml"));
			
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        
			
	        width =(int) dim.getWidth()-10;
	        height =(int) dim.getHeight()-65;
			
			Scene scene = new Scene(root,width,height);
		
			
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("simpleJavaIde/assets/logosmall.png"));
			primaryStage.setTitle("> Simple Java IDE");
			primaryStage.show();
			
			FXMLSplashController.hideSplashScreen();
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();
                    FXMLDocumentController.methodupdateInterrupt();
                    System.exit(0);
                }
            });
						
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	//scans c:\ drive and if jdk folder found set its path
	public static boolean scanForJdk() {

		try {
		Consts.setStateProc(true);
		Consts.setjdkPath();
		
		while(Consts.isStateProcRunning() == true) {
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			} finally {
				
			}
		}
		
		
		String s[] = Consts.getResults().split("\n");
		String temp =null;
		
		for(String ss : s) {
			if(ss.contains("jdk")) {
				temp = ss;
			}
		}
		
		String t[] = temp.split(" ");
		
		for(String ss : t) {
			if(ss.contains("jdk")) {
				Consts.setpath("C:/Program Files/Java/"+ss);
				return true;
			}
		}
		
		
		} catch(NullPointerException ex) {
			return false;
		} finally {
			
		}
		
		return false;
		
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
}
