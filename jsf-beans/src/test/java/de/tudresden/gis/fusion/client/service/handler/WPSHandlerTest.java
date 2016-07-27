package de.tudresden.gis.fusion.client.service.handler;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import de.tudresden.gis.fusion.client.data.ows.WPSCapabilities;
import de.tudresden.gis.fusion.client.data.ows.WPSProcess;
import de.tudresden.gis.fusion.client.data.ows.WPSProcessCollection;

public class WPSHandlerTest {

	@Test
	public void readWPSCapabilities() throws IOException {
		
		WPSHandler handler = new WPSHandler();
		handler.setBaseURL("http://cobweb.gis.geo.tu-dresden.de:8080/wps_conflation/WebProcessingService");
		handler.setVersion("1.0.0");
		handler.setRequest(WPSHandler.REQUEST_GETCAPABILITIES);
		WPSCapabilities capabilities = handler.getCapabilities();
		
		Assert.assertNotNull(capabilities);
		Assert.assertNotNull(capabilities.getIdentifiers());
		
		Assert.assertTrue(capabilities.getIdentifiers().contains("de.tudresden.gis.fusion.algorithm.COBWEB_ZonalStats"));
		
	}
	
	@Test
	public void readWPSProcessDescription() throws IOException {
		
		WPSHandler handler = new WPSHandler();
		handler.setBaseURL("http://cobweb.gis.geo.tu-dresden.de:8080/wps_conflation/WebProcessingService");
		handler.setVersion("1.0.0");
		handler.setRequest(WPSHandler.REQUEST_DESCRIBEPROCESS);
		WPSProcessCollection processDescriptions = handler.getProcessDescriptions("de.tudresden.gis.fusion.algorithm.COBWEB_ZonalStats");
		
		Assert.assertNotNull(processDescriptions);
		Assert.assertTrue(processDescriptions.getProcesses().size() == 1);
		
		WPSProcess process = processDescriptions.getProcesses().iterator().next();
		
		Assert.assertTrue(process.getIdentifier().equals("de.tudresden.gis.fusion.algorithm.COBWEB_ZonalStats"));
		
	}
	
	@Test
	public void readWPSProcessDescriptions() throws IOException {
		
		WPSHandler handler = new WPSHandler();
		handler.setBaseURL("http://cobweb.gis.geo.tu-dresden.de:8080/wps_conflation/WebProcessingService");
		handler.setVersion("1.0.0");
		handler.setRequest(WPSHandler.REQUEST_DESCRIBEPROCESS);
		WPSProcessCollection processDescriptions = handler.getProcessDescriptions(""
				+ "de.tudresden.gis.fusion.algorithm.COBWEB_ZonalStats,"
				+ "de.tudresden.gis.fusion.algorithm.COBWEB_DamerauLevenshteinDistance,"
				+ "de.tudresden.gis.fusion.algorithm.BoundingBoxDistance");
		
		Assert.assertNotNull(processDescriptions);
		Assert.assertTrue(processDescriptions.getProcesses().size() == 3);
		
	}
	
}
