package com.monash.halftone.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;

public class Halftone extends FilteredImage {
	private int gridSize;
	Bitmap oldImage;
	private float MAX_RADIUS;
	
	public Halftone(Uri file, int gridSize, Context context){
		
			oldImage = BitmapFactory.decodeFile(file.toString());// MediaStore.Images.Media.getBitmap( context.getApplicationContext().getContentResolver(), file);
			width = oldImage.getWidth();
			height = oldImage.getHeight();
			this.gridSize = gridSize;
			MAX_RADIUS = (float) Math.hypot(((float) this.gridSize/2), ((float) this.gridSize/2)); // Calculate the maximum radius for the gridsize using Pythagorus

			image = Bitmap.createBitmap( ( width - ( width%gridSize ) ), ( height - ( height%gridSize ) ), Config.ARGB_8888 );
			convert();
	}

	@Override
	protected void convert() {
		
		Canvas c = new Canvas(image);										// Create canvas from Bitmap
		Paint p = new Paint();													// 
//		p.setColor(Color.WHITE);
//		c.drawRect(new Rect(0, 0, width, height), p);
		p.setColor(Color.BLACK);
		
//		c.drawCircle(100, 100, 10, p);		

		// Iterate over the gray BufferedImage for each grid to calculate the black intensity, then write this dot to halftone
		for(int h = 0; h + gridSize <= height; h += gridSize)
		{
			for(int w = 0; w + gridSize <= width; w += gridSize)
			{
				int calc = calculateRGB(w, h);
				float grayValue = 255 - calc;
//
//				// Add the dot to the hash if it doesn't exist already
//				if( !imageHash.containsKey( grayValue ) )
//					addToImagehash(grayValue);

				// Draw the dot to the halftone image
				c.drawCircle(w, h, grayValue/( (float) 256/MAX_RADIUS ), p);	
				Log.i("Halftone", calc + "");
			}
		}
	}
	
	/**
	 * Calculates the intensity of black for the corresponding grid starting at the passed location
	 * 
	 * @param x x location on the gray image to start
	 * @param y y location on the gray image to start
	 * 
	 * @return The intensity of black for the grid read in
	 */
	private int calculateRGB( int x, int y )
	{
		int  rgb = 0;
		
		// Iterate over the image for the grid, summing up the rgb values
		for( int h = x; h < x + gridSize; h++ )
		{
			for(int j = y; j < y + gridSize; j++)
			{
				int c = oldImage.getPixel(h, j);// .getRaster().getPixel(h, j, pixel);
				rgb = rgb + (Color.red(c) + Color.green(c) + Color.blue(c))/3 ;
			}
		}
		
		// Get an average pixel intensity for the read in grid
		rgb /= (gridSize * gridSize);
		
		return rgb;
	}
}
