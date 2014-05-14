package com.monash.halftone.model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.monash.halftone.R;

public class CaptionFragment extends DialogFragment {
		int capPos = 0; //wrong spot
		Image image = null; //wrong spot
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final CharSequence[] items = {"Above", "Below"};
		builder.setMessage(R.string.dialog_caption)
               .setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
					image.addText("Hello World");
					if(capPos == 1){
               			image.setCaptionPos("Above");
               		} else if(capPos == 2){
               			image.setCaptionPos("Below");
               		}
               		
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               })
        		.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
        			public void onClick(DialogInterface dialog, int id){
        				if ("Above".equals(items[id])){
        					capPos = 1;
        				}else if ("Below".equals(items[id])){
        					capPos = 2;
        				}
        			}
        		});
        // Create the AlertDialog object and return it
        return builder.create();
    } 
    public String getText(){
		return null;
    	
    }
    public int getPos(){
    	return (Integer) null;
    }
}
