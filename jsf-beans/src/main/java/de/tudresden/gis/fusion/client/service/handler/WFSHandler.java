package de.tudresden.gis.fusion.client.service.handler;

import java.io.IOException;

import de.tudresden.gis.fusion.client.data.ows.WFSCapabilities;
import de.tudresden.gis.fusion.client.service.parser.WFSCapabilitiesParser;

public class WFSHandler extends OWSHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String SERVICE = "WFS";
	
	private final static String REQUEST_DESCRIBEFEATURETYPE = "DescribeFeatureType";
	private final static String REQUEST_GETFEATURE = "GetFeature";
	
	private final String PARAM_TYPENAME = "typename";
	private final String PARAM_OUTPUTFORMAT = "outputformat";
	private final String PARAM_SRSNAME = "srsname";
	private final String PARAM_BBOX = "bbox";
	
	@Override
	public String getService() {
		return SERVICE;
	}
	
	@Override
	public String getDefaultVersion(){
		return "1.1.0";
	}
	
	@Override
	public String[] getSupportedVersions() { 
		return new String[]{"1.0.0","1.1.0","2.0.0","2.0.2"};
	}

	@Override
	public WFSCapabilities getCapabilities() throws IOException {
		try {
			WFSCapabilitiesParser parser = new WFSCapabilitiesParser(getCapabilitiesDocument());
			return parser.parseCapabilities();
		} catch (Exception e) {
			throw new IOException("Could not parse WFS capabilities", e);
		}
	}

}
