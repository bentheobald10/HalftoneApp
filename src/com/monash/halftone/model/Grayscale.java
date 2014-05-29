package com.monash.halftone.model;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
/**
 * The Grayscale class implements the grayscale filter on an image. 
 * It converts a image into a black and white image.
 * @author Jake Spicer and Ben Theobald
 *
 */
public class Grayscale extends FilteredImage {
	/**
	 * A public constructor that creates a Grayscale filter of an image.
	 * @param the uri of an image file
	 */
	public Grayscale(Uri file){
		uri = file;
		Bitmap oldImage = BitmapFactory.decodeFile(uri.toString());
		width = oldImage.getWidth();
		height = oldImage.getHeight();
		
		image = Bitmap.createBitmap( width, height, Config.ARGB_8888 );
		convert(oldImage);
	}
	
	@Override
	/**
	 * The inherited convert() method does the filtering process. In this case it makes the image black and white.
	 */
	protected void convert(Bitmap oldImage) {
		Canvas c = new Canvas(image);
		Paint p = new Paint();
		ColorMatrix matrix = new ColorMatrix();
	    matrix.setSaturation(0);
	    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
		p.setColorFilter(filter);
		
		c.drawBitmap(oldImage, 0, 0, p);
	}
}
