package com.monash.halftone;

import android.graphics.Bitmap;

public abstract class Image {
	private Bitmap img;
	
	public abstract void save(String filename);
	public abstract void load(String filename);
	public abstract void share();
}
