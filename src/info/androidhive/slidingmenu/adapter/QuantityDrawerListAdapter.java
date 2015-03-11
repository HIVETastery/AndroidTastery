
package info.androidhive.slidingmenu.adapter;

import info.androidhive.slidingmenu.model.DayDrawerItem;
import info.androidhive.slidingmenu.model.NavDrawerItem;
import info.androidhive.slidingmenu.model.PlaceDrawerItem;
import info.androidhive.slidingmenu.model.QuantityDrawerItem;

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

public class QuantityDrawerListAdapter extends BaseAdapter 
{

	private Typeface font;
	private Context context;
	private ArrayList<QuantityDrawerItem> navDrawerItems;
    public QuantityDrawerListAdapter(Context context, ArrayList<QuantityDrawerItem> navDrawerItems) {
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
        txtTitle.setTextAppearance(context, android.R.style.TextAppearance_DeviceDefault_Small);
        txtTitle.setTypeface(font);
        if(navDrawerItems.get(position).isEnabled)
        	txtTitle.setTextColor(context.getResources().getColor(R.color.black));
        else
        	txtTitle.setTextColor(context.getResources().getColor(R.color.gray));
        txtTitle.setText(" " + navDrawerItems.get(position).getTitle() + " ");
        txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        txtTitle.setBackgroundColor(Color.WHITE);
           
        return txtTitle;
	}
}