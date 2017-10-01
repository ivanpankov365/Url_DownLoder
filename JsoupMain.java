package PankovURLDownLoder;

import java.io.IOException;

public class JsoupMain {
	public static void main(String[] args) throws IOException {
		String name, URL;
		
		JsoupDownLoader d = new JsoupDownLoader();
		//d.setUrl(args);
		System.out.println("For the correct operation of the program, specify three command-line arguments");
		System.out.println("1st: Url");
		System.out.println("2nd: Directory, \"no\" for default settings");
		System.out.println("3rd: Do you want to open your file, yes\\no");
		String[] arg = {"http://mail.ru","C:\\Users\\Иван\\Desktop\\Java\\проекты\\URLDownLoder\\test0","yes"};
		d.setUrl(arg);
		
		//System.out.println(d.getUrl());
		//System.out.println(d.getSiteName());
		//System.out.println(d.getFileName());
		//System.out.println();
		//System.out.println();
		//System.out.println(args[0]);
		//d.openUrl();
		
	}
}
