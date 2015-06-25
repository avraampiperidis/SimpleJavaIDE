package simpleJavaIde;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;



import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;





import org.fxmisc.richtext.*;

import simpleJavaIde.model.ArrowFactory;
import simpleJavaIde.model.CodeAreaFinalValues;

public class FXMLDocumentController implements Initializable {
	
	
	@FXML
	private Button runbutton;
	
	@FXML
	private ImageView runbuttonIcon;
	
	@FXML
	private ImageView buildbuttonIcon;	
	
	@FXML
	private Button buildbutton;
	
	@FXML
	private CodeArea mainTextArea;
	
	@FXML
	private  StyleClassedTextArea output;
	
	@FXML
	private TabPane tabpane;	
	
	@FXML
	private Button newfile;
	
	@FXML 
	private BorderPane borderpane;
	
	@FXML
	private Button save;
	
	@FXML
	private Button redobutton;
	
	@FXML
	private Button undobutton;
	
	@FXML
	private Button openfile;
	
	@FXML
	private Button cleanfile;
	
	@FXML
	private ImageView cleanfileicon;
	
	@FXML
	private ImageView undobuttonicon;
	
	@FXML
	private ImageView redobuttonicon;
	
	@FXML
	private ImageView newfilebutton;
	
	@FXML
	private ImageView savebuttonicon;
	
	@FXML
	private ImageView openbuttonicon;
	
	
	@FXML
	private Tab tab;
	
	@FXML
	private  TreeView<String> treeview;
	
	
	
	
	
	
	//-->non fxml private variables
	
	private  Node classicon;
	
	private Node interfaceIcon;
	
	private Pattern pattern;
	
	private Matcher matcher;
	
	private int caretpos =0;
	
	private boolean bracket = false;
	
	public static boolean openfiletab = false;
	
	public static boolean isInterface = false;
	
	public static boolean isImport = false;
	
	private static Thread methodupdate;
	
	public static String importString = "";
	
	
	
	
	//initialize only once
	{ 
		this.runbuttonIcon = new ImageView("simpleJavaIde/assets/runbutton.png");
    	this.buildbuttonIcon = new ImageView("simpleJavaIde/assets/buildbutton.png");
    	this.undobuttonicon = new ImageView("simpleJavaIde/assets/undobutton.png");
    	this.redobuttonicon = new ImageView("simpleJavaIde/assets/redobutton.png");
    	this.newfilebutton = new ImageView("simpleJavaIde/assets/newfilebutton.png");
    	this.savebuttonicon = new ImageView("simpleJavaIde/assets/savebutton.png");
    	this.openbuttonicon = new ImageView("simpleJavaIde/assets/openbutton.png");
    	this.cleanfileicon = new ImageView("simpleJavaIde/assets/cleanbutton.png");
    	
    	classicon = new ImageView("simpleJavaIde/assets/classicon.png");
    	interfaceIcon = new ImageView("simpleJavaIde/assets/intericon.png");
    	
    	  	
	}
	
	

    @SuppressWarnings("static-access")
	@Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	
    	 IntFunction<Node> numberFactory = LineNumberFactory.get(mainTextArea);
         IntFunction<Node> arrowFactory = new ArrowFactory(mainTextArea.currentParagraphProperty());
         IntFunction<Node> graphicFactory = line -> {
             HBox hbox = new HBox(
                 numberFactory.apply(line),
                 arrowFactory.apply(line));
             hbox.setAlignment(Pos.CENTER_LEFT);
             return hbox;
         };
    	
           
    	
