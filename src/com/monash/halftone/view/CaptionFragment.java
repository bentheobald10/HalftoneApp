package com.monash.halftone.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.monash.halftone.R;
import com.monash.halftone.model.Image;

public class CaptionFragment extends DialogFragment {
		static String capPos = null; 
		Image image = null;
		static String text = null;
		
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
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final CharSequence[] items = {"Above", "Below"};
		builder.setTitle(R.string.dialog_caption)
               .setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
					text = "Hello World";
					mListener.onDialogPositiveClick(CaptionFragment.this);
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       mListener.onDialogNegativeClick(CaptionFragment.this);
                   }
               })
        		.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
        			public void onClick(DialogInterface dialog, int id){
        				if ("Above".equals(items[id])){
        					capPos = "Above";
        				}else if ("Below".equals(items[id])){
        					capPos = "Below";
        				}
        			}
        		});
        // Create the AlertDialog object and return it
        return builder.create();
    } 
    public static String getText(){
		return text;
    	
    }
    public static String getPos(){
    	return capPos;
    }
}
