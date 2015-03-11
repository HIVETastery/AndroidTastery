package info.androidhive.slidingmenu.model;

public class PlaceDrawerItem {
	
	private String id;
	private String title;
	private String subtitle;
	public boolean isEnabled = false;
	
	public PlaceDrawerItem(String id, String title, String subtitle, boolean isEnabled){
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.isEnabled  = isEnabled;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getSubtitle()
	{
		return this.subtitle;
	}
}
