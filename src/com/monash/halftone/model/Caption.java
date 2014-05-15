package com.monash.halftone.model;

public class Caption {
	
	public enum Position {
		ABOVE, BELOW, NONE
	};
	
	private Position pos;
	private String text;
	
	public Caption(String text){
		this.text = text;
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
		this.text = text;
	}
}
