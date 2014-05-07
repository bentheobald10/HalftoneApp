package com.monash.halftone.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

public class Image {
	public enum Filter {
		NONE, HALFTONE, GRAYSCALE
	};
	
	private FilteredImage originalImage;
	private FilteredImage filteredImage;
	private Caption textCaption;
	private int gridSize;
	private String filename;
	
	public Image(String filename, Filter filter){
		
	}
	
	public void setFilter(Context context, Filter filter){
		switch(filter)
		{
		case NONE:
			filteredImage = new NoFilter();
			break;
		case HALFTONE:
			filteredImage = new Halftone(originalImage.getUri() , gridSize, context);
			break;
		case GRAYSCALE:
			filteredImage = new Grayscale();
			break;
		}
	}
	
	public void rename(String newName){
		
	}
	
	public void addText(String text){
		
	}
	
	public Bitmap getFilteredImage(){
		
		return null;
	}
	
	public void reset(){
		
	}
}
