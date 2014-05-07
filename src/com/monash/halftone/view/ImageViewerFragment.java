package com.monash.halftone.view;

import android.app.Fragment;
import android.graphics.drawable.BitmapDrawable;
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
	Image image;
	Button bShare, bLoad, bSave;
	RadioButton rbFilter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.image_viewer_fragment, container, false);

		ivMain = (ImageView) view.findViewById(R.id.ivImage);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.bShare:
			shareImage();
			break;
		case R.id.bImageLoad:
			loadImage();
			break;
		case R.id.bSave:
			saveImage();
			break;
		}

	}

	private void loadImage(){
		//TODO Add load from gallery
	}

	private void saveImage(){
//		TODO Add save Image
	}

	private void shareImage(){
		//TODO Add share Image
	}

	private void applyImageFilter(Image.Filter filter){
		if( ((BitmapDrawable)(ivMain.getDrawable())).getBitmap() != null )
		{
			image.setFilter(this.getActivity().getApplicationContext(), filter);
			ivMain.setImageDrawable( new BitmapDrawable(getResources(), image.getFilteredImage()));
		}
	}
}
