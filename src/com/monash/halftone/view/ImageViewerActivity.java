package com.monash.halftone.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.monash.halftone.R;
import com.monash.halftone.model.Image;

public class ImageViewerActivity extends Activity implements CaptionFragment.CaptionDialogListener, HalftoneOptionsFragment.HalftoneOptionsDialogListener  {
	ImageViewerFragment imageFragment;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageFragment = new ImageViewerFragment();

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, imageFragment).commit();
		}

	}

	public void onDialogPositiveClick(DialogFragment dialog) {
		imageFragment.onCapDialogPositiveClick(dialog);


	}

	public void onDialogNegativeClick(DialogFragment dialog) {
		imageFragment.onCapDialogNegativeClick(dialog);
	}

	@Override
	public void onHalfOpDialogPositiveClick(DialogFragment dialog) {
		imageFragment.onHalfOpDialogPositiveClick(dialog);

	}

	@Override
	public void onHalfOpDialogNegativeClick(DialogFragment dialog) {
		imageFragment.onHalfOpDialogNegativeClick(dialog);
	}

}
