package de.tudresden.gis.fusion.client.service.handler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.tudresden.gis.fusion.client.data.ows.WPSCapabilities;
import de.tudresden.gis.fusion.client.data.ows.WPSProcessCollection;
import de.tudresden.gis.fusion.client.service.parser.WPSCapabilitiesParser;
import de.tudresden.gis.fusion.client.service.parser.WPSDescriptionParser;

public class WPSHandler extends OWSHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final static String SERVICE = "WPS";
	
	public final static String REQUEST_DESCRIBEPROCESS = "DescribeProcess";
	
	private final String PARAM_IDENTIFIER = "identifier";

	@Override
	public String getService() {
		return SERVICE;
	}
	
	@Override
	public String getDefaultVersion() {
		return "1.0.0";
	}

	@Override
	public String[] getSupportedVersions() {
		return new String[]{"1.0.0"};
	}

	@Override
	public WPSCapabilities getCapabilities() throws IOException {
		try {
			WPSCapabilitiesParser parser = new WPSCapabilitiesParser(getCapabilitiesDocument());
			return parser.parseCapabilities();
		} catch (Exception e) {
			throw new IOException("Could not parse WPS capabilities", e);
		}
	}	
	
	/**
	 * parse process descriptions
	 * @param identifier service identifiers (multiple identifiers are separated by comma)
	 * @return process descriptions for selected identifiers
	 * @throws IOException
	 */
	public WPSProcessCollection getProcessDescriptions(String identifier) throws IOException {
		try {
			WPSDescriptionParser parser = new WPSDescriptionParser(getProcessDescriptionDocument(identifier));
			return parser.parseProcessDescription();
		} catch (Exception e) {
			throw new IOException("Could not parse WPS capabilities", e);
		}
	}
	
	/**
	 * retrieve process description document
	 * @param identifier process identifier (multiple identifiers are separated by comma)
	 * @return process description document
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document getProcessDescriptionDocument(String identifier) throws ParserConfigurationException, SAXException, IOException {
		return getDOM(getProcessDescriptionRequest(identifier));
	}
	
	/**
	 * get WPS process description request
	 * @param identifier process identifier (multiple identifiers are separated by comma)
	 * @return process description
	 * @throws IOException
	 */
	public String getProcessDescriptionRequest(String identifier) throws IOException {
		setRequest(REQUEST_DESCRIBEPROCESS);
		setParameter(PARAM_IDENTIFIER, identifier);
		return getKVPRequest(new String[]{PARAM_SERVICE,PARAM_REQUEST,PARAM_VERSION,PARAM_IDENTIFIER}, new String[]{});
	}

}
