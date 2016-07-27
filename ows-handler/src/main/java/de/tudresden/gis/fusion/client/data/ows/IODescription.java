package de.tudresden.gis.fusion.client.data.ows;

import java.io.Serializable;

public class IODescription implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * io description variables
	 */
	private String identifier;
	private String title;
	private IOFormatCollection formats;
	
	/**
	 * constructor
	 * @param identifier IO identifier
	 * @param title IO title
	 * @param supportedFormats supported data formats
	 * @param defaultFormat default data format
	 */
	public IODescription(String identifier, String title, IOFormatCollection formats){
		this.identifier = identifier;
		this.title = title;
		this.formats = formats;
	}
	
	/**
	 * get identifier
	 * @return IO identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	/**
	 * get title
	 * @return IO title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * get supported formats
	 * @return supported IO formats
	 */
	public IOFormatCollection getformats(){
		return formats;
	}
	
}
