package de.tudresden.gis.fusion.client.data.ows;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class WFSLayer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * WFS layer parameter
	 */
	private String identifier;
	private Set<String> supportedSRS = new HashSet<String>();
	private String lowerCorner;
	private String upperCorner;
	
	/**
	 * constructor
	 * @param identifier WFS layer identifier
	 * @param supportedSRS supported reference systems
	 * @param lowerCorner lower extent of bbox
	 * @param upperCorner upper extent of bbox
	 */
	public WFSLayer(String identifier, Set<String> supportedSRS, String lowerCorner, String upperCorner) {
		this.identifier = identifier;
		this.supportedSRS = supportedSRS;
		this.lowerCorner = lowerCorner;
		this.upperCorner = upperCorner;
	}
	
	/**
	 * get identifier
	 * @return WFS layer identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	/**
	 * get supported reference systems
	 * @return supported reference systems
	 */
	public Set<String> getSupportedSRS() {
		return supportedSRS;
	}
	
	/**
	 * get lower extent of bbox
	 * @return lower extent of bbox
	 */
	public String getLowerCorner() {
		return lowerCorner;
	}
	
	/**
	 * get upper extent of bbox
	 * @return upper extent of bbox
	 */
	public String getUpperCorner() {
		return upperCorner;
	}
	
	/**
	 * check if given SRS is supported
	 * @param srs input srs
	 * @return true if srs is supported
	 */
	public boolean isSupportedSRS(String srs){
		return getSupportedSRS().contains(srs);
	}
	
	/**
	 * get extent [minx,miny,maxx,maxy]
	 * @return extent
	 */
	public double[] getExtent() {
		if(lowerCorner == null || upperCorner == null)
			return null;
		String[] aLc = this.getLowerCorner().split(" ");
		String[] aUc = this.getUpperCorner().split(" ");
		return new double[]{Double.parseDouble(aLc[0]), Double.parseDouble(aLc[1]), 
				Double.parseDouble(aUc[0]), Double.parseDouble(aUc[1])};
	}
	
	/**
	 * get center coordinate for layer [x,y]
	 * @return center coordinate
	 */
	public double[] getCenter() {
		double[] extent = this.getExtent();
		if(extent == null) 
			return null;
		return new double[]{((extent[0] + extent[2]) / 2), ((extent[1] + extent[3]) / 2)};
	}
	
	/**
	 * get extent for provided layer as string (minx,miny,maxx,maxy), required for OpenLayers
	 * @return extent or null, of layer does not exist
	 */
	public String getExtentAsString() {
		double[] extent = getExtent();
		if(extent == null) 
			return null;
		return "[" + String.format(Locale.ENGLISH, "%.8f", extent[0]) + ", " + 
			String.format(Locale.ENGLISH, "%.8f", extent[1]) + ", " + 
			String.format(Locale.ENGLISH, "%.8f", extent[2]) + ", " + 
			String.format(Locale.ENGLISH, "%.8f", extent[3]) + "]";
	}
	
	/**
	 * get srs shorts (EPSG:{code}), required for OpenLayers
	 * @return srs shorts or empty set, if layer does not exist or srs cannot be identified
	 */
	public Set<String> getEPSGCodeShorts() {
		Set<String> supportedSRS = getSupportedSRS();
		if(supportedSRS == null || supportedSRS.isEmpty())
			return null;
		Set<String> srsShorts = new HashSet<String>();
		for(String srs : supportedSRS){
			String srsShort = getEPSGCodeShort(srs);
			if(srsShort != null)
				srsShorts.add(srsShort);
		}
		return srsShorts;
	}
	
	/**
	 * reduce EPSG code (EPSG:#####)
	 * @param code input code
	 * @return reduced EPSG code
	 */
	private String getEPSGCodeShort(String code){
		String index;
		if(code.contains(":"))
			index = code.substring(code.lastIndexOf(":") + 1);
		else if(code.contains("/"))
			index = code.substring(code.lastIndexOf("/") + 1);
		else return null;
		return "EPSG:" + index;
	}

}
