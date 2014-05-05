package com.monash.halftone.model;

import android.graphics.Bitmap;

public class Image {
	public enum Filter {
		NONE, HALFTONE, GRAYSCALE
	};
	
	private FilteredImage originalImage;
	private FilteredImage filteredImage;
	private Caption textCaption;
	private String filename;
	
	public Image(String filename, Filter filter){
		
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
