package de.tudresden.gis.fusion.client.data.ows;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WFSCapabilities extends OWSCapabilities {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * service type
	 */
	public final static String SERVICE = "WFS";
	
	/**
	 * WFS layer map
	 */
	private Map<String,WFSLayer> layers;
	
	/**
	 * constructor
	 * @param version WFS version
	 * @param layers WFS layers
	 */
	public WFSCapabilities(String version, Set<WFSLayer> layerSet) {
		super(SERVICE, version);
		layers = new HashMap<String,WFSLayer>();
		for(WFSLayer layer : layerSet){
			layers.put(layer.getIdentifier(), layer);
		}
	}
	
	/**
	 * get all WFS layer as Map<identifier,layer>
	 * @return WFS layer
	 */
	public Set<String> getIdentifiers() {
		return layers.keySet();
	}
	
	/**
	 * get WFS layer by identifier
	 * @param identifier layer identifier
	 * @return WFS layer associated with the identifier
	 */
	public WFSLayer getLayer(String identifier) {
		return layers.get(identifier);
	}

}
