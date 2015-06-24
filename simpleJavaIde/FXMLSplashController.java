package simpleJavaIde;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLSplashController {
	
	private static Stage stage;
	
	private static AnchorPane root;
	
	private static ImageView imageview;
	
	private static Image image;
		
	public static float pbalLevel = 0.1f;
	
	
	public void initialize(URL url, ResourceBundle rb) {
		 
	}
	
	
	
	public static void showSplashScreen() {
		
		
		 root = new AnchorPane();
		 Scene scene = new Scene(root,200,257);		 
		 
		 image = new Image("simpleJavaIde/assets/logomed.png");
		 
		 imageview = new ImageView();
		 
		 imageview.setImage(image);
		 
		 root.getChildren().add(imageview);
		 
		 
		 stage = new Stage();		 
		 stage.setScene(scene);
		 stage.show();
		 
		 
		 
	}
	
	public static void setText(String s) {
		
		
	}
	
	public static void hideSplashScreen() {
		stage.hide();
	}
	
	

}
