package com.monash.halftone.model;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;

public class Halftone extends FilteredImage {
	private int gridSize;
	private float MAX_RADIUS;
	private HalftoneShape halftoneShape;
	
	public enum HalftoneShape
	{
		CIRCLE, SQUARE, DIAMOND
	};
	
	public Halftone(Uri file, int gridSize, HalftoneShape halftoneShape){
		uri = file;
		this.gridSize = gridSize;
		this.halftoneShape = halftoneShape;
		
		Bitmap oldImage = BitmapFactory.decodeFile(uri.toString());
		width = oldImage.getWidth();
		height = oldImage.getHeight();
		MAX_RADIUS = (float) Math.hypot(((float) this.gridSize/2), ((float) this.gridSize/2)); // Calculate the maximum radius for the gridsize using Pythagorus

		image = Bitmap.createBitmap( ( width - ( width%gridSize ) ), ( height - ( height%gridSize ) ), Config.ARGB_8888 );
		convert( oldImage);
	}

	@Override
	protected void convert(Bitmap oldImage) {
		
		Canvas c = new Canvas(image);										// Create canvas from Bitmap
		Paint p = new Paint();													// 
		p.setColor(Color.WHITE);
		c.drawRect(new Rect(0, 0, width, height), p);
		p.setColor(Color.BLACK);	

		// Iterate over the gray BufferedImage for each grid to calculate the black intensity, then write this dot to halftone
		for(int h = 0; h + gridSize <= height; h += gridSize)
		{
			for(int w = 0; w + gridSize <= width; w += gridSize)
			{
				int calc = 0;
				
				// Iterate over the image for the grid, summing up the rgb values
				for( int gw = w; gw < w + gridSize; gw++ )
				{
					for(int gh = h; gh < h + gridSize; gh++)
					{
						int colour = oldImage.getPixel(gw, gh);
						calc = calc + (Color.red(colour) + Color.green(colour) + Color.blue(colour))/3 ;
					}
				}
				
				// Get an average pixel intensity for the read in grid
				calc /= (gridSize * gridSize);
				float grayValue = 255 - calc;

				switch(halftoneShape)
				{
				case CIRCLE:
					// Draw the dot to the halftone image
					c.drawCircle((w + w + gridSize)/2, (h + h + gridSize)/2, grayValue/( (float) 256/MAX_RADIUS ), p);
					break;
				case SQUARE:
					c.drawRect((w + w + gridSize)/2, (h + h + gridSize)/2, (w + w + gridSize)/2 +  grayValue/( (float) 256/MAX_RADIUS ), (h + h + gridSize)/2 +  grayValue/( (float) 256/MAX_RADIUS ), p);
					break;
				case DIAMOND:
					c.save();
					c.rotate(45, (w + w + gridSize)/2, (h + h + gridSize)/2);
					c.drawRect((w + w + gridSize)/2, (h + h + gridSize)/2, (w + w + gridSize)/2 +  grayValue/( (float) 256/MAX_RADIUS ), (h + h + gridSize)/2 +  grayValue/( (float) 256/MAX_RADIUS ), p);
					c.restore();
					break;
				}
			}
		}
	}
}
