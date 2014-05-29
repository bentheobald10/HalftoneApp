package test.com.monash.halftone.model;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.monash.halftone.model.Halftone;

public class TestHalftone extends TestCase{

	private Halftone h;

	@Before
	public void setup()
	{
		Halftone h = new Halftone();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetGridSize0Fail()
	{
		try
		{
			Halftone h = new Halftone();
			h.setGridSize(0);
			assertFalse(false);
		}
		catch(IllegalArgumentException e)
		{
			assertTrue(true);
		}
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetGridSize2Fail()
	{
		try
		{
			Halftone h = new Halftone();
			h.setGridSize(2);
			
			assertFalse(false);
		}
		catch(IllegalArgumentException e)
		{
			assertTrue(true);
		}
	}

	@Test
	public void testSetGridSize3Success()
	{
		Halftone h = new Halftone();
		h.setGridSize(3);
		
		assertTrue(true);
	}

	@Test
	public void testSetGridSize14Success()
	{
		Halftone h = new Halftone();
		h.setGridSize(14);
		
		assertTrue(true);
	}

	@Test
	public void testSetGridSize15Success()
	{
		Halftone h = new Halftone();
		h.setGridSize(15);
		
		assertTrue(true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetGridSize16Fail()
	{
		try
		{
			Halftone h = new Halftone();
			h.setGridSize(16);
			
			assertFalse(false);
		}
		catch(IllegalArgumentException e)
		{
			assertTrue(true);
		}
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetRotationDegreesNegativeFail()
	{
		try
		{
			Halftone h = new Halftone();
			h.setRotationDegrees(-10);
			
			assertFalse(false);
		}
		catch(IllegalArgumentException e)
		{
			assertTrue(true);
		}
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetRotationDegrees0Success()
	{
		try
		{
			Halftone h = new Halftone();
			h.setRotationDegrees(0);
			
			assertFalse(false);
		}
		catch(IllegalArgumentException e)
		{
			assertTrue(true);
		}
	}

	@Test
	public void testSetRotationDegrees1Success()
	{
		Halftone h = new Halftone();
		h.setRotationDegrees(1);
		
		assertTrue(true);
	}

	@Test
	public void testSetRotationDegrees44Success()
	{
		Halftone h = new Halftone();
		h.setRotationDegrees(44);
		
		assertTrue(true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetRotationDegrees45Success()
	{
		try
		{
			Halftone h = new Halftone();
			h.setRotationDegrees(45);
			assertFalse(false);
		}
		catch(IllegalArgumentException e)
		{
			assertTrue(true);
		}
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetRotationDegrees46Fail()
	{
		try
		{
			Halftone h = new Halftone();
			h.setRotationDegrees(46);
			assertFalse(false);
		}
		catch(IllegalArgumentException e)
		{
			assertTrue(true);
		}
	}
}
