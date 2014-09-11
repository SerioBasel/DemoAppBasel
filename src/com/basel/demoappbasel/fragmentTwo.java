package com.basel.demoappbasel;

import com.basel.demoappbasel.R;

import android.support.v4.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class fragmentTwo extends Fragment {
TextView textView;
String[] discriptions;
Resources resources;
int x = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_two, container, false);
	
		resources = getResources();
		discriptions = resources.getStringArray(R.array.descriptions);
		
		
	
		return view;
	}
@Override
public void onActivityCreated(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onActivityCreated(savedInstanceState);
	textView = (TextView) getActivity().findViewById(R.id.textView);
	if(savedInstanceState==null){
		
	}else{
		x = savedInstanceState.getInt("position", 0);
		textView.setText(discriptions[x]);
		
	}
	
}


@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("position", x);
		
		
	}
public void changeData(int i) {
	
	this.x = i;
	 
	 textView.setText(discriptions[i]);
	
}
}
