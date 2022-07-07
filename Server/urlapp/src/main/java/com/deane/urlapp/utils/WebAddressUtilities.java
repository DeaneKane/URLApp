package com.deane.urlapp.utils;

public class WebAddressUtilities {
	
	public static String prependHttp(String webAddress) {
		
		return "http://" + webAddress;
	}
	
	public static String transformToShortUrl(String longAddress) {
		
		char map[] = "teapotcups".toCharArray(); 
	      
		String hashCode = String.valueOf(longAddress.hashCode() > 0 ? longAddress.hashCode() : 
					(longAddress.hashCode() - longAddress.hashCode() - longAddress.hashCode()));
		
		StringBuilder shortUrl = new StringBuilder();
		
		for(int i = 0; i < hashCode.length(); i++) {
			int numAtIndex = Character.getNumericValue(hashCode.charAt(i));
			shortUrl.append(map[numAtIndex]);
		}    
		return shortUrl.toString();
		
	}

}
