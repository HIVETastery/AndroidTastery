package info.androidhive.slidingmenu.model;

public class QuantityDrawerItem {
	
	private String title;
	public boolean isEnabled = false;
	
	public QuantityDrawerItem(String title, boolean isEnabled){
		this.title = title;
		this.isEnabled  = isEnabled;
	}
	
	public String getTitle(){
		return this.title;
	}
}
