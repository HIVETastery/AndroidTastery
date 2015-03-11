package rj.hive.database;

import java.text.DecimalFormat;
import java.util.ArrayList;

import rj.hive.database.models.Product;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
    // Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "tastery";
 
    // Table Names
    private static final String TABLE_PRODUCTS = "products";
    
    //Fields => Records
    private static final String PROD_PRIMARY_KEY    = "product_key"; 
    private static final String PRODUCT_ID     		= "product_id";
    private static final String PRODUCT_CATEGORY_ID = "category_id";
    private static final String PRODUCT_THUMB  		= "thumb";
    private static final String PRODUCT_NAME    	= "name";
    private static final String PRODUCT_DESCRIPTION = "description";
    private static final String PRODUCT_DAY   		= "day";
    private static final String PRODUCT_PRICE		= "price";
    private static final String PRODUCT_SPECIAL  	= "special";
    private static final String PRODUCT_RATING		= "rating";
    private static final String PRODUCT_REVIEWS		= "reviews";
    private static final String PRODUCT_HREF		= "href";
    
    

    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE "
            + TABLE_PRODUCTS + "(" 
    			+ PROD_PRIMARY_KEY + " INTEGER PRIMARY KEY," 
    			+ PRODUCT_ID  			+ " TEXT," 
    			+ PRODUCT_CATEGORY_ID   + " TEXT," 
    			+ PRODUCT_THUMB 		+ " TEXT," 
    			+ PRODUCT_NAME  		+ " TEXT," 
    			+ PRODUCT_DESCRIPTION 	+ " TEXT," 
    			+ PRODUCT_DAY 			+ " TEXT," 
    			+ PRODUCT_PRICE 		+ " TEXT," 
    			+ PRODUCT_SPECIAL   	+ " TEXT," 
    			+ PRODUCT_RATING 		+ " TEXT," 
    			+ PRODUCT_REVIEWS 		+ " TEXT," 
    			+ PRODUCT_HREF   		+ " TEXT"  + ")";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) 
    {
        db.execSQL(CREATE_TABLE_PRODUCTS);
    }
     
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }   
    
    public long addProduct(Product product) 
    {
    	Log.d("TASTERY", "Add Product " + product);
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        
        values.put(PRODUCT_ID, 				product.id);        
        values.put(PRODUCT_CATEGORY_ID, 	product.category_id);
        values.put(PRODUCT_THUMB, 			product.thumb);        
        values.put(PRODUCT_NAME, 			product.name);
        values.put(PRODUCT_DESCRIPTION, 	product.description);
        values.put(PRODUCT_DAY, 			product.day);     
        values.put(PRODUCT_PRICE, 			product.price);
        values.put(PRODUCT_SPECIAL, 		product.special);
        values.put(PRODUCT_RATING, 			product.rating);        
        values.put(PRODUCT_REVIEWS, 		product.reviews);
        values.put(PRODUCT_HREF, 			product.href);
        Log.d("+++Product", product.description);
        long rec_id = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return rec_id;
    }
    
    public ArrayList<Product> getAllProducts()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Product> products = new ArrayList<Product>();
        String selectQuery;
        selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;
        Cursor c = db.rawQuery(selectQuery, null);    
        
        Product product = null;
        while(c.moveToNext())
        {
        	product = new Product(
        			c.getInt(c.getColumnIndex(PROD_PRIMARY_KEY)),
        			c.getString(c.getColumnIndex(PRODUCT_ID)),
        			c.getString(c.getColumnIndex(PRODUCT_CATEGORY_ID)),
        			c.getString(c.getColumnIndex(PRODUCT_THUMB)),
        			c.getString(c.getColumnIndex(PRODUCT_NAME)),
        			c.getString(c.getColumnIndex(PRODUCT_DESCRIPTION)),
        			c.getString(c.getColumnIndex(PRODUCT_DAY)),
        			c.getString(c.getColumnIndex(PRODUCT_PRICE)),
        			c.getString(c.getColumnIndex(PRODUCT_SPECIAL)),
        			c.getString(c.getColumnIndex(PRODUCT_RATING)),
        			c.getString(c.getColumnIndex(PRODUCT_REVIEWS)),
        			c.getString(c.getColumnIndex(PRODUCT_HREF))
        	);
        	
        	products.add(product);
        }
        db.close();
        return products;  
    }
    
    public void updateProduct(Product oldProduct, Product updatedProduct)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String strFilter = PRODUCT_ID + " = '" + oldProduct.id + "'";
    	ContentValues values = new ContentValues();
        values.put(PRODUCT_CATEGORY_ID, 	updatedProduct.category_id);
        values.put(PRODUCT_THUMB, 			updatedProduct.thumb);        
        values.put(PRODUCT_NAME, 			updatedProduct.name);
        values.put(PRODUCT_DESCRIPTION, 	updatedProduct.description);
        values.put(PRODUCT_DAY, 			updatedProduct.day);     
        values.put(PRODUCT_PRICE, 			updatedProduct.price);
        values.put(PRODUCT_SPECIAL, 		updatedProduct.special);
        values.put(PRODUCT_RATING, 			updatedProduct.rating);        
        values.put(PRODUCT_REVIEWS, 		updatedProduct.reviews);
        values.put(PRODUCT_HREF, 			updatedProduct.href);
    	db.update(TABLE_PRODUCTS, values, strFilter, null);
    	db.close();    	
    }
     
    
    public void deleteProduct(Product product)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
     	String strFilter = PRODUCT_ID + " = '" + product.id + "'";
     	db.delete(TABLE_PRODUCTS, strFilter, null);
     	db.close();
    }
    
    public ArrayList<Product> getProductsPerDay(String day)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Product> products = new ArrayList<Product>();
        String selectQuery;
        selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS + " WHERE " + PRODUCT_DAY + " = '" + day + "'";
        Cursor c = db.rawQuery(selectQuery, null);    
        Log.v("TASTERY", "Total Products " + c.getCount());
        Product product = null;
        while(c.moveToNext())
        {
        	product = new Product(
        			c.getInt(c.getColumnIndex(PROD_PRIMARY_KEY)),
        			c.getString(c.getColumnIndex(PRODUCT_ID)),
        			c.getString(c.getColumnIndex(PRODUCT_CATEGORY_ID)),
        			c.getString(c.getColumnIndex(PRODUCT_THUMB)),
        			c.getString(c.getColumnIndex(PRODUCT_NAME)),
        			c.getString(c.getColumnIndex(PRODUCT_DESCRIPTION)),
        			c.getString(c.getColumnIndex(PRODUCT_DAY)),
        			c.getString(c.getColumnIndex(PRODUCT_PRICE)),
        			c.getString(c.getColumnIndex(PRODUCT_SPECIAL)),
        			c.getString(c.getColumnIndex(PRODUCT_RATING)),
        			c.getString(c.getColumnIndex(PRODUCT_REVIEWS)),
        			c.getString(c.getColumnIndex(PRODUCT_HREF))
        	);
        	products.add(product);
        }
        db.close();
        return products;  
    }
    
    
    public Product getProduct(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        
        String selectQuery;
        selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS + " WHERE " + PRODUCT_ID + " = '" + id + "'";
        Cursor c = db.rawQuery(selectQuery, null);    
        
        Product product = null;
        if(c.moveToNext())
        {
        	product = new Product(
        			c.getInt(c.getColumnIndex(PROD_PRIMARY_KEY)),
        			c.getString(c.getColumnIndex(PRODUCT_ID)),
        			c.getString(c.getColumnIndex(PRODUCT_CATEGORY_ID)),
        			c.getString(c.getColumnIndex(PRODUCT_THUMB)),
        			c.getString(c.getColumnIndex(PRODUCT_NAME)),
        			c.getString(c.getColumnIndex(PRODUCT_DESCRIPTION)),
        			c.getString(c.getColumnIndex(PRODUCT_DAY)),
        			c.getString(c.getColumnIndex(PRODUCT_PRICE)),
        			c.getString(c.getColumnIndex(PRODUCT_SPECIAL)),
        			c.getString(c.getColumnIndex(PRODUCT_RATING)),
        			c.getString(c.getColumnIndex(PRODUCT_REVIEWS)),
        			c.getString(c.getColumnIndex(PRODUCT_HREF))
        	);
        }
        db.close();
        return product;  
    }    
    
}
