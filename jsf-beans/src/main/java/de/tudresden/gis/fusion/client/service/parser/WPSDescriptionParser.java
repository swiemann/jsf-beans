package de.tudresden.gis.fusion.client.service.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import de.tudresden.gis.fusion.client.data.ows.IODescription;
import de.tudresden.gis.fusion.client.data.ows.IOFormat;
import de.tudresden.gis.fusion.client.data.ows.IOFormatCollection;
import de.tudresden.gis.fusion.client.data.ows.WPSProcess;
import de.tudresden.gis.fusion.client.data.ows.WPSProcessCollection;

public class WPSDescriptionParser extends XMLParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * XML identifiers
	 */
	private final String PROCESS_DESCRIPTION = ".*(?i)ProcessDescription$";
	private final String PROCESS_IDENTIFIER = ".*(?i)Identifier";
	private final String PROCESS_TITLE = ".*(?i)Title";
	private final String PROCESS_ABSTRACT = ".*(?i)Abstract";
	private final String PROCESS_INPUTS = ".*(?i)DataInputs";
	private final String PROCESS_OUTPUTS = ".*(?i)ProcessOutputs";
	private final String PROCESS_INPUT = ".*(?i)Input";
	private final String PROCESS_OUTPUT = ".*(?i)Output";
	
	private final String IO_IDENTIFIER = ".*(?i)Identifier";
	private final String IO_TITLE = ".*(?i)Title";
	private final String IO_COMPLEX = ".*(?i)ComplexData";
	private final String IO_DEFAULT = ".*(?i)Default";
	private final String IO_SUPPORTED = ".*(?i)Supported";
	private final String IO_FORMAT = ".*(?i)Format";
	private final String IO_MIMETYPE = ".*(?i)MimeType";		
	private final String IO_SCHEMA = ".*(?i)Schema";
	private final String IO_LITERAL = ".*(?i)LiteralData|.*(?i)LiteralOutput";
	private final String IO_LITERAL_TYPE = ".*(?i)DataType";
	private final String IO_LITERAL_REF = ".*(?i)reference";
	private final String IO_BBOX = ".*(?i)BoundingBoxData|.*(?i)BoundingBoxOutput";
	private final String IO_BBOX_DEFAULT = ".*(?i)Default";
	private final String IO_BBOX_SUPPORTED = ".*(?i)Supported";
	private final String IO_BBOX_CRS = ".*(?i)CRS";
	
	/**
	 * constructor
	 * @param document input document
	 */
	public WPSDescriptionParser(Document document){
		super(document);
	}
	
	/**
	 * parse WPS process description
	 * @return WPS process collection
	 */
	public WPSProcessCollection parseProcessDescription() {
		Set<WPSProcess> wpsProcesses = new HashSet<WPSProcess>();
		//get all process description nodes
		List<Node> matches = getNodes(PROCESS_DESCRIPTION);
		//parse single processes
		for(Node description : matches) {
			wpsProcesses.add(parseProcess(description));
		}
		return new WPSProcessCollection(wpsProcesses);
	}

	/**
	 * parse single process description from node
	 * @param description process description
	 * @return WPS process object
	 */
	private WPSProcess parseProcess(Node descriptionNode) {
		//init temp variables
		String identifier = null, title = null, description = null;
		Set<IODescription> inputs = new HashSet<IODescription>();
		Set<IODescription> outputs = new HashSet<IODescription>();
		//get child nodes
		NodeList layerNodes = descriptionNode.getChildNodes();
		//iterate
		for (int i=0; i<layerNodes.getLength(); i++) {
			//get element node
			Node element = layerNodes.item(i);
			//check for identifier
			if(element.getNodeName().matches(PROCESS_IDENTIFIER)){
				identifier = element.getTextContent().trim();
			}
			//check for title
			if(element.getNodeName().matches(PROCESS_TITLE)){
				title = element.getTextContent().trim();
			}
			//check for description
			if(element.getNodeName().matches(PROCESS_ABSTRACT)){
				description = element.getTextContent().trim();
			}
			//parse inputs
			if(element.getNodeName().matches(PROCESS_INPUTS)){
				NodeList inputNodes = element.getChildNodes();
				for (int j=0; j<inputNodes.getLength(); j++) {
					Node inputElement = inputNodes.item(j);
					if(inputElement.getNodeName().matches(PROCESS_INPUT)){
						inputs.add(parseIODescription(inputElement));
					}
				}	
			}
			//parse outputs
			if(element.getNodeName().matches(PROCESS_OUTPUTS)){
				NodeList outputNodes = element.getChildNodes();
				for (int j=0; j<outputNodes.getLength(); j++) {
					Node outputElement = outputNodes.item(j);
					if(outputElement.getNodeName().matches(PROCESS_OUTPUT)){
						outputs.add(parseIODescription(outputElement));
					}
				}
			}
		}
		//create WPS process description
		return new WPSProcess(identifier, title, description, inputs, outputs);
	}

	private IODescription parseIODescription(Node ioNode) {
		//create temp variables
		String identifier = null, title= null; 
		IOFormatCollection formats = new IOFormatCollection();
		//get child nodes
		NodeList ioNodes = ioNode.getChildNodes();
		//iterate
		for (int i=0; i<ioNodes.getLength(); i++) {
			//get element node
			Node element = ioNodes.item(i);
			//check for identifier
			if(element.getNodeName().matches(IO_IDENTIFIER)){
				identifier = element.getTextContent().trim();
			}
			//check for title
			if(element.getNodeName().matches(IO_TITLE)){
				title = element.getTextContent().trim();
			}
			//check for complex format
			if(element.getNodeName().matches(IO_COMPLEX)){
				 parseComplexIO(element, formats);				
			}
			//check for literal format
			if(element.getNodeName().matches(IO_LITERAL)){
				parseLiteralIO(element, formats);				
			}
			//check for bbox format
			if(element.getNodeName().matches(IO_BBOX)){
				parseBBoxIO(element, formats);				
			}
		}
		//create IO description
		return new IODescription(identifier, title, formats);
	}
		
	/**
	 * parse complex IO
	 * @param element IO element
	 * @return format collection
	 */
	private void parseComplexIO(Node formatNode, IOFormatCollection formats) {
		//get child nodes
		NodeList formatNodes = formatNode.getChildNodes();
		//check for default
		Node defaultNode = getNode(IO_DEFAULT, formatNodes);
		if(defaultNode != null)
			formats.addFormat(parseComplexFormat(defaultNode), true);
		//check for supported
		Node supportedNode = getNode(IO_SUPPORTED, formatNodes);
		//get all supported formats
		List<Node> supportedNodes = getNodes(IO_FORMAT, supportedNode.getChildNodes(), null);
		for(Node node : supportedNodes){
			formats.addFormat(parseComplexFormat(node), false);
		}
	}

	/**
	 * parse format node
	 * @param formatNode
	 * @return IO format object
	 */
	private IOFormat parseComplexFormat(Node formatNode) {
		//create temp variables 
		String mimetype = null, schema = null;
		//get child nodes
		NodeList nodes = formatNode.getChildNodes();
		for (int i=0; i<nodes.getLength(); i++) {
			//get element node
			Node element = nodes.item(i);
			//check for mimetype
			if(element.getNodeName().matches(IO_MIMETYPE)){
				mimetype = element.getTextContent().trim();
			}
			//check for schema
			if(element.getNodeName().matches(IO_SCHEMA)){
				schema = element.getTextContent().trim();
			}
		}
		//create IOFormat
		return new IOFormat(mimetype, schema, null);
	}

	private void parseLiteralIO(Node formatNode, IOFormatCollection formats) {
		//create temp variables 
		String type = null;
		//get child nodes
		NodeList nodes = formatNode.getChildNodes();
		outerloop:
		for (int i=0; i<nodes.getLength(); i++) {
			//get element node
			Node element = nodes.item(i);
			//check for data type
			if(element.getNodeName().matches(IO_LITERAL_TYPE)){
				NamedNodeMap atts = element.getAttributes();
				for (int j=0; j<atts.getLength(); j++) {
					Node attribute = atts.item(j);
					if(attribute.getNodeName().matches(IO_LITERAL_REF)){
						type = attribute.getNodeValue().trim();
						break outerloop;
					}
				}
			}
		}
		//add IOFormat
		formats.addFormat(new IOFormat(null, null, type), true);
	}

	private void parseBBoxIO(Node formatNode, IOFormatCollection formats) {
		//create temp variables 
		String type = null;
		//get child nodes
		NodeList nodes = formatNode.getChildNodes();
		outerloop:
		for (int i=0; i<nodes.getLength(); i++) {
			//get element node
			Node element = nodes.item(i);
			//check for data type
			if(element.getNodeName().matches(IO_BBOX_DEFAULT) || element.getNodeName().matches(IO_BBOX_SUPPORTED)){
				NodeList bboxNodes = element.getChildNodes();
				for (int j=0; j<bboxNodes.getLength(); j++) {
					Node bboxElement = bboxNodes.item(j);
					if(bboxElement.getNodeName().matches(IO_BBOX_CRS)){
						type = bboxElement.getTextContent().trim();
						break outerloop;
					}
				}
			}
		}
		//add IOFormat
		formats.addFormat(new IOFormat(null, null, type), true);
	}

}
