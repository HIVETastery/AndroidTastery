package tastery.datamanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rj.hive.database.models.Account;
import rj.hive.database.models.CartMap;
import rj.hive.database.models.Product;
import android.util.Log;

public class TasteryRestConnection implements Runnable {

	static TasteryRestConnection context = null;

	public int Method;
	public String Request, Response;
	public boolean isDone;
	public InputStream is = null;
	public HttpClient httpclient;
	public HttpGet httpget;
	public HttpPost httppost;
	public HttpResponse response;
	public HttpEntity entity;
	public int RequestType;

	public final int loginWithUsernameAndPassword 	= 0x001;
	public final int getProductsList 				= 0x002;
	public final int getCartItems	 				= 0x003;
	public final int addItemToCart					= 0x004;
	public final int commonRequest					= 0x005;
	
	public final int RequestType_Get				= 0x101;
	public final int RequestType_Post				= 0x102;

	public static TasteryRestConnection getContext() {
		return context == null ? context = new TasteryRestConnection()
				: context;
	}

	@Override
	public void run() {
		isDone = false;
		
		if(httpclient == null) {
			httpclient = getNewHttpClient();
		}
		
		Response = getJSON();
		
		Log.d("api", "Response Data : " + Response);
		parseJSON();
		isDone = true;
	}

	public void setHttpPostAndMethod(HttpPost httppost, int Method) {
		this.Method = Method;
		this.RequestType = RequestType_Post;
		this.httpget = null;
		this.httppost = httppost;
	}

	public void setHttpGetAndMethod(HttpGet httpget, int Method) {
		this.Method = Method;
		this.RequestType = RequestType_Get;
		this.httpget = httpget;
		this.httppost = null;
	}
	
