package info.androidhive.slidingmenu.model;

public class DayDrawerItem {
	
	private String title;
	private String subtitle;
	public boolean isEnabled = false;
	
	public DayDrawerItem(String title, String subtitle, boolean isEnabled){
		this.title = title;
		this.subtitle = subtitle;
		this.isEnabled  = isEnabled;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getSubtitle()
	{
		return this.subtitle;
	}
}
