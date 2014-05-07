package com.monash.halftone.model;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;

public class Halftone extends FilteredImage {
	private int gridSize;
	
	public Halftone(Uri file, int gridSize, Context context){
		Bitmap oldImage;
		try {
			oldImage = MediaStore.Images.Media.getBitmap( context.getApplicationContext().getContentResolver(), file);
			width = oldImage.getWidth();
			height = oldImage.getHeight();
			this.gridSize = gridSize;

			image = Bitmap.createBitmap( ( width - ( width%gridSize ) ), ( height - ( height%gridSize ) ), Config.ARGB_8888 );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void convert() {
		
		Canvas c = new Canvas(image);										// Create canvas from Bitmap
		Paint p = new Paint();													// 
		p.setColor(Color.WHITE);
		c.drawRect(new Rect(0, 0, width, height), p);
		p.setColor(Color.BLACK);
		
		c.drawCircle(100, 100, 10, p);		

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
				c.drawCircle(w, h, grayValue, p);	
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
				int c = image.getPixel(h, j);// .getRaster().getPixel(h, j, pixel);
				rgb = rgb + c;
			}
		}
		
		// Get an average pixel intensity for the read in grid
		rgb /= (gridSize * gridSize);
		
		return rgb;
	}
}
