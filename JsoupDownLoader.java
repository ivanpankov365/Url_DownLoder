package PankovURLDownLoder;

import static java.lang.System.out;

import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.Jsoup;

import java.io.*;

public class JsoupDownLoader {
	private URL Url = null;
	private String UrlName = null;
	private String SiteName = null;
	private String FileName = null;
	private String Dir = null;
	

	public URL getUrl() {
		
		return Url;
	}
	
	public String getSiteName() {
		
		return SiteName;
	}
	
	public String getFileName() {
		
		return FileName;
	}
	
	public void setUrl(String[] args) throws IOException {
	//public void setUrl(String args) throws IOException {  
		try
	      {
	    	Url = new URL(args[0]);
			//Url = new URL(args);	    	  
	      }
	      catch(MalformedURLException ex)
	      {
	        out.println(ex.toString());
	
	      }
		
		//System.out.println(Url);
		
		//System.out.print("getProtocol:  ");		System.out.println(Url.getProtocol()); 	// Возвращает компонент протокола протокола URL
		//System.out.print("getAuthority:  "); 	System.out.println(Url.getAuthority()); // Возвращает компонент полномочий URL.
		//System.out.print("getHost:  ");			System.out.println(Url.getHost()); 		// Возвращает компонент имени узла URL-адреса.
		//System.out.print("getPort:  ");			System.out.println(Url.getPort());		// Возвращает компонент номера порта URL. 
																						// Метод getPort возвращает целое число, которое является номером порта.
																						// Если порт не установлен, getPort возвращает -1.
		//System.out.print("getPath:  ");			System.out.println(Url.getPath()); 		// Возвращает компонент пути этого URL-адреса.
		//System.out.print("getQuery:  ");		System.out.println(Url.getQuery());		// Возвращает компонент запроса этого URL-адреса.
		//System.out.print("getFile:  ");			System.out.println(Url.getFile());		// Возвращает компонент имени файла URL-адреса. Метод getFile возвращает то же,
																						// что и getPath, плюс конкатенация значения getQuery, если таковая имеется.
		//System.out.print("getRef:  ");			System.out.println(Url.getRef());		// Возвращает ссылочный компонент URL-адреса.
		
		if(Url.getPath().isEmpty()) {
			FileName = "index.html";
		}
		else {
			FileName = Url.getPath().substring(1);
		}
		
		//System.out.println(FileName);
		
		if(args[1].endsWith("no")) {
			Dir = null;		
		}
		else {
			Path path = Paths.get(args[1]);

			if (Files.exists(path)) {
			    
			    // Запрос
				 out.println("This directory are Exist");
				  out.println("Do you want to rewrite this directory? yes\\no");
				  Scanner in = new Scanner(System.in);
				  String str = in.nextLine();
				  if (str.equals("yes")) {
					  Dir = args[1];
				  }
				  else {
				  Dir = args[1]+"\\newDir";
				  out.println("Created New Directory: " + Dir);
				  }
			}
			else {
				new File(args[1]).mkdirs(); 
				Dir = args[1];
			}
			
		}
		
		downLoad(Url.toString(),FileName, Dir,args);
	}
	
	private void downLoad(String url, String fileName, String Dir,String[] args) throws IOException {
		Document doc = Jsoup.connect(url).get();

		Elements img = doc.getElementsByTag("img");
		for (Element el : img) {
					//el.attr(attributeKey, attributeValue);
							String src = el.absUrl("src");
							String localSrc = el.attr("src");
							System.out.println("local attribute is : "+localSrc);
			                System.out.println("src attribute is : "+src);
			                //getImages(src);
			                URL tmpUrl = new URL(src);
			                int index0 = src.lastIndexOf('/');
			                int index1 = src.lastIndexOf('?');
			                
			                
			                localSrc = localSrc.replaceAll("[*?:\"|><]", "1");
			                System.out.println(localSrc);
			                String name;
			                
			                if(!localSrc.isEmpty()) {
				                name = localSrc.substring(localSrc.indexOf('/'));
				                if(name.charAt(name.length()-1)=='/') {
				                	name = name.substring(0, name.length()-2);
				                }
			                }
			                else {
			                	name = "\\newDir\\1";
			                }
			               		                
			                System.out.println(name);
			                String NewDir = Dir + "\\"+"files";
			                System.out.println(NewDir);
			                Path path = Paths.get(NewDir);

			    			if (!Files.exists(path)) {
			    			    System.out.println("YES");
			    				new File(NewDir).mkdirs();
			    			}
			    			String FullName = NewDir +"\\"+ name;
			                downloadJpg(src,  FullName);
			                el.attr("src", FullName);
		}
		
		
		String html = doc.html();
		html = html.replaceFirst("charset\\s?=\\s?\"?[Uu][Tt][fF]-8", "charset=cp1251");
		
		int index0 = fileName.lastIndexOf('/');
        fileName =fileName.substring(index0+1);
        
		String Direction = Dir + "\\"+ fileName;
		 try(FileWriter writer = new FileWriter(Direction, false))
	        {
	            writer.write(html);
	            writer.append('\n');
	             
	            writer.flush();
	        }
	        catch(IOException ex){
	             
	            System.out.println(ex.getMessage());
	        } 
		 
		 
		 if(args[2].equals("yes")) {
				Desktop desk = null;
				if (Desktop.isDesktopSupported()) {
				    desk = Desktop.getDesktop();
				}
				try {
				    desk.open(new File(Direction));
				} catch (IOException ioe) {
				    ioe.printStackTrace();
				}
			}
	}
	
	private void downloadJpg(String urlStr, String file) throws IOException{
		new File(file.substring(0,file.lastIndexOf('/'))).mkdirs();
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


}
