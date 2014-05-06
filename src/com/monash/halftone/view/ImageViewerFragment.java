package com.monash.halftone.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.monash.halftone.R;
import com.monash.halftone.model.Image;

public class ImageViewerFragment extends Fragment implements OnClickListener{
	ImageView ivMain;
	Button bShare, bLoad, bSave;
	RadioButton rbFilter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.image_viewer_fragment, container, false);
		// TODO Add details
		return view;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	private void loadImage(String file){
		
	}
	
	private void saveImage(String filename){
		
	}
	
	private void shareImage(){
		
	}
	
	private void createImage(Image.Filter filter){
		
	}
}
