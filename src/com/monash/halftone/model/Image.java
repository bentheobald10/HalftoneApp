package com.monash.halftone.model;

import com.monash.halftone.model.Caption.Position;
import com.monash.halftone.model.Halftone.HalftoneShape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

public class Image {
	public enum Filter {
		NONE, HALFTONE, GRAYSCALE, NEGATIVE
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
		textCaption = new Caption("");
	}
	public void setGridsize(int gridsize){
		this.gridSize = gridsize;	
	}
	
	public void setFilter(Filter filter){
		switch(filter)
		{
		case NONE:
			filteredImage = new NoFilter(originalImage.getUri());
			break;
		case HALFTONE:
			filteredImage = new Halftone(originalImage.getUri() , gridSize, HalftoneShape.DIAMOND);
			break;
		case GRAYSCALE:
			filteredImage = new Grayscale(originalImage.getUri());
			break;
		case NEGATIVE:
			filteredImage = new Negative(originalImage.getUri());
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
	
	public void setCaptionPos(Caption.Position pos){
		Log.i("Image", pos.toString());
		textCaption.setPos(pos);
	}
	
	public Bitmap getImage(){
		Bitmap image = filteredImage.getImage();
		int size =  (int) Math.ceil( Math.hypot(((float) image.getWidth()/2), ((float) image.getHeight()/2)) );
		Bitmap returnImage = Bitmap.createBitmap(size*2, (size*2) + 50, Config.ARGB_8888);
		Canvas canvas = new Canvas(returnImage);
		Position pos = textCaption.getPos();
		Paint p = new Paint();
		p.setColor(Color.RED);
		
		canvas.drawRect(0, 0, returnImage.getWidth(), returnImage.getHeight(), p);
		
		p.setTextSize(50); p.setTypeface(Typeface.DEFAULT); p.setColor(Color.BLACK);
		int x = 0, y = 0;

//		canvas.rotate(45, image.getWidth()/2, image.getHeight()/2);

//		canvas.rotate(45, size, size);
		switch(pos)
		{
			case ABOVE:
				canvas.drawBitmap(image, size-(image.getWidth()/2), size-(image.getHeight()/2) + 50, p);
				canvas.drawText(textCaption.getText(),size-(image.getWidth()/2), size-(image.getHeight()/2) + 50,p);
				break;
			case BELOW:
				canvas.drawBitmap(image, size-(image.getWidth()/2), size-(image.getHeight()/2), p);
				canvas.drawText(textCaption.getText(),size-(image.getWidth()/2), size-(image.getHeight()/2) + image.getHeight() + 50,p);
				break;
			case NONE:
				return image;
		}
				
		return returnImage;
	}
	
	public Bitmap reset(){
		return originalImage.getImage();		
	}
}
