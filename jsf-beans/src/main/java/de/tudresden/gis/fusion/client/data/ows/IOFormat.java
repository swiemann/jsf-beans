package de.tudresden.gis.fusion.client.data.ows;

import java.io.Serializable;

public class IOFormat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * format parameter
	 */
	private String mimetype;
	private String schema;
	private String type;
	
	/**
	 * constructor
	 * @param mimetype format mimetype
	 * @param schema format schema
	 * @param type format type
	 */
	public IOFormat(String mimetype, String schema, String type){
		this.mimetype = mimetype;
		this.schema = schema;
		this.type = type;
	}
	
	/**
	 * get mimetype
	 * @return mimetype
	 */
	public String getMimetype() { 
		return mimetype; 
	}
	
	/**
	 * get schema
	 * @return schema
	 */
	public String getSchema() { 
		return schema; 
	}
	
	/**
	 * get type
	 * @return type
	 */
	public String getType() { 
		return type; 
	}
	
	@Override
	public boolean equals(Object format){
		if(!(format instanceof IOFormat))
			return false;
		if(((IOFormat) format).getMimetype().equalsIgnoreCase(getMimetype()) && 
				((IOFormat) format).getSchema().equalsIgnoreCase(getSchema()) &&
				((IOFormat) format).getType().equalsIgnoreCase(getType()))
			return true;
		return false;
	}

}