    	mainTextArea.setParagraphGraphicFactory(graphicFactory);
    	
    	
    		//text propertyChange listener
    		mainTextArea.textProperty().addListener((obs, oldText, newText) -> {
    			
    			  		
    			Consts.setCode(mainTextArea.getText());
    			
    			if(mainTextArea.getText(mainTextArea.getCaretPosition()-1, mainTextArea.getCaretPosition()).equals(" ")) {
    				mainTextArea.getUndoManager().mark();
    				Consts.setlastTabid(tab.getId());
    				Consts.setundoCode(tab.getId(),mainTextArea);;
    			}
    			
    			Consts.setCurrentTabId(tab.getId());
    			
    			mainTextArea.setStyleSpans(0, CodeAreaFinalValues.getComputeHighLighting(newText));
    		  			    			
    			addBracketFill(mainTextArea);
    			
    			if(!(tab.getId().contains("inter"))) {
    				Utils.findMainClass(mainTextArea.getText());
    			}
    			
    			if(Utils.getClassName(mainTextArea.getText()).length() <= 0 && !(tab.getId().contains("inter"))) {
    				
    			} else {
    				
    				
    				Iterator<Entry<String, String>> it = Consts.getClassCode().entrySet().iterator();
    		    					
    		    	while(it.hasNext()) { 		    		
    					Entry<String, String> pair = it.next(); 		    		
    					if(pair.getKey().toString().equals(tab.getId())) {
    						tab.setText(Utils.getClassName(mainTextArea.getText()));
    						Consts.setClassCode(pair.getKey().toString(), mainTextArea.getText());
    					} 		    		
    		    	}
    		    	
    			}
    			
    			if(Utils.getInterName(mainTextArea.getText()).length() <= 0  && !(tab.getId().contains("tab"))) {
    				
    			} else {
    				    				
    				Iterator<Entry<String, String>> inter = Consts.getInter().entrySet().iterator();
    		    					
    		    	while(inter.hasNext()) { 		    		
    					Entry<String, String> pair = inter.next(); 		    		
    					if(pair.getKey().toString().equals(tab.getId())) {
    						tab.setText(Utils.getInterName(mainTextArea.getText()));
    						Consts.setInter(pair.getKey().toString(), mainTextArea.getText());
    					} 		    		
    		    	}
    		    	
    			}
    			
    			if(isImport == true) {
    				
    				isImport = false;
    				insertImportText(mainTextArea);   				
    				
    			}
    			
    			  	
    		});
    		
    		
       
