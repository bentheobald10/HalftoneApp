package com.monash.halftone.model;
/**
 * A public class that creates and alters a Caption for an image.
 * It also creates an enum Position, which holds the place of where the Caption is located relative to the image.
 * Caption has essentially two elements, the text of the caption, and the location of the caption.
 * @author Jake Spicer and Ben Theobald
 *
 */
public class Caption {
	
	private Position pos;
	private String text;
	
	/**
	 * An enum type that describes the position of the caption
	 * @author Jake Spicer and Ben Theobald
	 */
	public enum Position {
		ABOVE, BELOW, NONE
	};
	/**
	 * Constructor that creates a new Caption, it initially has a position of none (i.e. it doesn't have a position)
	 * @param text
	 */
	public Caption(String text){
		this.text = text;
		pos = Position.NONE;
	}
	/**
	 * Gets the position of the caption
	 * @return position of caption
	 */
	public Position getPos() {
		return pos;
	}
	/**
	 * Sets the position of the caption
	 * @param position of caption
	 */
	public void setPos(Position pos) {
		this.pos = pos;
	}
	/**
	 * Gets the text of the caption
	 * @return text of caption
	 */
	public String getText() {
		return text;
	}
	/**
	 * Sets the text of the caption
	 * @param text of caption
	 */
	public void setText(String text) {
		this.text = text;
	}
}
