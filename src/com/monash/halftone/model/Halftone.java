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

/**
 * The Halftone class implements the Halftone filter to the current image.
 * It allows the user to select the shape to use, Circle, Diamond or Rectangle, and also the grid angle to rotate to, 0-45.
 * 
 * @author Ben Theobald and Jake Spicer
 *
 */
public class Halftone extends FilteredImage {
	private int gridSize;
	private float MAX_RADIUS;
	private HalftoneShape halftoneShape;
	
	/**
	 * An enum class that defines the shape to use for the Halftone filter
	 * 
	 * @author Ben Theobald and Jake Spicer
	 *
	 */
	public enum HalftoneShape
	{
		CIRCLE(0),
		DIAMOND(1),
		RECTANGLE(2);
		
		/**
		 * Id of the shape used
		 */
		private int id;
		
		/**
		 * Sets the Halftone shape to use, and sets the Id
		 * 
		 * @param id The id of the HalftoneShape to use
		 */
		private HalftoneShape(int id)
		{
			this.id = id;
		}
		
		/**
		 * Gets the Id of the HalftoneShape that is selected
		 * 		
		 * @return The id of the HalftoneShape used
		 */
		public int getId()
		{
			return this.id;
		}
	};
	
	/**
	 * The constructor of the Halftone class that takes the Uri of the image to apply the filter to, the gridsize for the shape and the HalfotneSHape to use for the filter
	 * 
	 * @param file The Uri of the Image to apply the filter to
	 * @param gridSize The size of the halftone grid to use
	 * @param halftoneShape The halftoneShape to use for the filter
	 */
	public Halftone(Uri file, int gridSize, HalftoneShape halftoneShape){
		uri = file;
		this.gridSize = gridSize;
		this.halftoneShape = halftoneShape;
		
		// Read in the image as a Bitmap and get its width/ height
		Bitmap oldImage = BitmapFactory.decodeFile(uri.toString());
		width = oldImage.getWidth();
		height = oldImage.getHeight();
		MAX_RADIUS = (float) Math.hypot(((float) this.gridSize/2), ((float) this.gridSize/2)); // Calculate the maximum radius for the gridsize using Pythagorus
		
		// Calculate the size of the Bitmap to use for the new bitmap so that the grid can be rotated without image loss
		int size =  (int) Math.ceil( Math.hypot(((float) oldImage.getWidth()/2), ((float) oldImage.getHeight()/2)) );
		Bitmap returnImage = Bitmap.createBitmap(size*2, (size*2), Config.ARGB_8888);
		
		// Create Bitmap for the image and run the convert() to make it halftoned
//		image = Bitmap.createBitmap( ( width - ( width%gridSize ) ), ( height - ( height%gridSize ) ), Config.ARGB_8888 );
//		image = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		image = Bitmap.createBitmap(returnImage.getWidth(), returnImage.getHeight(), Config.ARGB_8888);
		
		Paint p = new Paint();
		Canvas canvas = new Canvas(returnImage);
		p.setColor(Color.WHITE);
		canvas.drawRect(0, 0, returnImage.getWidth(), returnImage.getHeight(), p);
		p.setColor(Color.BLACK);
		
		canvas.save();

		// Draw the old image on the new Bitmap in the center, then rotate 45 degrees
		canvas.rotate(45, returnImage.getWidth()/2, returnImage.getHeight()/2);
		canvas.drawBitmap(oldImage, size - (oldImage.getWidth()/2), size - (oldImage.getHeight()/2), p);
		
		// Convert the image to halftone
		convert( returnImage);
//		
//		Canvas c = new Canvas(image);
//		p.setColor(Color.WHITE);
//		c.drawRect(new Rect(0, 0, image.getWidth(), image.getHeight()), p);
//		c.rotate(45, image.getWidth()/2, image.getHeight()/2);
//		c.drawBitmap(image, 0, 0, p);
		
		// Restore the orientation and get the pixels changed
//		canvas.restore();
//		int[] pixels = new int[oldImage.getWidth()*oldImage.getHeight()];
//		
//		returnImage.getPixels(pixels, 0, oldImage.getWidth(), size - (oldImage.getWidth()/2), size - (oldImage.getHeight()/2), oldImage.getWidth(), oldImage.getHeight());
		
//		image =  Bitmap.createBitmap(pixels, oldImage.getWidth(), oldImage.getHeight(), Config.ARGB_8888);
		
//		image = Bitmap.createBitmap(returnImage);
	}

