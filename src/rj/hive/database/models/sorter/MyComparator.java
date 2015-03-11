package rj.hive.database.models.sorter;

import rj.hive.database.models.CartMap;

public class MyComparator implements java.util.Comparator<CartMap> {

	@Override
	public int compare(CartMap arg0, CartMap arg1) 
	{
        return arg0.product.name.length()- arg1.product.name.length();
	}
}