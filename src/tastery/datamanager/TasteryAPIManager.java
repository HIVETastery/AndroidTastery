package tastery.datamanager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import rj.hive.database.models.Account;
import rj.hive.database.models.CartMap;
import rj.hive.database.models.Product;
import android.util.Log;


public class TasteryAPIManager {
	
	static TasteryAPIManager tasteryAPIManager = null;
	
	public String host;
	public String Username;
	public String Password;
	public int Method;
	public boolean isDone;
	public HttpGet httpget;
	public HttpPost httppost;
	public TasteryRestConnection restConn;
	
	public static TasteryAPIManager getContext() {
		if(tasteryAPIManager == null) {
			tasteryAPIManager = new TasteryAPIManager();
			tasteryAPIManager.host = "http://tastery.ph/index.php?route=";
			tasteryAPIManager.httpget = new HttpGet(tasteryAPIManager.host);
			tasteryAPIManager.restConn = new TasteryRestConnection();
		}
		return tasteryAPIManager;
	}
	
	public void initializeWithHost(String host) {
		this.host = host;
	}
	
	private TasteryRestConnection init() {
		return TasteryRestConnection.getContext();
	}
	
	private void initHttpWithUrl(String Url) {
		Log.d("REQUEST", Url);
		httpget = new HttpGet(Url);
		httppost = new HttpPost(Url);
	}
	
	// ON COMPLETE BLOCK INTERFACE
	
	public interface OnLoginComplete {
		void onLoginComplete(Account account);
	}
	
	public interface OnGetProductsListComplete {
		void onGetProductsListComplete(List<Product> products);
	}
	
	public interface OnGetCartItemsComplete {
		void onGetCartItemsComplete(List<CartMap> cartItems);
	}
	
	public interface OnAddToCartItemsComplete {
		void onAddToCartItemsComplete(String success, Error error);
	}
	
	public interface OnRequestComplete {
		void onRequestComplete(Error error);
	}
	
	// API FUNCTIONS
			
	// Login
	public void loginWithUsernameAndPassword(String Username, 
			String Password, 
			OnLoginComplete onComplete) {
			TasteryObject.getContext().onLoginComplete = onComplete;
			
			String Url = this.host+"account/login&json";
			initHttpWithUrl(Url);
			restConn = init();
			
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("email", Username));
			pairs.add(new BasicNameValuePair("password", Password));
			try {
				httppost.setEntity(new UrlEncodedFormEntity(pairs));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			restConn.setHttpPostAndMethod(httppost, restConn.loginWithUsernameAndPassword);
			new Thread(restConn).start();
		} 
	
	// Get Products List
	public void getProductsList(OnGetProductsListComplete onComplete) {
		TasteryObject.getContext().onGetProductsList = onComplete;
		
		String Url = this.host+"common/home&json";
		initHttpWithUrl(Url);
		restConn = init();
		restConn.setHttpGetAndMethod(httpget, restConn.getProductsList);
		new Thread(restConn).start();
	}	
	
	// Get Cart Items
	public void getCartItems(OnGetCartItemsComplete onComplete) {
		TasteryObject.getContext().onGetCartItemsComplete = onComplete;
		
		String Url = this.host+"checkout/cart&json";
		initHttpWithUrl(Url);
		restConn = init();
		restConn.setHttpGetAndMethod(httpget, restConn.getCartItems);
		new Thread(restConn).start();
	}
	
	// Add To Cart
	// checkout/cart/add
	// // 'product_id=' + product_id + '&quantity=' + quantity+ '&place=' + place+ '&deliverydate=' + delivery_date,
	public void addToCartItems(String product_id, 
			int quantity, 
			String place, 
			String delivery_date, 
			OnAddToCartItemsComplete onComplete) {
		TasteryObject.getContext().onAddToCartItemsComplete = onComplete;
		
		String Url = this.host+"checkout/cart/add";
		initHttpWithUrl(Url);
		restConn = init();
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("product_id", product_id));
		pairs.add(new BasicNameValuePair("quantity", ""+quantity));
		pairs.add(new BasicNameValuePair("place", place));
		pairs.add(new BasicNameValuePair("deliverydate", delivery_date));
		try {
			httppost.setEntity(new UrlEncodedFormEntity(pairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		restConn.setHttpPostAndMethod(httppost, restConn.addItemToCart);
		new Thread(restConn).start();
	}
	
	public void confirmCheckout(OnRequestComplete onComplete) {
		TasteryObject.getContext().onRequestComplete = onComplete;
		
		String Url = this.host+"payment/cod/confirm";
		initHttpWithUrl(Url);
		restConn = init();
		restConn.setHttpGetAndMethod(httpget, restConn.commonRequest);
		new Thread(restConn).start();
	}
	
	public void confirmOrder(OnRequestComplete onComplete) {
		TasteryObject.getContext().onRequestComplete = onComplete;
		
		String Url = this.host+"checkout/confirm&json";
		initHttpWithUrl(Url);
		restConn = init();
		restConn.setHttpGetAndMethod(httpget, restConn.commonRequest);
		new Thread(restConn).start();
	}
	
	public void finalCheckout(OnRequestComplete onComplete) {
		TasteryObject.getContext().onRequestComplete = onComplete;
		
		String Url = this.host+"checkout/checkout";
		initHttpWithUrl(Url);
		restConn = init();
		restConn.setHttpGetAndMethod(httpget, restConn.commonRequest);
		new Thread(restConn).start();
	}
	
	/*
	 * NSDictionary *signup = [[NSDictionary alloc] initWithObjectsAndKeys:firstName,@"firstname",
	 * lastName,@"lastname",email,@"email",mobile,@"mobile",telephone,@"telephone",@"--",@"company",
	 * address_1,@"address_1",@"--",@"address_2",city,@"city",password,@"password",password,@"confirm",@"1",
	 * @"agree",nil];
	 * 
	 */
	
	public void registerAccount(String Firstname, 
			String Lastname, 
			String Email, 
			String Mobile, 
			String Telephone,
			String Address, 
			String City, 
			String Password, 
			OnRequestComplete onComplete) {
		TasteryObject.getContext().onRequestComplete = onComplete;
		
		String Url = this.host+"account/register&json";
		initHttpWithUrl(Url);
		restConn = init();
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("firstname", Firstname));
		pairs.add(new BasicNameValuePair("lastname", Lastname));
		pairs.add(new BasicNameValuePair("email", Email));
		pairs.add(new BasicNameValuePair("mobile", Mobile));
		pairs.add(new BasicNameValuePair("telephone", Telephone));
		pairs.add(new BasicNameValuePair("company", ""));
		pairs.add(new BasicNameValuePair("address_1", Address));
		pairs.add(new BasicNameValuePair("address_2", ""));
		pairs.add(new BasicNameValuePair("city", City));
		pairs.add(new BasicNameValuePair("password", Password));
		pairs.add(new BasicNameValuePair("confirm", Password));
		pairs.add(new BasicNameValuePair("agree", "1"));
		try {
			httppost.setEntity(new UrlEncodedFormEntity(pairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		restConn.setHttpPostAndMethod(httppost, restConn.commonRequest);
		new Thread(restConn).start();
	}
	
}
