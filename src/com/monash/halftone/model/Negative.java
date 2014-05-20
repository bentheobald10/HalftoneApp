package com.monash.halftone.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.net.Uri;

public class Negative extends FilteredImage {
	
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
	protected void convert(Bitmap oldImage)
	{		
		Canvas c = new Canvas(image);
		Paint p = new Paint();	
		
		ColorMatrix matrix = new ColorMatrix();
	    matrix.setSaturation(0);
	    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
		p.setColorFilter(filter);
		
		int pixels = oldImage.getWidth() * image.getHeight();
		int[] imagePixels = new int[pixels];
		
		oldImage.getPixels(imagePixels, 0, width, 0, 0, width, height);
		
		for(int i = 0; i < pixels; i++)
		{
			imagePixels[i] = 0xffffffff - imagePixels[i];
		}
		
		c.drawBitmap(imagePixels, 0, width, 0, 0, width, height, false, p);
	}

}
