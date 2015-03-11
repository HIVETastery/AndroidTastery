package rj.hive.tastery.fragments;

import rj.hive.tastery.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentPrivacyPolicy  extends Fragment {
	
	View rootView;
	public FragmentPrivacyPolicy(){}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		rootView = inflater.inflate(R.layout.fragment_privacypolicy, container, false);
        return rootView;
    }
}