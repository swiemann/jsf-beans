package de.tudresden.gis.fusion.client.service.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * XML parent node
	 */
	private Node root;
	
	/**
	 * constructor
	 * @param document input document
	 */
	public XMLParser(Node root){
		this.root = root;
	}
	
	/**
	 * get root node
	 * @return root node
	 */
	protected Node getRoot() {
		return root;
	}
	
	/**
	 * get first element from root with provided tag name
	 * @param regex regular expression for tag name
	 * @return first node matching the regex
	 */
	public Node getNode(String regex) {
		return getNode(regex, root.getChildNodes());
	}
	
	/**
	 * get first node with specified tag name
	 * @param regex tag name as regex
	 * @param nodes input node list
	 * @return first node matching the regex
	 */
	public Node getNode(String regex, NodeList nodes){
		int i = 0;
		while(nodes.item(i) != null) {
			Node node = nodes.item(i++);
			if(node.getNodeName().matches(regex))
				return node;
			else if(node.hasChildNodes()){
				Node tmpNode = this.getNode(regex, node.getChildNodes());
				if(tmpNode != null)
					return tmpNode;
			}
		}
		return null;
	}
	
	/**
	 * get all elements from root matching the provided tag name
	 * @param regex regular expression for tag name
	 * @return node list matching the regex
	 */
	public List<Node> getNodes(String regex){
		return getNodes(regex, root.getChildNodes(), null);
	}
	
	/**
	 * get nodes with specified tag name
	 * @param regex tag name as regex
	 * @param nodes input node list
	 * @param matches list with matches, will be initiated if null
	 * @return list of nodes matching the regex
	 */
	public List<Node> getNodes(String regex, NodeList nodes, List<Node> matches){
		if(matches == null)
			matches = new ArrayList<Node>();
		int i = 0;
		while(nodes.item(i) != null) {
			Node node = nodes.item(i++);
			if(node.getNodeName().matches(regex))
				matches.add(node);
			else if(node.hasChildNodes()){
				getNodes(regex, node.getChildNodes(), matches);
			}
		}
		return matches;
	}

}
