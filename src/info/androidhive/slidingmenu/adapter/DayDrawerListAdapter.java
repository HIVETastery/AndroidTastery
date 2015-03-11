
package info.androidhive.slidingmenu.adapter;

import info.androidhive.slidingmenu.model.DayDrawerItem;
import info.androidhive.slidingmenu.model.NavDrawerItem;

import java.util.ArrayList;

import rj.hive.tastery.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DayDrawerListAdapter extends BaseAdapter 
{

	private Typeface font;
	private Context context;
	private ArrayList<DayDrawerItem> navDrawerItems;
    public DayDrawerListAdapter(Context context, ArrayList<DayDrawerItem> navDrawerItems) {
		this.context = context;
		this.navDrawerItems = navDrawerItems;
		font = Typeface.createFromAsset(context.getAssets(), "fonts/brandon/Brandon_reg.otf");
    }
    

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
        TextView txtTitle = new TextView(context);
        txtTitle.setTextAppearance(context, android.R.style.TextAppearance_DeviceDefault_Medium);
        txtTitle.setTypeface(font);
        if(navDrawerItems.get(position).isEnabled)
        	txtTitle.setTextColor(context.getResources().getColor(R.color.black));
        else
        	txtTitle.setTextColor(context.getResources().getColor(R.color.gray));
        txtTitle.setText(" " + navDrawerItems.get(position).getTitle() + " ");
        txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        txtTitle.setBackgroundColor(Color.WHITE);
           
        
        
        //GradientDrawable gd = new GradientDrawable();
        //gd.setColor(0xFFFFFFFF); // Changes this drawbale to use a single color instead of a gradient
        //gd.setCornerRadius(2);
        //gd.setStroke(1, 0xFF005375);
        //txtTitle.setBackgroundDrawable(gd);
        
        
        return txtTitle;
	}
    
}