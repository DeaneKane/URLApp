package com.deane.urlapp.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWebAddressUtils {
		
	@Test
	public void testPrependHttp() {
		
		String prependedHttpString =  WebAddressUtilities.prependHttp("www.testurl.ie");
		
		assertEquals(prependedHttpString, "http://www.testurl.ie");	
	}
	
	@Test
	public void testTransformToShortUrl() {
		
		String shortUrl =  WebAddressUtilities.transformToShortUrl("http://testurl.ie");
		
		assertEquals(shortUrl, "ptccpucct");	
	}

}
