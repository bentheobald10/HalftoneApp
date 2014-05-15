package com.monash.halftone.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.monash.halftone.R;
import com.monash.halftone.model.Caption;
import com.monash.halftone.model.Image;
import com.monash.halftone.model.Caption.Position;

public class CaptionFragment extends DialogFragment {
		Caption.Position capPos; 
		String text;
		private String mText = "";
		
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
        capPos = Position.ABOVE;
        
        final EditText input = new EditText(getActivity());
        input.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        input.setHint("Caption");
        builder.setView(input);
        
		builder.setTitle(R.string.dialog_caption)
               .setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
					text = input.getText().toString();
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
    public String getText(){
		return text;
    	
    }
    public Caption.Position getPos(){
    	return capPos;
    }
}
