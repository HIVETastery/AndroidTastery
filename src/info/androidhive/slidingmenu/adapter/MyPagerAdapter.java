package info.androidhive.slidingmenu.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class MyPagerAdapter  extends FragmentStatePagerAdapter 
{
	Context context;
	Fragment[] fragments;
	ViewPager viewPager;
	
    public MyPagerAdapter(Context context, FragmentManager fm, Fragment[] fragments, ViewPager vp) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.viewPager = vp;
    }

    @Override
    public Fragment getItem(int pos) 
    {
    	return fragments[pos];
    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }    
    
    @Override
    public int getCount() {
        return fragments.length;
    }

    public void removePages() 
    {
        //call to ViewPage to remove the pages
        viewPager.removeAllViews();

        for(int i = 0; i < fragments.length; i++)
        {
        	fragments[i] = null;
        }

        //make this to update the pager
        viewPager.setAdapter(null);
    }    
    
}	
