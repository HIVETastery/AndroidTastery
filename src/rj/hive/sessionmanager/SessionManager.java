package rj.hive.sessionmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import rj.hive.database.DatabaseHelper;
import rj.hive.database.models.CartMap;
import rj.hive.database.models.Product;
import rj.hive.reusable.ReusableUtility;
import rj.hive.tastery.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref, prefSettings;
	
	// Editor for Shared preferences
	Editor editor, editorSettings;
	
	// Context
	Context _context;
	
	// Shared pref mode
	int PRIVATE_MODE = 0;
	
	// Sharedpref file name
	private static final String PREF_NAME = "TasterySession";
	private static final String PREF_SETTINGS = "TasterySettings";
	
	
	private static final String CURRENT_DATE = "DATE";
	
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";
	
	
	public static final String CUST_ID  	= "customer_id";
	public static final String STORE_ID 	= "store_id";
	public static final String FIRSTNAME 	= "firstname";
	public static final String LASTNAME 	= "lastname";
	public static final String EMAIL    	= "email";
	public static final String TELEPHONE   	= "telephone";
	public static final String MOBILE   	= "mobile";
	public static final String FAX		   	= "fax";
	public static final String PASSWORD	 	= "password";
	public static final String SALT		 	= "salt";
	
	public static final String WISHLIST    	= "wishlist";
	
	public static final String NEWSLETTER	= "newsletter";
	
	public static final String ADDRESSID	= "address_id";
	public static final String CUST_GRP_ID	= "customer_group_id";
	
	public static final String IP           = "ip";
	
	public static final String STATUS       = "status";
	
	public static final String APPROVED     = "approved";
	
	public static final String TOKEN    	= "token";
	
	public static final String DATE_ADDED   = "date_added";
	
	public static final String CONFCODE     = "confirm_code";
	
	public static final String SESSION_DATA = "session_code";	
	
	
	
	public static final String ADDRESS_1    = "address_1";
	
	public static final String ADDRESS_2    = "address_2";
	
	public static final String CITY         = "city";
	
	public static final String COMPANY      = "company";
	
	
	
	// Constructor
	public SessionManager(Context context)
	{
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		prefSettings = _context.getSharedPreferences(PREF_SETTINGS, PRIVATE_MODE);
		editor = pref.edit();
		editorSettings = prefSettings.edit();
	}
	
	/**
	 * Create login session
	 * */

	
	public Date getCurrentDateFormat()
	{
		// Log.d("+++Date", prefSettings.getString(CURRENT_DATE, null));
		/*Date convertedDate = new Date();
	        SimpleDateFormat dateFormat = ;
			try {
				convertedDate = dateFormat.parse(prefSettings.getString(CURRENT_DATE, null));
				return convertedDate;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new Date();
			}*/
		return new Date();
	}
	
	
	public void setCurrentDate(String date)
	{
		editorSettings.putString(CURRENT_DATE, date);
		editorSettings.commit();
	}
	
	
	public void setAddressDetails(String address1, String address2, String city, String company)
	{	
		editor.putString(ADDRESS_1, address1);
		editor.putString(ADDRESS_2, address2);
		editor.putString(CITY, city);
		editor.putString(COMPANY, company);
		editor.commit();
	}
	
	
	public void updateMainInfor(String firstName, String lastName, String mobile, String telephone)
	{
		editor.putString(FIRSTNAME, firstName);
		editor.putString(LASTNAME, 	lastName);
		editor.putString(MOBILE, 	mobile);
		editor.putString(TELEPHONE, telephone);
		editor.commit();
	}
	
	public void createLoginSession(
			String cust_id, 
			String store_id,
			String firstname,
			String lastname,
			String email,
			String telephone,
			String mobile,
			String fax,
			String password,
			String salt,
			String wishlist,
			String newsletter,
			String address_id,
			String custom_group_id,
			String ip,
			String status,
			String approved,
			String token,
			String date_added,
			String confirm_code,
			String session_code)
	{
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		
		editor.putString(CUST_ID, cust_id);
		editor.putString(STORE_ID, store_id);
		editor.putString(FIRSTNAME, firstname);
		editor.putString(LASTNAME, lastname);
		editor.putString(EMAIL, email);
		editor.putString(TELEPHONE, telephone);
		editor.putString(MOBILE, mobile);
		editor.putString(FAX, fax);
		editor.putString(PASSWORD, password);
		editor.putString(SALT, salt);
		editor.putString(WISHLIST, wishlist);
		editor.putString(NEWSLETTER, newsletter);
		editor.putString(ADDRESSID, address_id);
		editor.putString(CUST_GRP_ID, custom_group_id);
		editor.putString(IP, ip);
		editor.putString(STATUS, status);
		editor.putString(APPROVED, approved);
		editor.putString(TOKEN, token);
		editor.putString(DATE_ADDED, date_added);
		editor.putString(CONFCODE, confirm_code);
		editor.putString(SESSION_DATA, session_code);

		
		if(ReusableUtility.isDevelopersMode)
			Toast.makeText(_context, "session is:"+session_code, Toast.LENGTH_SHORT).show();
		
		editor.commit();
	}	

	public void setSession(String session_code)
	{
		editor.putString(SESSION_DATA, session_code);
		
		if(ReusableUtility.isDevelopersMode)
			Toast.makeText(_context, "session is:"+session_code, Toast.LENGTH_SHORT).show();
		
		editor.commit();
	}
	
	public boolean checkLogin()
	{
		return this.isLoggedIn();
	}
	
	public float getVATAmount(DatabaseHelper dbHelper)
	{
		float amount =  getTotalAmount(dbHelper);
		float vat = (amount * (0.125f));
		
		return vat;
	}
	
	public float getTotalAmount(DatabaseHelper dbHelper)
	{//VAT INCLUSIVE
		String session_data = pref.getString(SESSION_DATA, null);
		if(session_data != null)
		{
			try
			{
				JSONObject session = new JSONObject(session_data);
				JSONObject cartlist = session.getJSONObject("data").getJSONObject("cart");
				JSONObject cartschedlist =  session.getJSONObject("data").getJSONObject("cart_sched");
				
				Iterator<String> keysIterator = cartlist.keys();

				
				float total = 0f;
				
				while (keysIterator.hasNext()) 
				{
			        String keyStr = (String)keysIterator.next();
			        String valueStr = cartlist.getString(keyStr);

		        		int id = Integer.parseInt(keyStr.replace("::", ""));
		        		int qty = Integer.parseInt(valueStr);
				        
				        cartschedlist.remove(keyStr);
				        Product product =dbHelper.getProduct(Integer.toString(id));
				        
				        float price  = Float.parseFloat(product.price.replaceAll("[^\\d.]+", ""));
				        
				        
				        
				        total += (price* qty);    
				}
				
				return total;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return 0f;
	}
	
	
	public void updateCart(String id, String quantity)
	{
		String session_data = pref.getString(SESSION_DATA, null);
		if(session_data != null)
		{
			try
			{
				JSONObject session = new JSONObject(session_data);
				JSONObject cartlist = session.getJSONObject("data").getJSONObject("cart");
				
				Iterator<String> keysIterator = cartlist.keys();

				
				while (keysIterator.hasNext()) 
				{
				        String keyStr = (String)keysIterator.next();
				        
				        if(keyStr.replace("::", "").equals(id))
				        {
				        	cartlist.put(keyStr, quantity);
				        	break;
				        }
				}
				
				setSession(session.toString());

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}		
	}
	
	
	public void removeFromCart(String id)
	{
		String session_data = pref.getString(SESSION_DATA, null);
		if(session_data != null)
		{
			try
			{
				JSONObject session = new JSONObject(session_data);
				JSONObject cartlist = session.getJSONObject("data").getJSONObject("cart");
				JSONObject cartschedlist =  session.getJSONObject("data").getJSONObject("cart_sched");
				
				Iterator<String> keysIterator = cartlist.keys();

				
				while (keysIterator.hasNext()) 
				{
				        String keyStr = (String)keysIterator.next();
				        
				        
				        if(keyStr.replace("::", "").equals(id))
				        {
				        	cartlist.remove(keyStr);
				        	cartschedlist.remove(keyStr);
				        	break;
				        }
				}
				
				setSession(session.toString());

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	
	public ArrayList<CartMap> getCartArray()
	{
		ArrayList<CartMap> cartMapList = new ArrayList<CartMap>();
		
		String session_data = pref.getString(SESSION_DATA, null);
		if(session_data != null)
		{
			try
			{
				JSONObject session = new JSONObject(session_data);
				JSONObject cartlist = session.getJSONObject("data").getJSONObject("cart");
				JSONObject cartschedlist =  session.getJSONObject("data").getJSONObject("cart_sched");
				
				Iterator<String> keysIterator = cartlist.keys();

				
				while (keysIterator.hasNext()) 
				{
				        String keyStr = (String)keysIterator.next();
				        String valueStr = cartlist.getString(keyStr);
				        
				        
				        JSONObject schedule = cartschedlist.getJSONObject(keyStr);
				        
				        String place       = schedule.getString("place");
				        String deliverdate = schedule.getString("delivery_date");
				        String delivertime = schedule.getString("dtime");
				        String orderdate   = schedule.getString("odate");
				        
				        
				        
				        /*
				        CartMap cartMap = new CartMap(
				        		Integer.parseInt(keyStr.replace("::", "")), 
				        		Integer.parseInt(valueStr),
				        		orderdate,
				        		place,
				        		deliverdate,
				        		delivertime
				        		);
				        cartMapList.add(cartMap);
				        */
				}
				
				//setSession(session.toString());
				
				
				
				return cartMapList;	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public int getCartAmount()
	{
		String session_data = pref.getString(SESSION_DATA, null);
		if(session_data != null)
		{
			try 
			{
				JSONObject session = new JSONObject(session_data);
				JSONObject cartlist = session.getJSONObject("data").getJSONObject("cart");
				Iterator<String> keysIterator = cartlist.keys();
				int counter = 0;
				while (keysIterator.hasNext()) 
				{
				        String keyStr = (String)keysIterator.next();
				        String valueStr = cartlist.getString(keyStr);
				        
				        counter += Integer.parseInt(valueStr);
				}
				
				return counter;
			}
			catch(Exception e)
			{
				
			}
		}
		else
		{
			
		}
		return 0;
	}
	
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		// user name

		user.put(CUST_ID,   pref.getString(CUST_ID, null));
		user.put(STORE_ID,  pref.getString(STORE_ID, null));
		user.put(FIRSTNAME, pref.getString(FIRSTNAME, null));
		user.put(LASTNAME,  pref.getString(LASTNAME, null));
		user.put(EMAIL, pref.getString(EMAIL, null));
		user.put(TELEPHONE, pref.getString(TELEPHONE, null));
		user.put(MOBILE, pref.getString(MOBILE, null));
		user.put(FAX, pref.getString(FAX, null));
		user.put(PASSWORD, pref.getString(PASSWORD, null));
		user.put(SALT, pref.getString(SALT, null));
		user.put(WISHLIST, pref.getString(WISHLIST, null));
		user.put(NEWSLETTER, pref.getString(NEWSLETTER, null));
		user.put(ADDRESSID, pref.getString(ADDRESSID, null));
		user.put(CUST_GRP_ID,  pref.getString(CUST_GRP_ID, null));
		user.put(IP,  pref.getString(IP, null));
		user.put(STATUS,  pref.getString(STATUS, null));
		user.put(APPROVED,  pref.getString(APPROVED, null));
		user.put(TOKEN,  pref.getString(TOKEN, null));
		user.put(DATE_ADDED,  pref.getString(DATE_ADDED, null));
		user.put(CONFCODE,  pref.getString(CONFCODE, null));	
		user.put(SESSION_DATA, pref.getString(SESSION_DATA, null));
		
		user.put(CITY,  pref.getString(CITY, null));
		user.put(ADDRESS_1,  pref.getString(ADDRESS_1, null));
		user.put(ADDRESS_2,  pref.getString(ADDRESS_2, null));
		user.put(COMPANY,  pref.getString(COMPANY, null));
		return user;
	}
	
	
	public void logoutUser()
	{
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
	}
	
	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn()
	{
		return pref.getBoolean(IS_LOGIN, false);
	}
}
