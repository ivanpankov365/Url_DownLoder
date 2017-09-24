package PankovURLDownLoder;

import java.io.IOException;

public class DownLoderMain {
	
	public static void main(String[] args) throws IOException {
			String name, URL;
			
			UrlDownLoad d = new UrlDownLoad();
			
			d.setUrl();
			//System.out.println(d.getUrl());
			System.out.println(d.getFileName());
			//System.out.println(d.getSiteName());
			
			d.openUrl();
			
		}
}
