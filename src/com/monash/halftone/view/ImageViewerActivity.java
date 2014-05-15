package com.monash.halftone.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.monash.halftone.R;
import com.monash.halftone.model.Image;

public class ImageViewerActivity extends Activity implements CaptionFragment.CaptionDialogListener{
	ImageView ivMain;
	Image image;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ivMain = (ImageView) this.findViewById(R.id.ivImage);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new ImageViewerFragment()).commit();
		}

	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		image.addText(CaptionFragment.getText());
		image.setCaptionPos(CaptionFragment.getPos());
			if( ((BitmapDrawable)(ivMain.getDrawable())).getBitmap() != null )
			{
				ivMain.setImageDrawable( new BitmapDrawable(getResources(), image.getImage()));
			}
		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}
}
