package com.monash.halftone.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.monash.halftone.R;
import com.monash.halftone.model.Caption;
import com.monash.halftone.model.Image;
import com.monash.halftone.model.Caption.Position;
import com.monash.halftone.view.CaptionFragment.CaptionDialogListener;


public class HalftoneOptionsFragment extends DialogFragment {
	public interface HalftoneOptionsDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegativeClick(DialogFragment dialog);
	}

	HalftoneOptionsDialogListener mListener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		final CharSequence[] items = {"Circles", "Diamonds", "Rectangles"};

		TextView text = new TextView(getActivity());
		text.setText("Choose which shape to Halftone your image with");
//		builder.setView(text);
		
		
		builder.setTitle(R.string.halfOp)
		.setView(text)
		.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});

	


	
		// Create the AlertDialog object and return it
		return builder.create();
	} 
}
