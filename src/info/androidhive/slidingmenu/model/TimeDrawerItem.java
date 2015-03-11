package info.androidhive.slidingmenu.model;

public class TimeDrawerItem {
	
	private String title;
	public boolean isEnabled = false;
	
	public TimeDrawerItem(String title, boolean isEnabled){
		this.title = title;
		this.isEnabled  = isEnabled;
	}
	
	public String getTitle(){
		return this.title;
	}
}
