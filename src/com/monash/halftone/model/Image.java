package com.monash.halftone.model;

import com.monash.halftone.model.Caption.Position;
import com.monash.halftone.model.Halftone.HalftoneShape;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;

/**
 * The Image class creates the bitmap that is displayed with a caption.
 * It has references to a FilteredImage and a Caption, and then returns the Image created by combining both of these.
 * It provides the ability to set the Image Filter to use and updates it's Image based on the Filter chosen.
 * 
 * @author Ben Theobald and Jake Spicer
 *
 */
public class Image {
	/**
	 * An enum class that provides definitions for the different Filters available on a FilteredImage.
	 * Any new filters will need to be added here to be referenced correctly
	 *
	 *@author Ben Theobald and Jake Spicer
	 *
	 */
	public enum Filter {
		NONE, HALFTONE, GRAYSCALE, NEGATIVE
	};
	
	private FilteredImage originalImage;
	private FilteredImage filteredImage;
	private HalftoneShape halftoneShape;
	private Caption textCaption;
	private int gridSize;
	private int rotationAngle;
	private String filename;
	
	/**
	 * The constructor for creating a base Image object.
	 * 
	 * @param uri The Uri of where the Image Bitmap is located in the system to be read
	 * @param filename The Filename of the image to be set
	 * @param filter The Filter to apply to the created Image
	 * @param gridSize The gridsize to use for the Halftone Image, this will be null or 0 for other Images
	 */
	public Image(Uri uri, String filename, Filter filter, int gridSize){
		originalImage = new NoFilter(uri);
		this.gridSize = gridSize;
		rotationAngle = 0;
		halftoneShape = Halftone.HalftoneShape.CIRCLE;
		setFilename(filename);
		textCaption = new Caption("");
	}
	/**
	 * Sets the grid Size for the Halftone Image
	 * @param gridsize
	 */
	public void setGridsize(int gridsize){		
		this.gridSize = gridsize;	
	}
	
	/**
	 * Set the Filter for the Image.
	 * Possible values are NONE, HALFTONE, GRAYSCALE and NEGATIVE
	 * 
	 * @param filter The filter to apply to the Image
	 */
	public void setFilter(Filter filter){
		// Switch on the filter provided
		switch(filter)
		{
		case NONE:
			filteredImage = new NoFilter(originalImage.getUri());
			break;
		case HALFTONE:
			filteredImage = new Halftone(originalImage.getUri() , gridSize, halftoneShape, rotationAngle);
			break;
		case GRAYSCALE:
			filteredImage = new Grayscale(originalImage.getUri());
			break;
		case NEGATIVE:
			filteredImage = new Negative(originalImage.getUri());
			break;
		}
	}
	
	/**
	 * Get the filename of the Image
	 * 
	 * @return The filename of the Image
	 */
	public String getFilename(){
		return filename;
	}
	
	/**
	 * Set the filename of the Image
	 * 
	 * @param filename The filename to set for the Image
	 */
	public void setFilename(String filename){
		this.filename = filename;
	}
	
	/**
	 * The text of the caption to add to the Image
	 * 
	 * @param text The text the caption should display
	 */
	public void addText(String text){
		textCaption.setText(text);
	}
	
	/**
	 * Set the caption position relative to the Image.
	 * Possible values are ABOVE or BELOW
	 * 
	 * @param pos The position of the Caption relative to the Image
	 */
	public void setCaptionPos(Caption.Position pos){
		Log.i("Image", pos.toString());
		textCaption.setPos(pos);
	}
	
	public void setHalftoneShape(Halftone.HalftoneShape shape)
	{
		halftoneShape = shape;
	}
	
	public void setRotationAngle(int rotation)
	{
		rotationAngle = rotation;
	}
	
	/**
	 * Combines the FilteredImage and the Caption to a Bitmap Image to display
	 * 
	 * @return A Bitmap Image that combines the Image and the Caption
	 */
	public Bitmap getImage(){
		// Get the FilteredImage object
		Bitmap image = filteredImage.getImage();
		Bitmap returnImage = Bitmap.createBitmap(image.getWidth(), image.getHeight() + 50, Config.ARGB_8888);
		
		Canvas canvas = new Canvas(returnImage);
		Position pos = textCaption.getPos();
		Paint p = new Paint();
		
		// Set default text size to 50 pixels
		p.setTextSize(50); p.setTypeface(Typeface.DEFAULT); p.setColor(Color.BLACK);
		
		// Switch to determine where the caption is placed. Image is placed in the middle of the canvas
		switch(pos)
		{
			case ABOVE:
				canvas.drawBitmap(image, 0, 50, p);
				canvas.drawText(textCaption.getText(), 0, 50, p);
				break;
			case BELOW:
				canvas.drawBitmap(image, 0, 0, p);
				canvas.drawText(textCaption.getText(), 0, image.getHeight() + 50, p);
				break;
			case NONE:
				return image;
		}
				
		return returnImage;
	}
	
	/**
	 * Reset the Image to how it was originally passed
	 * 
	 * @return The original image passed
	 */
	public Bitmap reset(){
		return originalImage.getImage();		
	}
}
