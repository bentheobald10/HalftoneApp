package com.monash.halftone.model;

import android.graphics.Bitmap;
import android.net.Uri;
/**
 * FilteredImage is an abstract class that is extended by various filter classes
 * @author Jake Spicer and Ben Theobald
 *
 */
public abstract class FilteredImage{
	protected Bitmap image;
	protected Uri uri;
	protected int width, height;
	/**
	 * An abstract method that will be inherited by classes that extend FilteredImage. 
	 * Each filter class will override this method with it's own filtering logic.
	 * @param oldImage
	 */
	protected abstract void convert(Bitmap oldImage);
	/**
	 * Gets a bitmap filtered image
	 * @return a Bitmap Image
	 */
	public Bitmap getImage(){
		return image;
	}
	/**
	 * Gets the URI of a FilteredImage
	 * @return uri of FilteredImage
	 */
	public Uri getUri(){
		return uri;
	}
}
