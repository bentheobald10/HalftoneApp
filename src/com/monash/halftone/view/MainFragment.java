package com.monash.halftone.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.monash.halftone.R;

public class MainFragment extends Fragment implements OnClickListener{
	Button bCamera, bLoadGallery;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_menu_fragment, container, false);
		// TODO Add details
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	private void setUp(){
		
	}
	
	private void takePhoto(){
		
	}

}
