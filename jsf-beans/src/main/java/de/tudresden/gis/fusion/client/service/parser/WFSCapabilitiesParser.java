package de.tudresden.gis.fusion.client.service.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.tudresden.gis.fusion.client.data.ows.WFSCapabilities;
import de.tudresden.gis.fusion.client.data.ows.WFSLayer;

public class WFSCapabilitiesParser extends XMLParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * XML identifiers
	 */
	private final String WFS_LAYER = ".*(?i)FeatureType";
	private final String WFS_VERSION = ".*(?i)ServiceTypeVersion";
	private final String LAYER_NAME = ".*(?i)Name";
	private final String LAYER_CRS = ".*(?i)defaultCRS|defaultSRS|supportedCRS|supportedSRS";
	private final String LAYER_BBOX = ".*(?i)WGS84BoundingBox";
	private final String LAYER_BBOX_LC = ".*(?i)LowerCorner";
	private final String LAYER_BBOX_UC = ".*(?i)UpperCorner";

	/**
	 * constructor
	 * @param document capabilities document
	 */
	public WFSCapabilitiesParser(Document document) {
		super(document);
	}
	
	/**
	 * parse WFS capabilities document
	 * @return capabilities object
	 */
	public WFSCapabilities parseCapabilities() {	
		//init layer map
		Set<WFSLayer> layers = new HashSet<WFSLayer>();
		//get version
		String version = identifyVersion(getNode(WFS_VERSION));
		//get all feature types (layers)
		List<Node> matches = getNodes(WFS_LAYER);
		for(Node node : matches) {
			WFSLayer layer = parseLayer(node);
			if(layer.getIdentifier() != null)
				layers.add(layer);
		}
		//create capabilities
		return new WFSCapabilities(version, layers);
	}
		
	/**
	 * identify WFS version
	 * @param node capabilities fragment with version
	 * @return WFS version
	 */
	private String identifyVersion(Node node) {
		return node.getTextContent().trim();
	}

	/**
	 * parse WFS layer information
	 * @param node WFS layer node
	 * @return WFS layer object
	 */
	private WFSLayer parseLayer(Node node) {
		//init tmp variables
		String identifier = null, lowerCorner = null, upperCorner = null;
		Set<String> supportedSRS = new HashSet<String>();
		//get child nodes
		NodeList layerNodes = node.getChildNodes();
		//iterate
		for (int i=0; i<layerNodes.getLength(); i++) {
			//get element node
			Node element = layerNodes.item(i);
			//check for name
			if(element.getNodeName().matches(LAYER_NAME)){
				identifier = element.getTextContent().trim();
			}
			//check for crs srs
			if(element.getNodeName().matches(LAYER_CRS)) {
				supportedSRS.add(element.getTextContent().trim());
			}
			//check for bbox
			if(element.getNodeName().matches(LAYER_BBOX)) {
				NodeList bboxElements = element.getChildNodes();
				for (int j=0; j<bboxElements.getLength(); j++) {
					Node bboxElement = bboxElements.item(j);
					if(bboxElement.getNodeName().matches(LAYER_BBOX_LC))
						lowerCorner = bboxElement.getTextContent().trim();
					else if(bboxElement.getNodeName().matches(LAYER_BBOX_UC))
						upperCorner = bboxElement.getTextContent().trim();
				}					
			}
		}
		//create and return layer
		return new WFSLayer(identifier, supportedSRS, lowerCorner, upperCorner);
	}

}