	/**
	 * Converts the passed Bitmap image to a Halftone Bitmap image
	 */
	@Override
	protected void convert(Bitmap oldImage) {
		Bitmap intermediate = Bitmap.createBitmap(oldImage.getWidth(), oldImage.getHeight(), Config.ARGB_8888);
		
		Canvas c = new Canvas(image);	// Create canvas from Bitmap
		Paint p = new Paint();			// Create a new Paint
		p.setColor(Color.WHITE);
		c.rotate(315, intermediate.getWidth()/2, intermediate.getHeight()/2);
		
		int imageWidth = oldImage.getWidth();
		int imageHeight = oldImage.getHeight();
		c.drawRect(new Rect(0, 0, imageWidth, imageHeight), p);
		p.setColor(Color.BLACK);	

		// Iterate over the gray BufferedImage for each grid to calculate the black intensity, then write this dot to halftone
		for(int h = 0; h + gridSize <= imageHeight; h += gridSize)
		{
			for(int w = 0; w + gridSize <= imageWidth; w += gridSize)
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

				// Calculate the midpoints and the intensity to use
				int midPointWidth = (w + w + gridSize)/2;
				int midPointHeight = (h + h + gridSize)/2;
				float grayIntensity;
				
				// Switch on the HalftoneShape to determine what type of shape to draw
				switch(halftoneShape)
				{
				case CIRCLE:
					// Draw the dot to the halftone image
					grayIntensity = grayValue/( (float) 256/MAX_RADIUS );
					c.drawCircle(midPointWidth, midPointHeight, grayIntensity, p);
					break;
				case RECTANGLE:
					// Draw a rectangle to the halftone image
					grayIntensity = grayValue/( (float) 256/(gridSize/2) );
//					Log.i("halftone", Integer.toString(w) + " " + Integer.toString(h) + " " + Integer.toString(midPointWidth) + " " + Integer.toString(midPointHeight) + " " + Float.toString(grayIntensity));
					c.drawRect(midPointWidth - grayIntensity, midPointHeight - (grayIntensity/2), midPointWidth +  grayIntensity, midPointHeight +  (grayIntensity/2), p);
					break;
				case DIAMOND:
					c.save();		// Save the canvas state to return to after rotating 
					c.rotate(45, midPointWidth, midPointHeight);	// Rotate the canvas at the midpoint of the next diamond
					grayIntensity = grayValue/( (float) 256/MAX_RADIUS );
					c.drawRect(midPointWidth, midPointHeight, midPointWidth +  grayIntensity, midPointHeight +  grayIntensity, p);	// Draw a rectangle to look like a diamond after rotation
					c.restore();	// Restore the canvas to the previous orientation before rotation
					break;
				}
			}
		}
//		
//		int[] pixels = new int[image.getWidth()*image.getHeight()];
//		intermediate.getPixels(pixels, 0, oldImage.getWidth(), intermediate.getWidth() - (image.getWidth()/2), intermediate.getHeight() - (image.getHeight()/2), image.getWidth(), image.getHeight());
//		
//		Canvas canvas = new Canvas(image);
//		p.setColor(Color.WHITE);
//		canvas.drawRect(new Rect(0, 0, image.getWidth(), image.getHeight()), p);
//		c.drawBitmap(pixels, 0, stride, x, y, imageWidth, imageHeight, hasAlpha, paint)
//		image =  Bitmap.createBitmap(pixels, oldImage.getWidth(), oldImage.getHeight(), Config.ARGB_8888);
	}
}
