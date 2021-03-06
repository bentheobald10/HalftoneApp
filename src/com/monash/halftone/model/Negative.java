package com.monash.halftone.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.net.Uri;
/**
 * The Negative class extends FilteredImage and creates a black and white negative filter over an image.
 * @author Jake Spicer and Ben Theobald
 *
 */
public class Negative extends FilteredImage {
	/**
	 * The Negative Constructor that creates a Negative filter.
	 * @param uri of an image file
	 */
	public Negative(Uri uri)
	{
		this.uri = uri;
		Bitmap oldImage = BitmapFactory.decodeFile(uri.toString());
		width = oldImage.getWidth();
		height = oldImage.getHeight();
		
		image = Bitmap.createBitmap( width, height, Config.ARGB_8888 );
		convert(oldImage);
	}

	@Override
	/**
	 * The inherited convert() method from FilteredImage, the process of creating a negative image is done here.
	 */
	protected void convert(Bitmap oldImage)
	{		
		Canvas c = new Canvas(image);
		Paint p = new Paint();	
		
		// Converts to Grayscale
		ColorMatrix matrix = new ColorMatrix();
	    matrix.setSaturation(0);
	    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
		p.setColorFilter(filter);
		
		// Gets amount of pixels in image
		int pixels = oldImage.getWidth() * image.getHeight();
		int[] imagePixels = new int[pixels];
		
		// Get all the image pixels
		oldImage.getPixels(imagePixels, 0, width, 0, 0, width, height);
		
		// Iterate over the pixels and produce the negative of that pixel (255 - pixel)
		for(int i = 0; i < pixels; i++)
		{
			imagePixels[i] = 0xffffffff - imagePixels[i];
		}
		
		// Draw the bitmap from the pixel array
		c.drawBitmap(imagePixels, 0, width, 0, 0, width, height, false, p);
	}

}
