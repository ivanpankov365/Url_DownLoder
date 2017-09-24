package PankovURLDownLoder;

import java.util.*;

import java.net.*;

import static java.lang.System.*;

import java.io.InputStream;


public class DownLoder {
			
			
			private String Url = null;
			
			private String Name = null;
						
			public String getUrl() {
				return Url;
			}
			
			public String getName() {
				return Name;
			}
			
			@SuppressWarnings("resource")
			public void setUrl() {
				out.println("Enter URL");
				
				String url;
				Scanner in = new Scanner(System.in);
				url = in.nextLine();
				Url = url;
				
				// SetName
				int index = url.indexOf('/',8);
				
				if(index == -1) {
					Name = "index.html";
				}
				else {
					int index1 = url.indexOf('?',index);
					if(index1 == -1) {
						Name = url.substring(index+1);
					}
					else {
						Name = url.substring(index+1,index1);
					}
				}
			}
			
			
			
			

			

			
			
}