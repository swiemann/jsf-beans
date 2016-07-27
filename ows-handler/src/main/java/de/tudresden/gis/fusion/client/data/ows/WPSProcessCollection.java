package de.tudresden.gis.fusion.client.data.ows;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WPSProcessCollection implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * process map
	 */
	private Map<String,WPSProcess> processes;
	
	/**
	 * constructor
	 * @param processes set of processes
	 */
	public WPSProcessCollection(Set<WPSProcess> wpsProcesses){
		processes = new HashMap<String,WPSProcess>();
		for(WPSProcess process : wpsProcesses){
			addProcess(process);
		}
	}
	
	/**
	 * get processes
	 * @return processes in collection
	 */
	public Collection<WPSProcess> getProcesses(){
		return processes.values();
	}
	
	/**
	 * add process to collection
	 * @param process input process to be added
	 */
	protected void addProcess(WPSProcess process){
		processes.put(process.getIdentifier(), process);
	}

}
