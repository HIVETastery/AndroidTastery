package rj.hive.tastery;

import java.util.List;

import rj.hive.database.models.Account;
import rj.hive.database.models.Product;
import tastery.datamanager.TasteryAPIManager;
import tastery.datamanager.TasteryAPIManager.OnGetProductsListComplete;
import tastery.datamanager.TasteryAPIManager.OnLoginComplete;
import tastery.datamanager.TasteryObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashScreenActivity extends Activity {
 
 // private boolean mIsBackButtonPressed;
 // private static final int SPLASH_DURATION = 2000; //6 seconds
 // private Handler myhandler;
 
 	public void onCreate(Bundle savedInstanceState) 
 	{
 		super.onCreate(savedInstanceState);
 		setContentView(R.layout.activity_splash);
 
	 // myhandler = new Handler();

 		TasteryAPIManager apiManager = TasteryAPIManager.getContext();
 		apiManager.getProductsList(new OnGetProductsListComplete() {
	
 			@Override
 			public void onGetProductsListComplete(List<Product> products) {
 				// TODO Auto-generated method stub
 				
 				Log.d("PRODUCTS","TOTAL PRODUCTS " + products.size());
 				TasteryObject.getContext().productsList = products;
		
 		 		/* TasteryAPIManager.getContext().loginWithUsernameAndPassword("ekusumi@icloud.com", "kusumi21", 
 		 				new OnLoginComplete() {
					
					@Override
					public void onLoginComplete(Account account) {
						// TODO Auto-generated method stub
						
						TasteryObject.getContext().currentAccount = account;*/
		 				
		 				finish();
		 				
		 				Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
		 		 		SplashScreenActivity.this.startActivity(intent);
						
					/*}
				});*/
 		 		
 			}
	
 		});

 	}
}
