package de.tudresden.gis.fusion.client.service.handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class KVPHandler extends ServiceHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * KVP parameter map
	 */
	private Map<String,String> kvp = new HashMap<String,String>();
	
	/**
	 * get service request parameter
	 * @param key parameter key
	 * @return parameter value for key
	 */
	public String getParameter(String key) { 
		return kvp.get(key);
	}
	
	/**
	 * set service request parameter
	 * @param key parameter key
	 * @param value parameter value
	 */
	public void setParameter(String key, String value) { 
		kvp.put(key, value);
	}
	
	/**
	 * clear request parameter list
	 */
	public void clearParameterList() {
		kvp.clear(); 
	}
	
	/**
	 * get KVP request URL
	 * @param mandatoryKeys mandatory keys for the request
	 * @param optionalKeys optional keys for the request
	 * @throws IOException if base URL or a mandatory key is not set
	 * @return
	 */
	public String getKVPRequest(String[] mandatoryKeys, String[] optionalKeys) throws IOException {
		//get url
		StringBuilder sBuilder = new StringBuilder().append(getBaseURL() + "?");
		//add mandatory keys
		if(mandatoryKeys != null){
			for(String key : mandatoryKeys) {
				if(this.getParameter(key) == null || this.getParameter(key).length() == 0)
					throw new IOException("KVP parameter " + key + " must not be null");
				sBuilder.append(getKVPParameter(key, this.getParameter(key)) + "&");
			}
		}
		//add optional keys
		if(optionalKeys != null){
			for(String key : optionalKeys) {
				if(this.getParameter(key) != null && this.getParameter(key).length() != 0)
					sBuilder.append(getKVPParameter(key, this.getParameter(key)) + "&");
			}
		}
		//return request string
		return sBuilder.substring(0, sBuilder.length()-1);	
	}

	/**
	 * get kvp parameter string
	 * @param key parameter key
	 * @param value parameter value
	 * @return kvp parameter string
	 */
	private String getKVPParameter(String key, String value){
		return key + "=" + value;
	}
	
	/**
	 * connect to service
	 * @return service input stream
	 * @throws IOException if connection cannot be established
	 */
	protected InputStream connect(String sURL) throws IOException {
		URL url = new URL(sURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
			throw new IOException("URL is not valid or not accessible");
		//get input stream
		return connection.getInputStream();
	}
	
}
