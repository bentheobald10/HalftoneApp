package com.monash.halftone.model;

public class Caption {
	
	public enum Position {
		ABOVE, BELOW, NONE
	};
	
	private Position pos;
	private String text;
	
	public Caption(String text){
		setText(text);
		pos = Position.NONE;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if(text.length() > 42 )
		{
			throw new IllegalArgumentException("Caption text must be less than 42 characters");
		}
		
		this.text = text;
	}
}
