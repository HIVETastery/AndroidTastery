package rj.hive.reusable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FacebookUtility {

	static Bitmap a;
	public static Bitmap getFacebookProfilePicture(final String userID)
	{

		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
				try 
				{
				    URL imageURL;
					imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=small");
				    Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
				    a = bitmap;
				} 
				catch (MalformedURLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		});

		thread.start(); 		
		return a;

	}
	
}