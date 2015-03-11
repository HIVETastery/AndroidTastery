package tastery.datamanager;

import java.util.ArrayList;
import java.util.List;

import rj.hive.database.models.Account;
import rj.hive.database.models.CartMap;
import rj.hive.database.models.Product;
import tastery.datamanager.TasteryAPIManager.OnAddToCartItemsComplete;
import tastery.datamanager.TasteryAPIManager.OnGetCartItemsComplete;
import tastery.datamanager.TasteryAPIManager.OnGetProductsListComplete;
import tastery.datamanager.TasteryAPIManager.OnLoginComplete;
import tastery.datamanager.TasteryAPIManager.OnRequestComplete;

public class TasteryObject {
	
	static TasteryObject tasteryObject = null;
	
	public OnLoginComplete onLoginComplete;
	public OnGetProductsListComplete onGetProductsList;
	public OnGetCartItemsComplete onGetCartItemsComplete;
	public OnAddToCartItemsComplete	onAddToCartItemsComplete;
	public OnRequestComplete onRequestComplete;
	
	public TasteryAPIManager apiManager;
	
	public List<Product> productsList = new ArrayList<Product>();
	public List<Product> productSetList = new ArrayList<Product>();
	public List<CartMap> cartList = new ArrayList<CartMap>();
	public Account currentAccount;
	
	public static TasteryObject getContext() {
		if(tasteryObject == null) {
			tasteryObject = new TasteryObject();
		}
		return tasteryObject;
	}
	
	public List<Product> getProductsForDay(String day) {
		List<Product> products = new ArrayList<Product>();
		for(Product product : productsList) {
			if(product.day.equalsIgnoreCase(day)) {
				products.add(product);
			}
		}
		return products;
	}
	
	public Product getProductSetForDay(String day) {
		for(Product product : productsList) {
			if(product.day.equalsIgnoreCase(day+"SetMeal")) {
				return product;
			}
		}
		return null;
	}
	
	public Boolean isLoggedIn() {
		if(this.currentAccount == null) {
			return false;
		} else {
			return true;
		}
	}

}
