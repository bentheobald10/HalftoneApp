package com.monash.halftone.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.monash.halftone.R;
import com.monash.halftone.model.Image;
/**
 * The ImageViewerActivity class is the activity connected with the ImageViewerFragment. 
 * The majority of the logic behind the app is handled by ImageViewerFragment, therefore this is a slim class.
 * The ImageViewerActivity passes along the return values from the Caption and Halftone Dialogs, allowing the return values to be used.
 * @author Jake Spicer and Ben Theobald
 */
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
