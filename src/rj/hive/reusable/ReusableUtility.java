package rj.hive.reusable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ReusableUtility 
{

	public static boolean isDevelopersMode = false;
	
	
	public static String removePortion(String a)
	{
		String remove = "</p>\r\n";
		String remove2 = "<p>";
		String remove3 = "&nbsp;";
		/*due to unstoppable imbecility of the front-end developer of Tastery,
		 * I have to cut parts of every string his API gives in
		 * 
		 * 
		 * So i invented this shit
		 * 
		 * such a fucking asshole*/
		String newstr = a.replace(remove, "");
		newstr = newstr.replace(remove2, "");
		newstr = newstr.replace(remove3, "");
		return newstr.replaceAll("\\<[^>]*>","");
		/*int index = str.indexOf("(");
		System.out.println(str.substring(0, index));*/
		
	}
}