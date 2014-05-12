package com.monash.halftone.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class NoFilter extends FilteredImage {

	public NoFilter(Uri uri){
		this.uri = uri;
		image = Bitmap.createBitmap(BitmapFactory.decodeFile(uri.toString()));
	}
	
	@Override
	protected void convert(Bitmap oldImage) {
		// TODO Auto-generated method stub
		
	}
}
