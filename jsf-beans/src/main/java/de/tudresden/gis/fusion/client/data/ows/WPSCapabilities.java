package de.tudresden.gis.fusion.client.data.ows;

import java.util.Set;

public class WPSCapabilities extends OWSCapabilities {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * service type
	 */
	public final static String SERVICE = "WPS";
	
	/**
	 * supported processes
	 */
	private Set<String> identifiers;
	
	public WPSCapabilities(String version, Set<String> identifiers) {
		super(SERVICE, version);
		this.identifiers = identifiers;
	}

	/**
	 * get supported processes
	 * @return process identifiers
	 */
	public Set<String> getIdentifiers(){
		return identifiers;
	}

}
