package com.monash.halftone.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.monash.halftone.R;
import com.monash.halftone.model.Halftone;
import com.monash.halftone.model.Halftone.HalftoneShape;

/**
 * Similar to the Captions Fragment class, the Halftone Options Fragment extends the DialogFragment class as an implementation
 * of an alert dialog.
 * It allows the user to specify the grid angle, as well as the shape to use during the Halftone process.
 * @author Jake Spicer and Ben Theobald
 *
 */
public class HalftoneOptionsFragment extends DialogFragment {
	public interface HalftoneOptionsDialogListener {
		public void onHalfOpDialogPositiveClick(DialogFragment dialog);
		public void onHalfOpDialogNegativeClick(DialogFragment dialog);
	}

	private HalftoneOptionsDialogListener mListener;
	private static Halftone.HalftoneShape hShape;
	private SeekBar seek;
//	private TextView tvRotation;

	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try{
			mListener = (HalftoneOptionsDialogListener) activity;
		} catch (ClassCastException e){
			throw new ClassCastException(activity.toString()
					+ " must implement CaptionDialogListener");
		}

	}

	/**
	 * This executes when the HalftoneOptionsFragment begins.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		final CharSequence[] items = {"Circles", "Diamonds", "Rectangles"}; //Strings to use for radio buttons

		TextView text = new TextView(getActivity());
		text.setText("Choose which shape to Halftone your image with");		
		
		if(hShape == null)
			hShape = Halftone.HalftoneShape.CIRCLE;
		
		seek = new SeekBar(getActivity());
		seek.setMax(45);		
		builder.setView(seek);
		
		builder.setTitle(R.string.halfOp)
		.setSingleChoiceItems(items, hShape.getId(), new DialogInterface.OnClickListener() {	//Set radio buttons, and capture result.
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
	/**
	 * Returns which shape was chosen
	 * @return The Halftone Shape 
	 */
	public HalftoneShape getHShape(){
		return hShape;
	}
	
	public int getRotationAngle()
	{
		return seek.getProgress();
	}
}