	public String getJSON() {
		is = null;
		String json = "";
		try {
			if (httpget != null) {
				response = httpclient.execute(httpget);
			} else if (httppost != null) {
				response = httpclient.execute(httppost);
			}

			entity = response.getEntity();
			is = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return json;
	}

	private void parseJSON() {
		try {
			switch (Method) {
			case loginWithUsernameAndPassword:
				loginWithUsernameAndPassword();
				break;
			case getProductsList:
				getProductsList();
				break;
			case getCartItems:
				getCartItems();
				break;
			case addItemToCart:
				addItemToCart();
				break;
			case commonRequest:
				commonRequest();
				break;
			}
		} catch(Exception e) {
			
		}
	}

	private void loginWithUsernameAndPassword() {
		try {
			JSONObject object = new JSONObject(this.Response);
			JSONObject message = object.getJSONObject("message");
			JSONObject account = message.getJSONObject("account");

			Account userAccount = new Account(account.getString("address_id"), 
					 "", 
					 account.getString("customer_id"),
					 account.getString("email"), 
					 account.getString("firstname"), 
					 account.getString("lastname"), 
					 account.getString("mobile"), 
					 account.getString("password"), 
					 account.getString("telephone"));
			/*
			 * 01-16 07:26:18.703: D/api(9796): Response Data :
			 * {"message":{"message"
			 * :{"status":"success"},"account":{"customer_id"
			 * :"223","store_id":"0"
			 * ,"firstname":"Emman","lastname":"Kusumi","email"
			 * :"ekusumi@icloud.com"
			 * ,"telephone":"12345678","mobile":"12345678","fax"
			 * :"","password":"293fdb21cd244f5082254d4069388d62704a2688"
			 * ,"salt":"1dfb6a4a2","cart":
			 * "a:5:{s:4:\"85::\";i:3;s:5:\"183::\";i:10;s:5:\"181::\";i:9;s:5:\"182::\";i:8;s:5:\"194::\";i:1;}"
			 * ,"cart_sched":
			 * "a:5:{s:4:\"85::\";a:4:{s:5:\"dtime\";s:0:\"\";s:5:\"odate\";i:1417764812;s:5:\"place\";s:6:\"Makati\";s:13:\"delivery_date\";i:1417622400;}s:5:\"183::\";a:4:{s:5:\"dtime\";s:0:\"\";s:5:\"odate\";i:1417058441;s:5:\"place\";N;s:13:\"delivery_date\";b:0;}s:5:\"181::\";a:4:{s:5:\"dtime\";s:0:\"\";s:5:\"odate\";i:1417058441;s:5:\"place\";N;s:13:\"delivery_date\";b:0;}s:5:\"182::\";a:4:{s:5:\"dtime\";s:0:\"\";s:5:\"odate\";i:1417058441;s:5:\"place\";N;s:13:\"delivery_date\";b:0;}s:5:\"194::\";a:4:{s:5:\"dtime\";s:0:\"\";s:5:\"odate\";i:1417532420;s:5:\"place\";N;s:13:\"delivery_date\";b:0;}}"
			 * ,
			 * "wishlist":"","newsletter":"0","address_id":"249","customer_group_id"
			 * :
			 * "1","ip":"112.203.246.240","status":"1","approved":"1","token":""
			 * ,
			 * "date_added":"2014-11-26 21:13:33","confirm_code":""},"session":{
			 * "data"
			 * :{"language":"en","currency":"PHP","cart":{"85::":3,"183::":
			 * 10,"181::"
			 * :9,"182::":8,"194::":1},"cart_sched":{"85::":{"dtime":""
			 * ,"odate":1417764812
			 * ,"place":"Makati","delivery_date":1417622400},"183::"
			 * :{"dtime":"",
			 * "odate":1417058441,"place":null,"delivery_date":false
			 * },"181::":{"dtime"
			 * :"","odate":1417058441,"place":null,"delivery_date"
			 * :false},"182::"
			 * :{"dtime":"","odate":1417058441,"place":null,"delivery_date"
			 * :false
			 * },"194::":{"dtime":"","odate":1417532420,"place":null,"delivery_date"
			 * :false}},"customer_id":"223"}}}}
			 */

			TasteryObject.getContext().onLoginComplete
					.onLoginComplete(userAccount);
		} catch (JSONException e) {
			e.printStackTrace();
			TasteryObject.getContext().onLoginComplete
					.onLoginComplete(null);
		}
	}

	private void getProductsList() {
		try {
			List<Product> products = new ArrayList<Product>();
			JSONObject jsonObject = new JSONObject(this.Response);
			JSONArray array = new JSONArray(jsonObject.getJSONArray("products")
					.toString());
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);

				Product product = new Product(object.getString("product_id"),
						"", object.getString("thumb"),
						object.getString("name"),
						object.getString("description"),
						object.getString("day"), object.getString("price"), "",
						"", object.getString("reviews"),
						object.getString("href"));
				products.add(product);

			}

			// Log.d("PRODUCT","Products Size : " + products.toString());
			TasteryObject.getContext().onGetProductsList
					.onGetProductsListComplete(products);
		} catch (JSONException e) {
			e.printStackTrace();
			TasteryObject.getContext().onGetProductsList
			.onGetProductsListComplete(null);
		}
	}
	
	private void getCartItems() {
		try {
			List<CartMap> cartItems = new ArrayList<CartMap>();
			JSONArray array = new JSONArray(this.Response);

			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				CartMap cartMap = new CartMap(
						i, 
						Integer.parseInt(object.getString("quantity")), 
						object.getString("ddate"), 
						object.getString("place"), 
						object.getString("ddate"), 
						"11am-1pm",
						object.getString("name"),
						object.getString("price")
				);
				cartItems.add(cartMap);
				
				// JSONArray array = new JSONArray(jsonObject.getJSONArray("products").toString());
				/*
				01-22 06:47:53.486: D/api(914): Response Data : 
				[{"key":"201::","thumb":"http:\/\/tastery.ph\/image\/cache\/data\/Pasta Primavera-47x47.jpg",
				"name":"Pasta Primavera","model":"food","option":[],"quantity":1,"stock":true,"reward":"",
				"price":"\u20b1199.00","total":"\u20b1199.00","href":
				"http:\/\/tastery.ph\/index.php?route=product\/product&amp;product_id=201&place=",
				"remove":"http:\/\/tastery.ph\/index.php?route=checkout\/cart&amp;remove=201::&place=",
				"recurring":false,"profile_name":"","profile_description":"","odate":"Thu Jan. 22, 2015",
				"dtime":[{"timeid":"72","delivery_time":"11am-1pm"},{"timeid":"73","delivery_time":"3pm-5pm"}],
				"ddate":"Thu Jan. 22, 2015","place":"Taguig"}]

				*/
				

			}

			TasteryObject.getContext().onGetCartItemsComplete.onGetCartItemsComplete(cartItems);
		} catch (JSONException e) {
			e.printStackTrace();
			List<CartMap> cartList = new ArrayList<CartMap>();
			TasteryObject.getContext().onGetCartItemsComplete.onGetCartItemsComplete(cartList);
		}
	}
	
	public void addItemToCart() {
		try {
			JSONObject object = new JSONObject(this.Response);
			String success = object.getString("success");
			TasteryObject.getContext().onAddToCartItemsComplete.onAddToCartItemsComplete(success, null);
			
		} catch(JSONException e) {
			Error error = new Error();
			TasteryObject.getContext().onAddToCartItemsComplete.onAddToCartItemsComplete("Add Item To Cart Failed!", 
					error);
		}
	}
	
	public void commonRequest() {
		try {
			JSONObject object = new JSONObject(this.Response);
			TasteryObject.getContext().onRequestComplete.onRequestComplete(null);
			
		} catch(JSONException e) {
			TasteryObject.getContext().onRequestComplete.onRequestComplete(null);
		}
	}

	public class MySSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
					// TODO Auto-generated method stub

				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
					// TODO Auto-generated method stub

				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

	public HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

}
