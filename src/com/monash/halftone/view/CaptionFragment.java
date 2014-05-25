package com.monash.halftone.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.monash.halftone.R;
import com.monash.halftone.model.Caption;
import com.monash.halftone.model.Image;
import com.monash.halftone.model.Caption.Position;
/**
 * The CaptionFragment class is an extension of the Dialog Fragment class. 
 * It is an Alert Dialog that is shown to the screen to give the user options in adding a caption to the image.
 * The Caption fragment specifies the text for the caption, as well as the placement, below or above the image.
 * 
 * @author Jake Spicer and Ben Theobald
 *
 */
public class CaptionFragment extends DialogFragment {
	
	Caption.Position capPos; 
	String text;
	private String mText = "";
	/**
	 * The Caption Dialog Listner is an interface that any inherited methods must implement Positive and Negative click methods.
	 * This is to make sure that code is included to handle a positive and negative return from the fragment.
	 * @author Jake Spicer and Ben Theobald
	 *
	 */
	public interface CaptionDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegativeClick(DialogFragment dialog);
	}

	CaptionDialogListener mListener;

	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try{
			mListener = (CaptionDialogListener) activity;
		} catch (ClassCastException e){
			throw new ClassCastException(activity.toString()
					+ " must implement CaptionDialogListener");
		}

	}
	/**
	 * OnCreateDialog is executed when the CaptionFragment is launched.
	 * It contains code that will be executed immediately.
	 * @param savedInstanceState
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final CharSequence[] items = {"Above", "Below"};		//The strings that will be used in the dialog
		capPos = Position.ABOVE;								//set initial position

		final EditText input = new EditText(getActivity());
		input.setInputType(EditorInfo.TYPE_CLASS_TEXT);
		input.setHint("Add caption here");
		input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(40) });		//Set maximum caption length. Currently set to 40.
		builder.setView(input);

		builder.setTitle(R.string.dialog_caption)
		.setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {	//Code that executes if the user clicks "proceed"
			public void onClick(DialogInterface dialog, int id) {
				text = input.getText().toString();
				mListener.onDialogPositiveClick(CaptionFragment.this);
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {		//Code that executes if the user clicks "cancel"
			public void onClick(DialogInterface dialog, int id) {
				mListener.onDialogNegativeClick(CaptionFragment.this);
			}
		})
		.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {			//Sets the radio buttons, as well as a listener for capturing caption position
			public void onClick(DialogInterface dialog, int id){
				if ("Above".compareTo(items[id].toString()) == 0){
					capPos = Caption.Position.ABOVE ;
					Log.i("CaptionFragment", "Above");
				}else if ("Below".compareTo(items[id].toString()) == 0){
					capPos = Caption.Position.BELOW;
					Log.i("CaptionFragment", "Below");
				}
			}
		});
		// Create the AlertDialog object and return it
		return builder.create();
	} 
	/**
	 * 
	 * @return The text for the caption
	 */
	public String getText(){
		return text;

	}
	/**
	 * 
	 * @return The position of the caption
	 */
	public Caption.Position getPos(){
		return capPos;
	}
}
