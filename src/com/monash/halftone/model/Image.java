package com.monash.halftone.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

public class Image {
	public enum Filter {
		NONE, HALFTONE, GRAYSCALE
	};
	
	private FilteredImage originalImage;
	private FilteredImage filteredImage;
	private Caption textCaption;
	private int gridSize;
	private String filename;
	
	public Image(Uri uri, String filename, Filter filter, int gridSize){
		originalImage = new NoFilter(uri);
		this.gridSize = gridSize;
		setFilename(filename);
	}
	
	public void setFilter(Context context, Filter filter, ImageView iv){
		switch(filter)
		{
		case NONE:
			filteredImage = new NoFilter(originalImage.getUri());
			break;
		case HALFTONE:
			filteredImage = new Halftone(originalImage.getUri() , gridSize);
			break;
		case GRAYSCALE:
			filteredImage = new Grayscale(originalImage.getUri());
			break;
		}
	}
	public String getFilename(){
		return filename;
	}
	
	public void setFilename(String filename){
		this.filename = filename;
	}
	
	public void addText(String text){
		textCaption.setText(text);
	}
	
	public Bitmap getFilteredImage(){
		return filteredImage.getImage();
	}
	
	public Bitmap reset(){
		return originalImage.getImage();		
	}
}
