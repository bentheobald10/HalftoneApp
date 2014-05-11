package com.monash.halftone.model;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;

public class Grayscale extends FilteredImage {
	
	public Grayscale(Uri file){
		uri = file;
		Bitmap oldImage = BitmapFactory.decodeFile(uri.toString());
		width = oldImage.getWidth();
		height = oldImage.getHeight();
		
		image = Bitmap.createBitmap( width, height, Config.ARGB_8888 );
		convert(oldImage);
	}
	
	@Override
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
