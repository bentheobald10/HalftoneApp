package com.monash.halftone.model;

import com.monash.halftone.model.Caption.Position;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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
		textCaption = new Caption("");
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
		Position pos = Position.ABOVE;	//hardcoded position
		//textCaption.setPos(pos);		
		textCaption.setText(text);

	}
	public void setCaptionPos(String sPos){
		Position pos = null;
		if (sPos.compareTo("Above")==0){
			pos = Position.ABOVE;
		}else if(sPos.compareTo("Below") ==0){
			pos = Position.BELOW;
		}
		textCaption.setPos(pos);
	}
	
	public Bitmap getImage(){
		Bitmap image = filteredImage.getImage();
		Bitmap returnImage = Bitmap.createBitmap(image.getWidth(), image.getHeight() + 50, Config.ARGB_8888);
		Canvas canvas = new Canvas(returnImage);
		Position pos = textCaption.getPos();
		float x = 0,y = 50;
		Paint p = new Paint();
		p.setTextSize(50); p.setTypeface(Typeface.DEFAULT); p.setColor(Color.BLACK);
		switch(pos){
		case ABOVE:
		case BELOW:
			
		}
		canvas.drawBitmap(image, x, y, p);
		canvas.drawText(textCaption.getText(),x,y,p);
		return returnImage;
	}
	
	public Bitmap reset(){
		return originalImage.getImage();		
	}
}
