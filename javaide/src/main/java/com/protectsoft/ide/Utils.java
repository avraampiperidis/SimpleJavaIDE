package com.protectsoft.ide;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map.Entry;


public class Utils {
	
	
	public Utils() {
		
	}
	
	
	
	 public static void findMainClass(String text) {
	    	
		 try {
	    	if(text.contains("main(String")) {
	    		
	    		String[] temp = text.split(" ");
	    		for(int i =0; i < temp.length; i++) {
	    			if(temp[i].equals("class")) {
	    				Consts.setMainClass(temp[++i]);
	    				
	    			   }
	    			
	    		  }
	    		
	    	 }
		 } catch(Exception ex) {
			 
		 }
	    	
	    }
	 
	 public static String getInterName(String text) {
		 try {
			 if(text.contains("interface")) {
				 String temp[] = text.split(" ");
				 for(int i =0; i< temp.length; i++) {
					 if(temp[i].equals("interface")) {
						 return fixInterName(temp[++i].trim());
					 }
				 }
			 }
		 } catch(Exception ex) {
			 
		 }
		 return "";
	 }
	 
	 
	 private static String fixInterName(String s) {
		 
		 if(s.substring(s.length()-1,s.length()).equals("{")) {
			 return s.substring(0, s.length()-1);
		 }		 
		 String t = ""; 
		 char[] ch = s.toCharArray();
		 
		 for(int i =0; i < ch.length; i++) {
			 if(ch[i] != '<') {
				 t += ch[i];		
			 } else {
				 return t;
			 }
		 }
		 
		 return s;
	 }
	 
	 
	 public static String getClassName(String text) {
	    	
		    try {
		    	if(text.contains("class")) {
		    	String temp[] = text.split(" ");
		    	 for(int i =0; i < temp.length; i++) {
		    		if(temp[i].equals("class")) {
		    			
		    			return fixClassName(temp[++i].trim());
		    			//return temp[++i];
		    		 }
		    	 }
		    	}
		    	
		       } catch(Exception ex) {
		    	   
		       }
		       return "";
		    
		    }
	 
	 
	 
	 
	 private static String fixClassName(String s) {
		 
		 if(s.substring(s.length()-1,s.length()).equals("{")) {
			 return s.substring(0, s.length()-1);
		 }		 
		 String t = ""; 
		 char[] ch = s.toCharArray();
		 
		 for(int i =0; i < ch.length; i++) {
			 if(ch[i] != '<') {
				 t += ch[i];		
			 } else {
				 return t;
			 }
		 }
		 
		 return s;
		 
	 }
	 
	 
	 
	 public static String getTabcode(String id) {
		 
		 if(id.contains("inter")) {
			 
			 Iterator<Entry<String, String>> inter = Consts.getInter().entrySet().iterator();
				
		    	while(inter.hasNext()) { 		    		
					Entry<String, String> pair = inter.next(); 		    		
					if(pair.getKey().toString().equals(id)) {
						return pair.getValue().toString();
					} 		    		
		    	}
			 
		 } else {
	    	
	    	Iterator<Entry<String, String>> it = Consts.getClassCode().entrySet().iterator();
			
	    	while(it.hasNext()) { 		    		
				Entry<String, String> pair = it.next(); 		    		
				if(pair.getKey().toString().equals(id)) {
					return pair.getValue().toString();
				} 		    		
	    	}
	    	
		 }
	    	
	    	return "";
	    	
	    }
	 
	 
	 
	 
	 public static void deleteJavaAndClassFiles() {
		 
	    	    Iterator<Entry<String, String>> it = Consts.getClassCode().entrySet().iterator();
		    	
		    	while(it.hasNext()) {
		    			
		       try {
		    	   
					Entry<String, String> pair = it.next();										
					
		    		File file = new File(Utils.getClassName(pair.getValue().trim())+".java");
		    		Path path  = file.toPath();		
					
					Files.delete(path);
														
					file = new File(Utils.getClassName(pair.getValue().trim())+".class");
					path = file.toPath();
					Files.delete(path);
					
					
		        } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					
				}
		    }
		    	
		    	
		    	 Iterator<Entry<String, String>> inter = Consts.getInter().entrySet().iterator();
			    	
			    	while(inter.hasNext()) {
			    			
			       try {
			    	   
						Entry<String, String> pair = inter.next();										
						
			    		File file = new File(Utils.getInterName(pair.getValue().trim())+".java");
			    		Path path  = file.toPath();		
						
						Files.delete(path);
															
						file = new File(Utils.getInterName(pair.getValue().trim())+".class");
						path = file.toPath();
						Files.delete(path);
												
			        } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						
					}
			    }
		    	
			
	 }
	 
	 
	 public static void saveToFile() {
	    	
	    	try {
	    		
	    	Iterator<Entry<String, String>> it = Consts.getClassCode().entrySet().iterator();
	    	
	    	while(it.hasNext()) {
	    		
	    		
				Entry<String, String> pair = it.next();
	    		    		
	    		File file = new File(Utils.getClassName(pair.getValue().toString())+".java");
				FileOutputStream fos = new FileOutputStream(file);
				
				if(!file.exists()) {
					file.createNewFile();
				}
				
				byte[] content = pair.getValue().toString().getBytes();
				
				fos.write(content);
				
				fos.flush();
				fos.close();
				
	    	}
	    	
	    	Iterator<Entry<String, String>> inter = Consts.getInter().entrySet().iterator();
	    	
	    	while(inter.hasNext()) {
	    		
	    		
				Entry<String, String> pair = inter.next();
	    		    		
	    		File file = new File(Utils.getInterName(pair.getValue().toString())+".java");
				FileOutputStream fos = new FileOutputStream(file);
				
				if(!file.exists()) {
					file.createNewFile();
				}
				
				byte[] content = pair.getValue().toString().getBytes();
				
				fos.write(content);
				
				fos.flush();
				fos.close();
				
	    	}
			
				
				
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
	 
	 
	 
	 
	 public static String getTime() {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			return sdf.format(cal.getTime());
		}
	 
	
	

}
