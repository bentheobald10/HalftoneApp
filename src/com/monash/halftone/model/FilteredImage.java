package com.monash.halftone.model;

import android.graphics.Bitmap;

public abstract class FilteredImage {
	protected Bitmap image;
	protected int width, height;
	
	protected abstract void convert();
	
	public Bitmap getImage(){
		return image;
	}
}
