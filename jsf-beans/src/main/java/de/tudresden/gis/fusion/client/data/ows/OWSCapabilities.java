package de.tudresden.gis.fusion.client.data.ows;

import java.io.Serializable;

public abstract class OWSCapabilities implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * OWS capabilities
	 */
	private String serviceType;
	private String version;
	
	/**
	 * constructor
	 * @param serviceType OWS type
	 * @param version OWS version
	 */
	public OWSCapabilities(String serviceType, String version){
		this.serviceType = serviceType;
		this.version = version;
	}

	/**
	 * get OWS type
	 * @return OWS type
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * get service version
	 * @return service version
	 */
	public String getVersion() {
		return version;
	}

}
