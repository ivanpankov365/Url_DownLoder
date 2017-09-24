package PankovURLDownLoder;

import static java.lang.System.out;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestClass {
	
	public static void main(String[] args) throws IOException {
		String name = "<img src=\"/fv/2010/logo2.jpg\" width=\"260\" height=\"210\" border=\"0\" alt=\"Фальшивим вместе: аккорды и табулатуры для гитары\" title=\"Фальшивим вместе: аккорды и табулатуры для гитары\" />";
	    if(checkImagTag(name)) {
	    	System.out.println("ZBS!");
	    }
	    else {
	    	System.out.println("NEEE ZBS!");
	    }
	
	    
	}
	
	private static boolean checkImagTag(String userNameString) {  
		String ptr= "img src\\s*=\\s*([\"'])?([^\"']*)";
	    Pattern p = Pattern.compile(ptr);
	    Matcher m = p.matcher(userNameString);
	    if (m.find()) {
	    	
	        String src = m.group(2); //Result
	        out.println(src);
	        return true;
	    }
	    else
	    	  	return false;
    }

	

}
