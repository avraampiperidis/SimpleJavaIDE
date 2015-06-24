package junk;


public class JunkComments {
	
	
	 /*
    if(space == true) { 	
     if(mainTextArea.getText(mainTextArea.getCaretPosition()-1, mainTextArea.getCaretPosition()).equals(" ")) {
     	  tempcount++;
   	  println("caretpos "+ caretpos);
   	  println("tempcount "+ tempcount);
      } else {
   	   caretpos = tempcount;
   	   tempcount = 0;
   	   space = false;
      }
    }
   
   */
   	
  /*
   if(mainTextArea.getCaretColumn() > 0) {
   	
   	if(mainTextArea.getText(mainTextArea.getCaretPosition()-1, mainTextArea.getCaretPosition()).equals("\n")) {
       space = true;
   	if(caretpos > 0) {
   			for(int i =0; i < caretpos; i++) {
   				
   				mainTextArea.insertText(mainTextArea.getCaretPosition()," ");
   			}
   		}
       }
   	
     }
     */
	
	
	
	
	/*
	   
 		private void addInt(CodeArea text) {
    	
    	if(addIntState == false) {
    		
    	    String temp = text.getText(text.getCaretPosition()-5, text.getCaretPosition());
    	    char[] c;   	
    	    c = temp.toCharArray();
    	
    	
    	    if(c[0] == ' ' || text.getCaretColumn() == 4) {
    		  if(c[1] == 'i') {
    			if(c[2] == 'n') {
    				if(c[3] == 't') {
    					if(c[4] == ' ') {
    						addIntState = true;
    						caretheight = text.getCaretPosition();
    					}
    				}
    			  }
    	    	}
    	  }
    	
    	} else {
    		
    		if(caretheight < text.getCaretPosition() && (caretheight+20) > text.getCaretPosition()) {
    			
    			
    			if(text.getText(text.getCaretPosition()-1, text.getCaretPosition()).equals("=")) {
    	    		//= -> get int nameVar word!
    				String out = text.getText(caretheight, text.getCaretPosition()-1);
    				
    				println("int variable-> "+out);
    				
    				
    				
    				
    				addIntState = false;
    	    	}
    			
    			
    		} else {
    			addIntState = false;
    		}
    		
    		
    	}
    	
    }
	 */
	
	
	/*
	mainTextArea.addEventHandler(KeyEvent.KEY_PRESSED, 
            new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
			
				if(event.getCode() == KeyCode.ENTER) {
    				
    				  
    			} 
			
		}	});
		*/
	
	
	

}
