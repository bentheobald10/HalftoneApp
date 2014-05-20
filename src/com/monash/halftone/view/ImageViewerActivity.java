package com.monash.halftone.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.monash.halftone.R;
import com.monash.halftone.model.Image;

public class ImageViewerActivity extends Activity implements CaptionFragment.CaptionDialogListener {
	ImageViewerFragment imageFragment;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imageFragment = new ImageViewerFragment();

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, imageFragment).commit();
		}

	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		imageFragment.onDialogPositiveClick(dialog);

		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		imageFragment.onDialogNegativeClick(dialog);
	}
}
