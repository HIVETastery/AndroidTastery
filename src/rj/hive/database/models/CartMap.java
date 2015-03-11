package rj.hive.database.models;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.widget.Toast;

public class CartMap 
{
    public int id;
    public int quantity;
    
    
    public String order_date;
    public String place;
    public String deliver_date;
    public String deliver_time;
    public String name;
    public String price;
    
    public Product product;
    
    public CartMap(
    			int id, 
    			int quantity, 
    			String order_date,
    			String place,
    			String delivery_date,
    			String dtime,
    			String name,
    			String price)
    {
    	this.id = id;
    	this.quantity = quantity;
    	this.order_date = order_date;
    	
    	this.place = place;
    	this.deliver_date =delivery_date;
    	this.deliver_time = dtime;
    	this.name = name;
    	this.price = price;
    }
    
    
    public String getOrderDate()
    {
    	long a = Long.parseLong(this.order_date);
    	SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
    	return sdf.format(new Date((a * 1000L)));
    }
    
    
    public void setTempDeliverDate(String tempDeliverDate)
    {
    	this.tempdeliverdate = tempDeliverDate;
    }
    
    public void setTempDeliverTime(String tempDeliverTime)
    {
    	this.tempdelivertime = tempDeliverTime;
    }
    
    public String tempdeliverdate;
    public String tempdelivertime;
    
    
	
}
