package de.tudresden.gis.fusion.client.service.handler;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import de.tudresden.gis.fusion.client.data.ows.WFSCapabilities;

public class WFSHandlerTest {

	@Test
	public void readWPSCapabilities() throws IOException {
		
		WFSHandler handler = new WFSHandler();
		handler.setBaseURL("https://dyfi.cobwebproject.eu/pcapi/ows");
		handler.setVersion("1.1.0");
		handler.setRequest(WFSHandler.REQUEST_GETCAPABILITIES);
		WFSCapabilities capabilities = handler.getCapabilities();
		
		Assert.assertNotNull(capabilities);
		Assert.assertNotNull(capabilities.getIdentifiers());
		
		Assert.assertTrue(capabilities.getIdentifiers().contains("cobweb:HT_Protokoll"));
		
	}
	
}
