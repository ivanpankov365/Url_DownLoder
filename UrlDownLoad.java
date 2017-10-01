package PankovURLDownLoder;

import java.util.*;

import javax.imageio.ImageIO;

import java.io.*;
import java.net.*;

import static java.lang.System.*;
import java.awt.Desktop;
import java.awt.Image;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.regex.Matcher;  
import java.util.regex.Pattern;


public class UrlDownLoad {
	
	private URL Url = null;
	private String UrlName = null;
	private String SiteName = null;
	private String FileName = null;
				
	public URL getUrl() {
		
		return Url;
	}
	
	public String getSiteName() {
		
		return SiteName;
	}
	
	public String getFileName() {
		
		return FileName;
	}
	
	public void setUrl(String[] args) {
	    
	   // while(true)
	    //{    
	     out.println("Enter URL-address ('quit' to exit): ");
		
	     // Scanner in = new Scanner(System.in);
		///  UrlName = in.nextLine(); 
		 // if(UrlName.equals("quit"))
			//	break;
		  
		  // Set FileName
		  int index = args[0].lastIndexOf('/');//UrlName.lastIndexOf('/');
			
			if(index == -1) {
				FileName = "index.html";
			}
			else {
				//int index1 = UrlName.indexOf('?',index);
				int index1 = args[0].indexOf('?',index);
				if(index1 == -1) {
					//FileName = UrlName.substring(index+1);
					FileName = args[0].substring(index+1);
				}
				else {
					//FileName = UrlName.substring(index+1,index1);
					FileName = args[0].substring(index+1,index1);
				}
			}
			
			//-------------------
	      
	      // Set SiteName
			//int index0 = UrlName.indexOf('/',8);
			int index0 = args[0].indexOf('/',8);
			
	      //SiteName = UrlName.substring(0, index0);
			SiteName = args[0].substring(0, index0);
	      
	      // Set Url
	      try
	      {
	    	  Url = new URL(args[0]);
	    	  
	      }
	      catch(MalformedURLException ex)
	      {
	        out.println(ex.toString());
		//continue;
	      }
	      
	  //  }  
		
	}

	public void openUrl() throws IOException {
		
		URLConnection conn = Url.openConnection();
		
		if(Url == null) {
			throw new IOException ("Error: Url is Empty");
		}
		
		// Путь к файлу
		out.println("Enter directory ('no' to use default way):");
		
		
		Desktop desktop = null;
		if (Desktop.isDesktopSupported()) {
		    desktop = Desktop.getDesktop();
		}
		
		
		Scanner in = new Scanner(System.in);
		String Direction = in.nextLine(); 
		if(Direction.endsWith("no")) {
			Direction = "C:\\Users\\Иван\\Desktop\\Java\\проекты\\URLDownLoder";
			
			UrlFileWriter(conn,Direction);
			
		}
		else {
			Path path = Paths.get(Direction);

			if (Files.exists(path)) {
			    out.println("Yes");
			    
			    UrlFileWriter(conn,Direction);
			    
			}
			else {
				new File(Direction).mkdirs(); 
				UrlFileWriter(conn,Direction);
			}
			
		}
		
	        
	}
	
	private void UrlFileWriter (URLConnection conn, String Direction) throws IOException {
		
		// Получаем данные о кодировке страницы
		
				//downloadUsingStream("https://vk.com/doc125397683_448301532", "1.pdf");
		
				String tmp = conn.getContentType();
				if(tmp.equals("image/jpeg")) {
					downloadJpg(Url.toString(), FileName);
				}
				else {
					int index = tmp.indexOf(' ');
					int index1 = tmp.indexOf('=');
					String Code, Type;
					Type = tmp.substring(0,index-1);
					Code = tmp.substring(index1+1);
					
					//out.println(Type);
					//out.println(Code);
					
		
					File file = new File(Direction);
					if (file.exists() && file.isFile()) {
						
						
					  out.println("Exist");
					  out.println("Do you want to reload existence file? (yes/no)");
					  
					  Scanner in = new Scanner(System.in);
					  String answ = in.nextLine(); 
					  
					  if(answ.equals("no")) {
						  CreateNewFile(conn, Direction);
					  }
					  else {
						  ReloadFile(conn, Direction);
					  }
					  
					  
					}
					else {
						Direction =  Direction + "\\" + FileName;
						File file1 = new File(Direction);
						if (file1.exists() && file1.isFile()) {
							
							
						  out.println("Exist");
						  out.println("Do you want to reload existence file? (yes/no)");
						  
						  Scanner in = new Scanner(System.in);
						  String answ = in.nextLine(); 
						  
						  if(answ.equals("no")) {
							  CreateNewFile(conn, Direction);
						  }
						  
						  else {
							  ReloadFile(conn, Direction);
						  }
						  
						  
						}
						
						else {
							ReloadFile(conn, Direction);
						}
					}
				}
				
	}
	
