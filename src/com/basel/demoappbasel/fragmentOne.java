package com.basel.demoappbasel;

import com.basel.demoappbasel.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class fragmentOne extends Fragment implements View.OnClickListener, OnItemClickListener {
 ListView listView;
Communicator communicator;
 int i;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		
		if(savedInstanceState==null){
			
		}else{
			
			
		}
		
	
		return view;
	}
@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		communicator = (Communicator) getActivity();
		listView = (ListView) getActivity().findViewById(R.id.listView1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.titles, android.R.layout.simple_list_item_1);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		
	
	}
@Override
public void onClick(View view) {
	// TODO Auto-generated method stub
	
}
public void onSaveInstanceState(Bundle outState) {
	super.onSaveInstanceState(outState);
	
	
	
	
}
@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int i, long arg3) {
	communicator.respond(i);
	this.i = i;
	
};
}