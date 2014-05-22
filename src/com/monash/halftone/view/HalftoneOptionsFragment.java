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
import com.monash.halftone.model.Halftone;
import com.monash.halftone.model.Halftone.HalftoneShape;
import com.monash.halftone.model.Image;
import com.monash.halftone.model.Caption.Position;
import com.monash.halftone.view.CaptionFragment.CaptionDialogListener;


public class HalftoneOptionsFragment extends DialogFragment {
	public interface HalftoneOptionsDialogListener {
		public void onHalfOpDialogPositiveClick(DialogFragment dialog);
		public void onHalfOpDialogNegativeClick(DialogFragment dialog);
	}

	HalftoneOptionsDialogListener mListener;
	static Halftone.HalftoneShape hShape;

	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try{
			mListener = (HalftoneOptionsDialogListener) activity;
		} catch (ClassCastException e){
			throw new ClassCastException(activity.toString()
					+ " must implement CaptionDialogListener");
		}

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		final CharSequence[] items = {"Circles", "Diamonds", "Rectangles"};

		TextView text = new TextView(getActivity());
		text.setText("Choose which shape to Halftone your image with");
		//		builder.setView(text);
		if(hShape == null)
			hShape = Halftone.HalftoneShape.CIRCLE;

		builder.setTitle(R.string.halfOp)
		//		.setView(text)
		.setSingleChoiceItems(items, hShape.getId(), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if ("Circles".compareTo(items[which].toString()) == 0){
					hShape = Halftone.HalftoneShape.CIRCLE;
				} else if("Diamonds".compareTo(items[which].toString()) == 0){
					hShape = Halftone.HalftoneShape.DIAMOND;
				} else if("Rectangles".compareTo(items[which].toString()) == 0){
					hShape = Halftone.HalftoneShape.RECTANGLE;
				}
			}
		})
		.setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mListener.onHalfOpDialogPositiveClick(HalftoneOptionsFragment.this);
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mListener.onHalfOpDialogNegativeClick(HalftoneOptionsFragment.this);
			}
		});





		// Create the AlertDialog object and return it
		return builder.create();
	} 
	public HalftoneShape getHShape(){
		return hShape;
	}
}
