package rj.hive.tastery.fragments;

import java.util.ArrayList;

import rj.hive.tastery.R;

import info.androidhive.slidingmenu.adapter.MyPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentHelpFAQ extends Fragment {
	
	View rootView;
	public FragmentHelpFAQ(){}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		rootView = inflater.inflate(R.layout.fragment_helpfaq, container, false);
        return rootView;
    }
}