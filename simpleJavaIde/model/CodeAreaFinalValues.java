package simpleJavaIde.model;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

import simpleJavaIde.Consts;

public  class CodeAreaFinalValues {
	
	private static String tempClassCode = "";
	
	
	public CodeAreaFinalValues() {
		
	}
	
	//sample code
	private static final String sampleCode = String.join("\n", new String[] {
	        "    ",
	        "",
	        "import java.util.*;",
	        "",
	        "public class Foo  {",
	        "",
	        "    /*",
	        "     * multi-line comment",
	        "     */",
	        "    public static void main(String[] args) {",
	        "        // single-line comment",
	        "        ", 
	        "              ",
	        "           System.out.println(\"Hello Java World!\");   ",
	        "              ",
	        "        ",
	        "    }",
	        "",
	        "}"
	    });
	
	
	
	
	 private static final String[] KEYWORDS = new String[] {
         "abstract", "assert", "boolean", "break",
         "case", "catch" , "class", "const",
         "continue", "default", "do", "else",
         "enum", "extends", "final", "finally",
         "for", "goto", "if", "implements", "import",
         "instanceof", "interface", "native",
         "new", "package", "private", "protected", "public",
         "return", "static", "strictfp", "super",
         "switch", "synchronized", "this", "throw", "throws",
         "transient", "try", "void", "volatile", "while"
	 };
	 
	 private static final String[] PRIMITIVE_TYPES = new String[] {
		 "byte","char", "double", "float", "int", "long", "short" , "String"
	 };
	
	 
	
	
	    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", CodeAreaFinalValues.getKEYWORDS()) + ")\\b";
	    private static final String PRIMITIVE_PATTERN = "\\b(" + String.join("|", CodeAreaFinalValues.getPRIMITIVE()) + ")\\b";
	    private static final String PAREN_PATTERN = "\\(|\\)";
	    private static final String BRACE_PATTERN = "\\{|\\}";
	    private static final String BRACKET_PATTERN = "\\[|\\]";
	    private static final String SEMICOLON_PATTERN = "\\;";
	    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
	    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
	    private static final String NUMBERS_PATTERN = "(\\d+)";
	    private static final String METHOD_PATTTERN = "(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";
	    private static final String VARIABLE_PATTERN = ".+\\s(.+?)(;|=)";

	    
	    private static final Pattern PATTERN = Pattern.compile(
	            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
	            + "|(?<PRIMITIVE>" + PRIMITIVE_PATTERN + ")"
	            + "|(?<PAREN>" + PAREN_PATTERN + ")"
	            + "|(?<BRACE>" + BRACE_PATTERN + ")"
	            + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
	            + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
	            + "|(?<STRING>" + STRING_PATTERN + ")"
	            + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
	            + "|(?<NUMBERS>" + NUMBERS_PATTERN + ")"
	    );
	 
	 
	    
	   
	    
	    
	    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
	    	
	        Matcher matcher = CodeAreaFinalValues.getPattern().matcher(text);
	        int lastKwEnd = 0;
	        StyleSpansBuilder<Collection<String>> spansBuilder
	                = new StyleSpansBuilder<>();
	        while(matcher.find()) {
	            String styleClass =
	                    matcher.group("KEYWORD") != null ? "keyword" :
	                    matcher.group("PRIMITIVE") != null ? "primitive" :
	                    matcher.group("PAREN") != null ? "paren" :
	                    matcher.group("BRACE") != null ? "brace" :
	                    matcher.group("BRACKET") != null ? "bracket" :
	                    matcher.group("SEMICOLON") != null ? "semicolon" :
	                    matcher.group("STRING") != null ? "string" :
	                    matcher.group("COMMENT") != null ? "comment" :
	                    matcher.group("NUMBERS")  != null ? "numbers" :	
	                    null; /* never happens */ assert styleClass != null;
	            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
	            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
	            lastKwEnd = matcher.end();
	        }
	        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
	        return spansBuilder.create();
	        
	    }
	    
	    
	
	
	//--public methods -->>
	    
	public final static String getSampleCode() {
		return sampleCode;
	}
	
	
	public  static String getNewClassCode() {
		return   "import java.util.*;"+"\n"+"\n"+"public class "+ Consts.getClassName() + " { "
				+ "\n"
				+ "\n"
				+ "\n"
				+ "}";
	}
	
	
	public  static String getNewInterfaceCode() {
		return  "\n"+"\n"+"public interface "+ Consts.getInterName() + " { "
				+ "\n"
				+ "\n"
				+ "\n"
				+ "}";
	}
	
	
	public final static String[] getKEYWORDS() {
		return KEYWORDS;
	}
	
	public final static String[] getPRIMITIVE() {
		return PRIMITIVE_TYPES;
	}
	
	public final static Pattern getPattern() {
		return PATTERN;
	}
	
	public final static String getMethodpattern() {
		return METHOD_PATTTERN;
	}
	
	public final static String getVariablePattern() {
		return VARIABLE_PATTERN;
	}
	
	public static StyleSpans<Collection<String>> getComputeHighLighting(String text) {
	    	return computeHighlighting(text);
	}
	
	public synchronized static String getTempClassCode() {
		return tempClassCode;
	}
	
	public synchronized static void setTempClassCode(String s) {
		tempClassCode = s;
	}
	

}
