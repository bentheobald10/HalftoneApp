package test.com.monash.halftone.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monash.halftone.model.Caption;

public class TestCaption {

	@Test
	public final void testSetTextEmptySuccess() {
		Caption c = new Caption("");
		
		assertTrue(true);
	}
	
	@Test
	public final void testSetText10Success() {
		Caption c = new Caption("1234567890");
		
		assertTrue(true);
	}
	
	@Test
	public final void testSetText42Success() {
		Caption c = new Caption("123456789012345678901234567890123456789012");
		
		assertTrue(true);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void testSetText43Fail() {
		Caption c = new Caption("1234567890123456789012345678901234567890123");
	}
}
