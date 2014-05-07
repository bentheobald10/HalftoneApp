package com.monash.halftone.model;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class Grayscale extends FilteredImage {

	public Grayscale(){
		
	}
	
	@Override
	protected void convert() {
		ColorMatrix matrix = new ColorMatrix();
	    matrix.setSaturation(0);
	    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
	    
		
	}
}
