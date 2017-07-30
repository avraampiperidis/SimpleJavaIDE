package com.protectsoft.ide;

import java.util.HashMap;

import org.fxmisc.richtext.*;

import com.protectsoft.ide.buildAndRun.Build;
import com.protectsoft.ide.buildAndRun.Run;
import com.protectsoft.ide.buildAndRun.SetJdkPath;


//--Constants Class--
public final class Consts {
	
	public Consts() {
		
	}
	
	
	
	private static String command = "cmd /C dir C:\\\"Program Files\"\\Java\\*";
	
	private static String path = "";
	
	private static boolean setJdkPath = false;
	
	//true as long a process is running
	private static boolean stateProcRun = false;
	
	private static String results = "";
	
	private static String textareacode = "";
	
	private static Thread buildAndRun;
		
	private static String mainClass = "";
	
	private static boolean loadOnce = false;
	
	private static String className;
	
	private static String intername;
		
	private static HashMap<String,String> classCode = new HashMap<>();
		
	private static HashMap<String,CodeArea> undoCode = new HashMap<>();
	
	private static HashMap<String,String> inter = new HashMap<>();
	
	private static String lastTabid = "";
	
	private static String currentTabid = "";
	
	private static String code = "";
	
	private static int id = 1;
	
	private static String args = "";
	
	private static String custom = "";
	
	
	
	
	//--> public static getters setters
	
	public static void setInter(String key,String value) {
		inter.put(key, value);
	}
	
	public static HashMap<String,String> getInter() {
		return inter;
	}
	
	public static void setCustom(String s) {
		custom = s;
	}
	
	public static String getCustom() {
		return custom;
	}
	
	public static void setArgs(String arg) {
		args = arg;
	}
	
	public static String getArgs() {
		return args;
		
	}
	
	public synchronized static String getlastTabid() {
    	return lastTabid;
    }
    
    public synchronized static void setlastTabid(String s) {
    	lastTabid = s;
    }
	
	public synchronized static String getClassName() {
		return className;
	}
	
	public synchronized static void setClassName(String s) {
		className = s;
	}
	
	public synchronized static String getInterName() {
		return intername;
	}
	
	public synchronized static void setInterName(String s) {
		intername = s;
	}
	
	public synchronized static boolean isLoadedOnce() {
		return loadOnce;
	}
	
	public synchronized static void setLoadOnce(boolean b) {
		loadOnce = b;
	}
	
    public synchronized static String getMainClass() {
    	return mainClass;
    }
    
    public synchronized static void setMainClass(String s) {
    	mainClass = s;
    }
	
	public synchronized static void setBuild() {
		buildAndRun = new Thread(new Build());
		buildAndRun.start();
	}
	
	public synchronized static void setRun() {
		buildAndRun = new Thread(new Run());
		buildAndRun.start();
	}
	
	public synchronized static void setjdkPath() {
		buildAndRun = new Thread(new SetJdkPath());
		buildAndRun.start();
	}
	
	public synchronized static String getCommand() {
		return command;
	}
	
	public synchronized static void setCommand(String s) {
		command = s;
	}
	
	public synchronized static String getPath() {
		return path;
	}
	
	public synchronized static void setpath(String s) {
		path = s;
	}
	
	public synchronized static boolean isjdkPathSet() {
		return setJdkPath;
	}
	
	public synchronized static void setJdkPath(boolean b) {
		setJdkPath = b;
	}
	
	public synchronized static int getId() {
		return id;
	}
	
	public synchronized static void setId() {
		id++;
	}
	
	public synchronized static boolean isStateProcRunning() {
		return stateProcRun;
	}
	
	public synchronized static void setStateProc(boolean b) {
		stateProcRun = b;
	}
	
	public synchronized static HashMap<String,String>  getClassCode() {	
		return classCode;	
	}
	
	public synchronized static void setClassCode(String key,String value) {
		classCode.put(key, value);
	}
	
	public synchronized static HashMap<String,CodeArea>  getundoCode() {	
		return undoCode;	
	}
	
	public synchronized static void setundoCode(String key,CodeArea value) {
		undoCode.put(key, value);
	}
	
	public synchronized static String getResults() {
		return results;
	}
	
	public synchronized static void setResults(String res) {
		results = res;
	}
	
	public synchronized static String getTextAreaCode() {
		return textareacode;
	}
	
	public synchronized static void setTextAreaCode(String areaCode) {
		textareacode = areaCode;
	}
	
	public synchronized static String getCode() {
		return code;
	}
	
	public synchronized static void setCode(String c) {
		code = c;
	}
	
	public synchronized static String getCurrentTabId() {
		return currentTabid;
	}
	
	public synchronized static void setCurrentTabId(String id) {
		currentTabid = id;
	}
	
	
	

}