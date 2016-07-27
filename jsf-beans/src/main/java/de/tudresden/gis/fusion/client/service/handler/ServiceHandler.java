package de.tudresden.gis.fusion.client.service.handler;

import java.io.Serializable;

public class ServiceHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * service base URL
	 */
	private String sBaseURL;
	
	/**
	 * getter for base URL
	 * @param validate set true, if URL must be validated
	 * @return base URL
	 */
	public String getBaseURL(boolean validate) { 
		if(validate)
			validateBaseURL();
		return sBaseURL; 
	}
	
	/**
	 * validate service URL
	 * throws Exception, if validation fails
	 */
	private void validateBaseURL() {
		if(sBaseURL == null)
			throw new NullPointerException("URL must not be null");
		if(sBaseURL.isEmpty())
			throw new IllegalArgumentException("URL must not be empty");
	}
	
	/**
	 * getter for base URL
	 * @return base URL
	 */
	public String getBaseURL() { 
		return getBaseURL(true); 
	}
	
	/**
	 * setter for base URL
	 * @param sBaseURL base URL
	 */
	public void setBaseURL(String sBaseURL) { 
		this.sBaseURL = sBaseURL; 
	}

}
