package de.tudresden.gis.fusion.client.service.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.tudresden.gis.fusion.client.data.ows.WPSCapabilities;

public class WPSCapabilitiesParser extends XMLParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * XML identifiers
	 */
	private final String WPS_VERSION = ".*(?i)ServiceTypeVersion";
	private final String WPS_PROCESS = ".*(?i)Process$";
	private final String PROCESS_IDENTIFIER = ".*(?i)Identifier";

	/**
	 * constructor
	 * @param document capabilities document
	 */
	public WPSCapabilitiesParser(Document document) {
		super(document);
	}
	
	/**
	 * parse WPS capabilities document
	 * @return capabilities object
	 */
	public WPSCapabilities parseCapabilities() {
		//init list
		Set<String> identifiers = new HashSet<String>();
		//get version
		String version = identifyVersion(getNode(WPS_VERSION));
		//get processes
		List<Node> matches = this.getNodes(WPS_PROCESS);
		for(Node processNode : matches) {
			NodeList processNodes = processNode.getChildNodes();
			//search for identifier
			String identifier = null;
			for (int i=0; i<processNodes.getLength(); i++) {
				Node element = processNodes.item(i);
				if(element.getNodeName().matches(PROCESS_IDENTIFIER)){
					identifier = element.getTextContent().trim();
					if(identifier != null && !identifier.isEmpty())
						identifiers.add(identifier);
				}
			}
		}
		//create capabilities document
		return new WPSCapabilities(version, identifiers);
	}
	
	/**
	 * identify WPS version
	 * @param node capabilities fragment with version
	 * @return WPS version
	 */
	private String identifyVersion(Node node) {
		return node.getTextContent().trim();
	}

}
