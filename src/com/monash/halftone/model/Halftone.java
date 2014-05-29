package com.monash.halftone.model;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;

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
	private int rotationDegrees;

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
	* Generic Constructor for Halftone
	*/
	public Halftone()
	{
	}

	/**
	 * The constructor of the Halftone class that takes the Uri of the image to apply the filter to, the gridsize for the shape and the HalfotneSHape to use for the filter
	 * 
	 * @param file The Uri of the Image to apply the filter to
	 * @param gridSize The size of the halftone grid to use
	 * @param halftoneShape The halftoneShape to use for the filter
	 */
	public Halftone(Uri file, int gridSize, HalftoneShape halftoneShape, int rotationDegrees){
		uri = file;
		this.gridSize = gridSize;
		this.halftoneShape = halftoneShape;
		setRotationDegrees(rotationDegrees);

		// Read in the image as a Bitmap and get its width/ height
		Bitmap oldImage = BitmapFactory.decodeFile(uri.toString());
		width = oldImage.getWidth();
		height = oldImage.getHeight();
		MAX_RADIUS = (float) Math.hypot(((float) this.gridSize/2), ((float) this.gridSize/2)); // Calculate the maximum radius for the gridsize using Pythagorus

		image = Bitmap.createBitmap(oldImage.getWidth(), oldImage.getHeight(), Config.ARGB_8888);

		convert(oldImage);
	}

	/**
	 * Converts the passed Bitmap image to a Halftone Bitmap image
	 */
	@Override
	protected void convert(Bitmap oldImage)
	{
		// Calculate the size of the Bitmap to use for the new bitmap so that the grid can be rotated without image loss
		int size =  (int) Math.ceil( Math.hypot(((float) oldImage.getWidth()/2), ((float) oldImage.getHeight()/2)) );
		Bitmap newImage = Bitmap.createBitmap(size*2, (size*2), Config.ARGB_8888);
		Bitmap angledImage = Bitmap.createBitmap(size*2, (size*2), Config.ARGB_8888);

		// Create a Canvas and Paint to add a white rectangle to newImage
		Paint p = new Paint();
		Canvas canvas = new Canvas(newImage);
		p.setColor(Color.WHITE);
		canvas.drawRect(0, 0, newImage.getWidth(), newImage.getHeight(), p);
		p.setColor(Color.BLACK);

		canvas.save();

		// Rotate the image and then draw the old image on the new Bitmap in the center
		canvas.rotate(rotationDegrees, newImage.getWidth()/2, newImage.getHeight()/2);
		canvas.drawBitmap(oldImage, size - (oldImage.getWidth()/2), size - (oldImage.getHeight()/2), p);

		// Convert the image to halftone
		convert( newImage, angledImage);
		
		// Create a canvas for image, crop the angled image to remove whitespace and draw the resulting Bitmap to image
		canvas = new Canvas(image);
		Bitmap temp = Bitmap.createBitmap(angledImage, angledImage.getWidth()/2 - (width/2), angledImage.getHeight()/2 - (height/2), angledImage.getWidth()/2 + (width/2), angledImage.getHeight()/2 + (height/2));
		canvas.drawBitmap(temp, 0, 0, p);
	}

	/**
	 * Reads the pixels data from the image to halftone, and draws the shapes to the image passed in.
	 * It rotates the output image 315  degrees to the original orientation before drawing the shapes.
	 * 
	 * @param oldImage Image who's pixels are read to be halftoned
	 * @param newImage Image to write the halftone shapes to to create a halftone image
	 */
	private void convert(Bitmap oldImage, Bitmap newImage)
	{
		Canvas c = new Canvas(newImage);	// Create canvas from Bitmap
		Paint p = new Paint();			// Create a new Paint
		p.setColor(Color.WHITE);
		c.rotate(360 - rotationDegrees, newImage.getWidth()/2, newImage.getHeight()/2); // Rotate the image to the original orientation
		
		// Draw a WHite rectangle background
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
	}
	
	/**
	 * Gets the rotation angle in degrees
	 * 
	 * @return The rotation angle in degrees
	 */
	public int getRotationDegrees() {
		return rotationDegrees;
	}

	/**
	 * Sets the rotation angle for the halftone grid.
	 * This angle must be between 0 and 45.
	 * 
	 * @param rotationDegrees The rotation to set for the halftone grid
	 */
	public void setRotationDegrees(int rotationDegrees) {
		if(rotationDegrees < 0 || rotationDegrees > 45)
		{
			throw new IllegalArgumentException(rotationDegrees + " Rotation angle must be between 0 and 45");
		}
		
		this.rotationDegrees = rotationDegrees;
	}
	/**
	 * Gets the halftone grid size
	 * 
	 * @return The Halftone grid size
	 */
	public int getGridSize() {
		return gridSize;
	}

	/**
	 * Set the Halftone gridsize.
	 * The gridsize must be between 3 and 15.
	 * 
	 * @param gridSize The Halftone gridsize to set for the image
	 */
	public void setGridSize(int gridSize) {
		if(gridSize < 3 || gridSize > 15 )
		{
			throw new IllegalArgumentException(gridSize + " Grid Size must be between 3 and 15");
		}
		
		this.gridSize = gridSize;
	}
}
