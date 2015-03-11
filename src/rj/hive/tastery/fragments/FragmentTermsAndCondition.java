package rj.hive.tastery.fragments;

import rj.hive.tastery.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentTermsAndCondition   extends Fragment {
	
	View rootView;
	public FragmentTermsAndCondition(){}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		rootView = inflater.inflate(R.layout.fragment_termsandcondition, container, false);
        return rootView;
    }
}