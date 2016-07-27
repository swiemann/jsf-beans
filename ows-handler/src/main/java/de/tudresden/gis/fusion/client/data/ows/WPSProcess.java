package de.tudresden.gis.fusion.client.data.ows;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WPSProcess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * WPS parameters
	 */
	private String identifier;
	private String title;
	private String description;
	private Map<String,IODescription> inputs = new HashMap<String,IODescription>();
	private Map<String,IODescription> outputs = new HashMap<String,IODescription>();
	
	/**
	 * constructor
	 * @param identifier process identifer
	 * @param title process title
	 * @param description process description
	 * @param inputs process inputs
	 * @param outputs process outputs
	 */
	public WPSProcess(String identifier, String title, String description, Set<IODescription> inputIO, Set<IODescription> outputIO) {
		this.identifier = identifier;
		this.title = title;
		this.description = description;
		for(IODescription input : inputIO){
			inputs.put(input.getIdentifier(), input);
		}
		for(IODescription output : outputIO){
			inputs.put(output.getIdentifier(), output);
		}
	}
	
	/**
	 * get identifier
	 * @return WPS process identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	/**
	 * get title
	 * @return WPS process title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * get description
	 * @return WPS process description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * get inputs
	 * @return WPS process inputs
	 */
	public Collection<IODescription> getInputs() {
		return inputs.values();
	}
	
	/**
	 * get outputs
	 * @return WPS process outputs
	 */
	public Collection<IODescription> getOutputs() {
		return outputs.values();
	}

}
