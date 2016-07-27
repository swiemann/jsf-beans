package de.tudresden.gis.fusion.client.service.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.tudresden.gis.fusion.client.data.ows.OWSCapabilities;

public abstract class OWSHandler extends KVPHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String REQUEST_GETCAPABILITIES = "GetCapabilities";	
	
	public final String PARAM_SERVICE = "service";
	public final String PARAM_VERSION = "version";
	public final String PARAM_REQUEST = "request";
	
	/**
	 * empty constructor
	 */
	public OWSHandler() {
		setService(getService());
		setVersion(getDefaultVersion());
	}

	
	/**
	 * set service parameter
	 * @param value service parameter
	 */
	private void setService(String value) { 
		setParameter(PARAM_SERVICE, value); 
	}
	
	/**
	 * get request parameter
	 * @return request parameter
	 */
	public String getRequest() { 
		return getParameter(PARAM_REQUEST); 
	}
	
	/**
	 * set request parameter
	 * @param value request parameter
	 */
	public void setRequest(String value) { 
		setParameter(PARAM_REQUEST, value); 
	}
	
	/**
	 * get version parameter
	 * @return version parameter
	 */
	public String getVersion() { 
		return getParameter(PARAM_VERSION); 
	}
	
	/**
	 * set version parameter
	 * @param value version parameter
	 */
	public void setVersion(String value) {
		if(!Arrays.asList(getSupportedVersions()).contains(value))
			throw new IllegalArgumentException("Version " + value + " is not supported");
		setParameter(PARAM_VERSION, value);
	}
	
	/**
	 * get capabilities request
	 * @return capabilities request
	 * @throws IOException
	 */
	public String getCapabilitiesRequest() throws IOException {
		setRequest(REQUEST_GETCAPABILITIES);
		return getKVPRequest(new String[]{PARAM_SERVICE,PARAM_REQUEST}, new String[]{PARAM_VERSION});
	}
	
	/**
	 * get capabilities document
	 * @return capabilities document
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document getCapabilitiesDocument() throws ParserConfigurationException, SAXException, IOException {
		return getDOM(getCapabilitiesRequest());
	}
	
	/**
	 * get XML document from input stream
	 * @param is XML input stream
	 * @return XML document
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	protected Document getDOM(String sURL) throws ParserConfigurationException, SAXException, IOException {
		//get input stream
		InputStream is = connect(sURL);
		try {	
			//parse document
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document document = dBuilder.parse(is);
	        //normalize and return document
	        document.getDocumentElement().normalize();
	        return document;
		} finally {
			is.close();
		}
	}
	
	/**
	 * get service parameter
	 * @return service parameter
	 */
	public abstract String getService();
	
	/**
	 * get default version for OWS
	 * @return default version (must also be contained in supported versions)
	 */
	public abstract String getDefaultVersion();
	
	/**
	 * get supported version for OWS
	 * @return vector of supported versions
	 */
	public abstract String[] getSupportedVersions();
	
	/**
	 * get OWS capabilities
	 * @return OWS capabilities
	 * @throws IOException
	 */
	public abstract OWSCapabilities getCapabilities() throws IOException ;

}
