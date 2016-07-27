package de.tudresden.gis.fusion.client.data.ows;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class IOFormatCollection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * format collection
	 */
	Set<IOFormat> formats;
	IOFormat defaultFormat;
	
	/**
	 * empty constructor
	 */
	public IOFormatCollection() {
		this.formats = new HashSet<IOFormat>();
	}
	
	/**
	 * constructor
	 * @param formats supported formats
	 */
	public IOFormatCollection(Set<IOFormat> formats) {
		this.formats = formats;
	}
	
	/**
	 * add format to collection
	 * @param format format to be added
	 * @param isDefault flag: true if default format
	 */
	public void addFormat(IOFormat format, boolean isDefault) {
		formats.add(format);
		if(isDefault)
			defaultFormat = format;
	}
	
	/**
	 * get set of formats
	 * @return formats
	 */
	public Set<IOFormat> getFormats() {
		return formats;
	}
	
	/**
	 * get default format
	 * @return default format
	 */
	public IOFormat getDefaultFormat() {
		return defaultFormat;
	}
	
}