         //loads only once at simpleJavaIde startup		
         if(Consts.isLoadedOnce() == false) {	
        	 
        	 //thread start
        	 methodUpdater();
             
        	 //append sample code     	
        	 //and setup things only one time during simpleJavaIde startup
        	 	if(Main.scanForJdk() == true) {
        	 				//sdk has been set
        	 				Consts.setJdkPath(true);
     					} else {
     							//ask the user for jdk path
     							if(inputDialog() == true) {
     									//path input success
     									Consts.setJdkPath(true);
     								} else {
     										Consts.setJdkPath(true);	
     											//no jdk was given
     											//disable build and run buttons
     											buildbutton.setDisable(true);
     											runbutton.setDisable(true);
     									}
     							
     					}
        	 	
        	 
        	 mainTextArea.appendText(CodeAreaFinalValues.getSampleCode());
         	 output.setParagraphGraphicFactory(LineNumberFactory.get(output));
        	 Consts.setLoadOnce(true);
        	 tab.setText(Consts.getMainClass());
        	 
        	 Consts.setClassCode(tab.getId().toString(), mainTextArea.getText());
        	 
        	 tabpane.setTabClosingPolicy(tabpane.getTabClosingPolicy().ALL_TABS);
        	 
        	 //set treeview items
        	 TreeItem<String> rootclass = new TreeItem<String> (Utils.getClassName(mainTextArea.getText()), classicon);
        	 rootclass.setExpanded(true);
        	 treeview.setStyle(" -fx-control-inner-background: #2A2A2A; -fx-font-size: 12px;  -fx-font-weight: Bold;  -fx-font-family: Consolas;");
        	 treeview.setRoot(rootclass);
        	
        	 Iterator<Entry<String, String>> it = Consts.getClassCode().entrySet().iterator();
         	
         	 while(it.hasNext()) {
         		
     			Entry<String, String> pair = it.next();
     			
     			if(pair.getKey().toString().equals(tab.getId())) {
     				
     				pattern = Pattern.compile(CodeAreaFinalValues.getMethodpattern());
    				matcher = pattern.matcher(mainTextArea.getText());
    				
    				while(matcher.find()) {   					
    					TreeItem<String> method = new TreeItem<String> (matcher.group(0).toString().substring(0,matcher.group(0).toString().length()-1));
    					rootclass.getChildren().add(method);
    					
    				}
     				
     			}
     			
         	 }
        	         	 
        	 //buttons tooltips
         	{
       		 
       		 Tooltip tip = new Tooltip("Build and run main");
       		 this.runbutton.setTooltip(tip);
       		 tip = new Tooltip("build file");
       		 this.buildbutton.setTooltip(tip);
       		 tip = new Tooltip("create new class file");
       		 this.newfile.setTooltip(tip);
       		 tip = new Tooltip("save all");
       		 this.save.setTooltip(tip);
       		 tip = new Tooltip("open java class file");
       		 this.openfile.setTooltip(tip);
       		 tip = new Tooltip("deletes java files and classes \nfrom drive path");
       		 this.cleanfile.setTooltip(tip);
       		 tip = new Tooltip("undo button");
       		 this.undobutton.setTooltip(tip);
       		 tip = new Tooltip("redo button");
      		 this.redobutton.setTooltip(tip);
       		  
       	   }
        	 
        	 
         } else {
        	 if(openfiletab == true) {
        		 if(isInterface == true) {
        			 mainTextArea.appendText(CodeAreaFinalValues.getNewInterfaceCode());
        			 Consts.setClassName("");      			 
        			 isInterface = false;
        			 
        		 } else {
        			 
        			 mainTextArea.appendText(CodeAreaFinalValues.getTempClassCode()); 
        			 Consts.setClassName("");
        			 openfiletab = false;
        		 
        		 }
        	 } else {
        		 
        		 	mainTextArea.appendText(CodeAreaFinalValues.getNewClassCode());
        		 	Consts.setClassName("");
        		 
        	 }
         }
         
         
		
    	
    } //initialize end
    
    
    
    public static boolean inputDialog() {
		
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Jdk Not Found!                      ");
		dialog.setHeaderText("set a valid path for Jdk!               \nEg. C:\\Program Files\\Java\\jdk1.8.0_45\\            ");

		Optional<String> result = dialog.showAndWait();
		
		if (result.isPresent()){
			
			
		    if(result.get().contains("jdk") && !result.get().isEmpty()) {
		    	String temp;
		    	temp = result.get().replace('\\','/');
		    	Consts.setpath(temp);
		    	
		    	return true;
		    }
		    
		    
		}
		
		return false;
		
	}
    
    
    
    
    private void addBracketFill(CodeArea text) {
    	
    	if(text.getText(text.getCaretPosition()-1, text.getCaretPosition()).equals("}")) {
    		bracket = false;
    	}
    	
    	 if(text.getText(text.getCaretPosition()-1, text.getCaretPosition()).equals("\n")) {
             if(bracket == true) {
             	
             	if(caretpos > 0) {
             		bracket = false;
             		text.insertText(text.getCaretPosition(),"\n");
         			for(int i =0; i < caretpos-8; i++) {
         				text.insertText(text.getCaretPosition()," ");
         				}
             		}
             	
             	   text.insertText(text.getCaretPosition(),"}");
             	
                  }
             }
    	   		
         	if(text.getText(text.getCaretPosition()-1, text.getCaretPosition()).equals("{")) {

         		caretpos = text.getCaretColumn();
                bracket = true;
         
         	}    	
    }
    
    
    
    @FXML
    private void openfile() {
    	
    	try {
    		
    	openfiletab = true;	
    	
    	FileChooser chooser = new FileChooser();
    	FileChooser.ExtensionFilter extfilter = new FileChooser.ExtensionFilter("Java files (*.java)","*.java");
    	chooser.setTitle("open java file");
    	chooser.getExtensionFilters().add(extfilter);
    	
    	File file = chooser.showOpenDialog(new Stage());
    	
    	FileInputStream fis = new FileInputStream(file.toString());
		  	
    	InputStreamReader isr = new InputStreamReader(fis);
		
		BufferedReader br = new BufferedReader(isr);
		
		String line = " ";
	    String temp = "";
	    
		while((line = br.readLine()) != null) {
			temp += line + "\n";
		}
		
		if(!(Utils.getClassName(temp).length() <=0 )) {
			CodeAreaFinalValues.setTempClassCode(temp);
		
			Tab tab;
		
			Consts.setClassName(Utils.getClassName(temp));
		
			Consts.setClassCode("tab"+Consts.getId(),CodeAreaFinalValues.getTempClassCode());					
		
			tab = FXMLLoader.load(getClass().getResource("view/FXMLTabs.fxml"));
			tab.setClosable(true);
    		tab.setText(Utils.getClassName(temp));				
    		tab.setId("tab"+Consts.getId());
    		Consts.setId();
		
    		tab.setClosable(true);
    	
		 	tabpane.getTabs().add(tab);
		}
    	
		br.close();
		isr.close();
		
		
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
    
    
    
    @FXML
    private void setArguments() {
    	
    	TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("      Arguments     ");
		dialog.setHeaderText("Enter Arguments with space between              ");

		Optional<String> result = dialog.showAndWait();
		
		if (result.isPresent()){
			
			if(result.get().length() <=0) {
				
			} else {
				
				String args = result.get();
				Consts.setArgs(args);
				System.out.println(Consts.getArgs());
				
				
			}
			
		}
    	
    }
    
    
    
    @FXML
    private void buildFile() {
    	 
    	
    	clearout();
    	Consts.setResults(" ");
    	Consts.setStateProc(true);
    	    	
    	Utils.saveToFile();
    	
    	build();
    	
    	while(Consts.isStateProcRunning() == true) {
    		try {
				Thread.currentThread();
				Thread.sleep(150);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			} finally {
				
			}
    		
    	}
    	
    	
    	
        if(Consts.getResults().contains("Error") || Consts.getResults().contains("Exception") || Consts.getResults().contains("error")) {
    			println(Utils.getTime() + " "+Consts.getResults());    			
    	} else {
    		    println("build completed without errors at: " + Utils.getTime());
    	}
    	
    	 	
    }
    
    
    @FXML
    private void cleanButton() {
    	Utils.deleteJavaAndClassFiles();
    }
    
    @FXML
    private void savefile() {
    	
    Utils.saveToFile();	
    	
    }
    
    
    @FXML
    private void newInterface() {
    		  	
    	try {
    		openfiletab = true;
    		isInterface = true;
    		
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("      New     ");
			dialog.setHeaderText("Enter Interface name             ");

			Optional<String> result = dialog.showAndWait();
			
			if (result.isPresent()){
				
				if(result.get().length() <=0) {
					
				} else {
					Tab tab;
					
					String interfacename = result.get();
					Consts.setInterName(result.get());
					Consts.setInter("inter"+Consts.getId(), CodeAreaFinalValues.getNewInterfaceCode());					
					
					tab = FXMLLoader.load(getClass().getResource("view/FXMLTabs.fxml"));
					tab.setClosable(true);
			    	tab.setText(interfacename);				
			    	tab.setId("inter"+Consts.getId());
			    	Consts.setId();
					
			    	tab.setClosable(true);
			    	
					tabpane.getTabs().add(tab);
				}
				
			}
						
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    }
    
    @FXML
    private void newClassFile() {
    	
			try {
				
				
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("      New     ");
				dialog.setHeaderText("Enter Class file name              ");

				Optional<String> result = dialog.showAndWait();
				
				if (result.isPresent()){
					
					if(result.get().length() <=0) {
						
					} else {
						Tab tab;
						String className = result.get();
						Consts.setClassName(result.get());
						
						Consts.setClassCode("tab"+Consts.getId(), CodeAreaFinalValues.getNewClassCode());					
						
						tab = FXMLLoader.load(getClass().getResource("view/FXMLTabs.fxml"));
						tab.setClosable(true);
				    	tab.setText(className);				
				    	tab.setId("tab"+Consts.getId());
				    	Consts.setId();
						
				    	tab.setClosable(true);
				    	
						tabpane.getTabs().add(tab);
					}
					
				}
							
				
			} catch (IOException e) {
				e.printStackTrace();
			}
					
    }
    
    
    
    
    
    @FXML
    private void buildAndRun() {
    	clearout();
    	Consts.setResults(" ");
    	Consts.setStateProc(true);
    	    	
    	Utils.saveToFile(); 	
    	build();
    	
    	while(Consts.isStateProcRunning() == true) {
    		try {
				Thread.currentThread();
				Thread.sleep(150);
				
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			} finally {
				
			}
    		
    	}
    	
    	
    	
        if(Consts.getResults().contains("Error") || Consts.getResults().contains("Exception") || Consts.getResults().contains("error")) {
    			println(Consts.getResults());
    	} else {
    	   Consts.setStateProc(true);	
    	   run();
    	  
    	   while(Consts.isStateProcRunning() == true) {
    		   try {
				Thread.currentThread();
				Thread.sleep(150);
				
				
				if(Consts.getResults().equals(output.getText())) {
					
				} else {
					clearout();
					println(Consts.getResults());					
				}
				
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
    	   }
   	   
    	   
    	}
    			  	
			
    }
    
    
    @FXML
    private void undoManager() {
    	
    	Iterator<Entry<String, CodeArea>> it = Consts.getundoCode().entrySet().iterator();
    	
    	while(it.hasNext()) {
    		   	
			Entry<String, CodeArea> pair = it.next();
			
			if(pair.getKey().toString().equals(Consts.getCurrentTabId())) {
				
				((CodeArea)pair.getValue()).undo();
				
			}
    	}
    	   	
    }
    
    
    @FXML
    private void redoManager() {
    	Iterator<Entry<String, CodeArea>> it = Consts.getundoCode().entrySet().iterator();
    	
    	while(it.hasNext()) {
    		   	
			Entry<String, CodeArea> pair = it.next();
			
			if(pair.getKey().toString().equals(Consts.getCurrentTabId())) {
				
				((CodeArea)pair.getValue()).redo();
				
			}
    	}
    }
    
    
    
    @FXML
   	private void closeprogram(ActionEvent event) {
    	methodupdate.interrupt();
   		System.exit(0);
   	}
    
    
    @FXML
    private void aboutwindow() {
    	
    	 AnchorPane root;
    	 Stage stage;
         Image image;
         ImageView imageview;
 
    	 image = new Image("simpleJavaIde/assets/aboutpic.png");
		 
		 imageview = new ImageView();
		 
		 imageview.setImage(image);
		 
		 
		 root = new AnchorPane();
		 Scene scene = new Scene(root,254,400);		 
		 
		 root.getChildren().add(imageview);
		 
		 stage = new Stage();		 
		 stage.setScene(scene);
		 stage.show();
		 
    	
    	
    }
    
    
	
    public void build() {  	  	 
    	 Consts.setBuild();	    	 	
    }
    
    
    
    public void run() {
    	Consts.setRun();
    }
	

   
	//--print methods
    
    
	private  void println(String text) {
		output.appendText(text);
		output.appendText("\n");
	}
	
	private void clearout() {
		output.clear();
	}
	
	
	@SuppressWarnings("unused")
	private void println(int i) {
		output.appendText(String.valueOf(i));
		output.appendText("\n");
	}
	
	@SuppressWarnings("unused")
	private void println(float f) {
		output.appendText(String.valueOf(f));
		output.appendText("\n");
	}
	
	
	@SuppressWarnings("unused")
	private void println(double d) {
		output.appendText(String.valueOf(d));
		output.appendText("\n");
	}
	
	
	@SuppressWarnings("unused")
	private void println(char c) {
		output.appendText(String.valueOf(c));
		output.appendText("\n");
	}
	
	@SuppressWarnings("unused")
	private void println(long l) {
		output.appendText(String.valueOf(l));
		output.appendText("\n");
	}
	
	
	@SuppressWarnings("unused")
	private void println(Object o) {
		output.appendText(o.toString());
		output.appendText("\n");
	}
	
	
	public static void methodupdateInterrupt() {
		 methodupdate.interrupt();
	}
	
	
	 public void checkformethods(String code) {
	    	
			if(code.isEmpty()) {
				
			} else {
			
			if(Consts.getCurrentTabId().contains("inter")) {
				
				pattern = Pattern.compile(CodeAreaFinalValues.getMethodpattern());
				matcher = pattern.matcher(Consts.getCode().trim());
				TreeItem<String> rootclass = new TreeItem<String> (Utils.getInterName(code), interfaceIcon);
				rootclass.setExpanded(true);
			
				treeview.setStyle(" -fx-control-inner-background: #2A2A2A; -fx-font-size: 12px;  -fx-font-weight: Bold;  -fx-font-family: Consolas;");				
				treeview.setRoot(rootclass);
     	
     	    	while(matcher.find()) {   					
					TreeItem<String> method = new TreeItem<String> (matcher.group(0).toString().substring(0,matcher.group(0).toString().length()-1));
					rootclass.getChildren().add(method);
	
					}
				
			} else {
			
				pattern = Pattern.compile(CodeAreaFinalValues.getMethodpattern());
				matcher = pattern.matcher(Consts.getCode().trim());
				TreeItem<String> rootclass = new TreeItem<String> (Utils.getClassName(code), classicon);
				rootclass.setExpanded(true);
			
				treeview.setStyle(" -fx-control-inner-background: #2A2A2A; -fx-font-size: 12px;  -fx-font-weight: Bold;  -fx-font-family: Consolas;");				
				treeview.setRoot(rootclass);
     	
     	    	while(matcher.find()) {   					
					TreeItem<String> method = new TreeItem<String> (matcher.group(0).toString().substring(0,matcher.group(0).toString().length()-1));
					rootclass.getChildren().add(method);
	
					}
			
			
				}
			}
			
		
	 }
	
	public void methodUpdater() {
		  methodupdate = new Thread() {
          	public void run() {
          		
          		while(true) {
          		try {
          			Thread.currentThread();
					Thread.sleep(3500);
          			
          			Platform.runLater(new Runnable() {
          			      @Override public void run() {
          			        //Update UI here   
          			    	 
          			    	 checkformethods(Utils.getTabcode(Consts.getCurrentTabId()));
          			    	 
          			      }
          			    });
          			
          			
  					
  				} catch (InterruptedException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				} finally {
  					
  				}
          		
          		}
          		
          	}
          };
          methodupdate.start();
	}
	
	
	
	
	private void insertImportText(CodeArea text) {
		
		text.insertText(0, "import "+ importString+ "\n");
        		
		importString = "";
		
		
	}
	
	//------------import @FXML methods---------------||
	//-----------------------------------------------||
	
		@FXML
		private MenuItem javaappletid;
	
	    @FXML
	    private void javaapplet() {
	    	
	    	isImport = true;
	        importString = javaappletid.getText();
	    		
	    }
	    
	    
	    @FXML
	    private MenuItem javaawtid;
	    
	    @FXML
	    private void javaawt() {
	    	
	    	isImport = true;	    	
	        importString = javaawtid.getText();
	    		
	    	
	    }
	    
	    
	    
	    @FXML
	    private MenuItem javaawtcid;
	    
	    @FXML
	    private void javaawtc() {
	    	
	    	isImport = true;	    	
	        importString = javaawtcid.getText();	    		
	    	
	    }
	    
	    
	    @FXML
	    private MenuItem javaawtdid;
	    
	    @FXML
	    private void javaawtd() {
	    	
	    	isImport = true;	    	
	        importString = javaawtdid.getText();	    		
	    	
	    }
	    
	    
	    @FXML
	    private MenuItem javaawtdndid;
	    
	    @FXML
	    private void javaawtdnd() {
	    	
	    	isImport = true;	    	
	        importString = javaawtdndid.getText();	    		
	    	
	    }
	    
	    @FXML
	    private MenuItem javaawtdndpid;
	    
	    @FXML
	    private void javaawtdndp() {
	    	
	    	isImport = true;	    	
	        importString = javaawtdndpid.getText();	    		
	    	
	    }
	    
	    @FXML
	    private MenuItem javaawteventid;
	    
	    @FXML
	    private void javaawtevent() {
	    	
	    	isImport = true;	    	
	        importString = javaawteventid.getText();	    		
	    	
	    }
	    
	    
	    @FXML
	    private MenuItem javaioid;
	    
	    @FXML
	    private void javaio() {
	    	
	    	isImport = true;	    	
	        importString = javaioid.getText();	    		
	    	
	    }
	    
	    
	    @FXML
	    private MenuItem javalangid;
	    
	    @FXML
	    private void javalang() {
	    	
	    	isImport = true;	    	
	        importString = javalangid.getText();	    		
	    	
	    }
	    
	    
	    @FXML
	    private MenuItem javamathid;
	    
	    @FXML
	    private void javamath() {
	    	
	    	isImport = true;	    	
	        importString = javamathid.getText();	    		
	    	
	    }
	    
	    @FXML
	    private MenuItem javanetid;
	    
	    @FXML
	    private void javanet() {
	    	
	    	isImport = true;	    	
	        importString = javanetid.getText();	    		
	    	
	    }
	    
	    @FXML
	    private MenuItem javanioid;
	    
	    @FXML
	    private void javanio() {
	    	
	    	isImport = true;	    	
	        importString = javanioid.getText();	    		
	    	
	    }
	    
	    @FXML
	    private MenuItem javaniocid;
	    
	    @FXML
	    private void javanioc() {
	    	
	    	isImport = true;	    	
	        importString = javaniocid.getText();	    		
	    	
	    }
	    
	    @FXML
	    private MenuItem javaniofid;
	    
	    @FXML
	    private void javaniof() {
	    	
	    	isImport = true;	    	
	        importString = javaniofid.getText();	    		
	    	
	    }
	    
	    
	    @FXML
	    private MenuItem javasecid;
	    
	    @FXML
	    private void javasec() {
	    	
	    	isImport = true;	    	
	        importString = javasecid.getText();	    		
	    	
	    }
	    
	    @FXML
	    private MenuItem javasqlid;
	    
	    @FXML
	    private void javasql() {
	    	
	    	isImport = true;	    	
	        importString = javasqlid.getText();	    		
	    	
	    }
	    
	    @FXML
	    private MenuItem javatxtid;
	    
	    @FXML
	    private void javatxt() {
	    	
	    	isImport = true;	    	
	        importString = javatxtid.getText();	    		
	    	
	    }
	    
	    @FXML
	    private MenuItem javatimeid;
	    
	    @FXML
	    private void javatime() {
	    	
	    	isImport = true;	    	
	        importString = javatimeid.getText();	    		
	    	
	    }
	    
	    @FXML
	    private MenuItem javautilid;
	    
	    @FXML
	    private void javautil() {
	    	
	    	isImport = true;	    	
	        importString = javautilid.getText();	    		
	    	
	    }
	    
	    
	      

}