	private void ReloadFile(URLConnection conn, String Direction) {
		
		// Получаем данные о кодировке страницы
		String tmp = conn.getContentType();
		int index = tmp.indexOf(' ');
		int index1 = tmp.indexOf('=');
		String Code, Type;
		Type = tmp.substring(0,index-1);
		Code = tmp.substring(index1+1);
		
		//out.println(Type);
		//out.println(Code);
		
		out.println("File was Reloaded");
		
		try(FileWriter writer = new FileWriter(Direction)) // Check file existence 
        {
			String externLine = "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">";
			 BufferedReader reader = new BufferedReader(
		                new InputStreamReader(conn.getInputStream(), Code));
		        while (true) {
		        
		            String line = reader.readLine();
		            
		            if (line == null)   break;
		            
		            if (line.equals(externLine)) 
		            	writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=cp1251\" />");
		            	
		            else {
		            	String PostDirection = checkImagTag(line);
		            	if(!PostDirection.equals("no")) {
		            		out.println("CHHHECK!!!!");
		            		out.println(SiteName);
		            		Direction = SiteName + PostDirection;
		            		out.println(Direction);
		            		out.println(PostDirection);
		            		UrlFileWriter (conn, Direction);
		            	}
		            	else {
		            		writer.write(line);	 
		            	}
		                                
		            }
	                writer.append('\n');
		            writer.flush();
		             // out.println(line);
		        }
		        writer.close();
        }
		catch(IOException ex){
			
			System.out.println(ex.getMessage());
        }
	}
	
	private void CreateNewFile(URLConnection conn, String Direction) {
		
		// Получаем данные о кодировке страницы
		String tmp = conn.getContentType();
		int index = tmp.indexOf(' ');
		int index1 = tmp.indexOf('=');
		String Code, Type;
		Type = tmp.substring(0,index-1);
		Code = tmp.substring(index1+1);
		
		//out.println(Type);
		//out.println(Code);
		
		
		out.println("Created New File");
		index = Direction.indexOf('.');
		String DirectionTmp = Direction.substring(0, index)+"new" + Direction.substring(index);
		Direction = DirectionTmp;
		try(FileWriter writer = new FileWriter(Direction)) // Check file existence 
        {
			String externLine = "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">";
			 BufferedReader reader = new BufferedReader(
		                new InputStreamReader(conn.getInputStream(), Code));
		        while (true) {
		        
		            String line = reader.readLine();
		            
		            if (line == null)   break;
		            
		            if (line.equals(externLine)) 
		            	writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=cp1251\" />");
		            	
		            else {
		                writer.write(line);	                 
		            }
	                writer.append('\n');
		            writer.flush();
		             // out.println(line);
		        }
		        writer.close();
        }
		catch(IOException ex){
			
			System.out.println(ex.getMessage());
        }
	}

	private void downloadJpg(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

	private String checkImagTag(String userNameString) {  
		String ptr= "img src\\s*=\\s*([\"'])?([^\"']*)";
	    Pattern p = Pattern.compile(ptr);
	    Matcher m = p.matcher(userNameString);
	    if (m.find()) {
	    	String src = m.group(2); //Result
	        out.println(src);
	    	return src;
	    }
	    else	return "no";
    }
	private String imagWay(String userNameString) {  
		String ptr= "img src\\s*=\\s*([\"'])?([^\"']*)";
	    Pattern p = Pattern.compile(ptr);
	    Matcher m = p.matcher(userNameString);
	    
	        String src = m.group(2); //Result
	      
        	return src;
    }
}
