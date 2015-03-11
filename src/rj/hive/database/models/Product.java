package rj.hive.database.models;

public class Product 
{
    public int key;
    public String id;
    public String category_id;
    public String thumb;
    public String name;
    public String description;
    public String day;
    public String price;
    public String special;
    public String rating;
    public String reviews;
    public String href;
    
    public static int CompareProducts(Product prod1, Product prod2)
    {
    	//0 not same product, 1 needs updating, 2 same shit
    	if(!prod1.id.equals(prod2.id))
    	{
    		return 0;
    	}
    	else if(!prod1.category_id.equals(prod2.category_id))
    	{
    		return 1;
    	}
    	else if(!prod1.day.equals(prod2.day))
    	{
    		return 1;
    	}
    	else if(!prod1.description.equals(prod2.description))
    	{
    		return 1;
    	}
    	else if(!prod1.href.equals(prod2.href))
    	{
    		return 1;
    	}
    	else if(!prod1.name.equals(prod2.name))
    	{
    		return 1;
    	}
    	else if(!prod1.price.equals(prod2.price))
    	{
    		return 1;
    	}	
    	else if(!prod1.rating.equals(prod2.rating))
    	{
    		return 1;
    	}	
    	else if(!prod1.reviews.equals(prod2.reviews))
    	{
    		return 1;
    	}	
    	else if(!prod1.special.equals(prod2.special))
    	{
    		return 1;
    	}	
    	else if(!prod1.thumb.equals(prod2.thumb))
    	{
    		return 1;
    	}		
    	else 
    		return 2;
    }
    
    
	public Product(String id, String category_id, String thumb,
			String name, String description, String day, String price,
			String special, String rating, String reviews, String href) 
	{
		super();
		this.id = id;
		this.category_id = category_id;
		this.thumb = thumb;
		this.name = name;
		this.description = description;
		this.day = day;
		this.price = price;
		this.special = special;
		this.rating = rating;
		this.reviews = reviews;
		this.href = href;
	}
 
	public Product(int key, String id, String category_id, String thumb,
			String name, String description, String day, String price,
			String special, String rating, String reviews, String href) 
	{
		super();
		this.key = key;
		this.id = id;
		this.category_id = category_id;
		this.thumb = thumb;
		this.name = name;
		this.description = description;
		this.day = day;
		this.price = price;
		this.special = special;
		this.rating = rating;
		this.reviews = reviews;
		this.href = href;
	}	
	
}
