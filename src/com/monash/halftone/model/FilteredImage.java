package com.monash.halftone.model;

import android.graphics.Bitmap;
import android.net.Uri;

public abstract class FilteredImage{
	protected Bitmap image;
	protected Uri uri;
	protected int width, height;
	
	protected abstract void convert(Bitmap oldImage);
	
	public Bitmap getImage(){
		return image;
	}
	
	public Uri getUri(){
		return uri;
	}
}
