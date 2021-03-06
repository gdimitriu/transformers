/*
 Copyright (c) 2017 Gabriel Dimitriu All rights reserved.
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

 This file is part of transformationengine project.

 transformationengine is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 transformationengine is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with transformationengine.  If not, see <http://www.gnu.org/licenses/>.
*/
package transformationsEngine.restCalls.test;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.internal.MultiPartWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import transformationsEngine.serverRest.ServerJetty;
import transformationsEngine.utils.StreamUtils;

public class RestCallsForDigesterTransformationsTest {
	
	private static final String DIGESTER_ONE_STEP = "digesterOneStep";

	private ServerJetty server = null;
	
	private final int port = 8099;

	private final URI baseUri = UriBuilder.fromUri("{arg}").build(new String[]{"http://localhost:"+ port + "/"},false);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		server = new ServerJetty();
		Thread thread = new Thread() {
			public void run() {
				try {
					server.startServer();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		thread.start();
		while(!server.isStarted()) {
			Thread.sleep(100);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		server.stopServer();
	}

	@Test
	public void xml2json2xmlStepsTest() {
		Client client = ClientBuilder.newClient()
				.register(MultiPartFeature.class)
				.register(MultiPartWriter.class);
		WebTarget target = client.target(baseUri);
		FormDataMultiPart multipartEntity = new FormDataMultiPart()
	     .field("data", getClass().getClassLoader().getResourceAsStream("xml2json2xml.xml"),
	    		MediaType.APPLICATION_XML_TYPE)
	     .field("configuration", StreamUtils.getStringFromResource("xml2json2xml.xml"),
	    		MediaType.APPLICATION_XML_TYPE);
		Response response = target.path("rest").path("transform").path(DIGESTER_ONE_STEP)
				.request(new String[]{MediaType.MULTIPART_FORM_DATA})
				.put(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
		if (response.getStatus() >= 0) {
			InputStream inputStream = response.readEntity(InputStream.class);
			assertEquals(StreamUtils.getStringFromResourceWithoutSpaces("xml2json2xml.xml"),
					StreamUtils.toStringFromStream(inputStream));
		}
	}
	
	@Test
	public void xml2json2xmlStepsWithConfigurationTest() {
		Client client = ClientBuilder.newClient().register(MultiPartFeature.class).register(MultiPartWriter.class);
		WebTarget target = client.target(baseUri);
		FormDataMultiPart multipartEntity = new FormDataMultiPart()
	     .field("data", getClass().getClassLoader().getResourceAsStream("xml2json2xml.xml"),
	    		MediaType.APPLICATION_XML_TYPE);
		Response response = target.path("rest").path("transform").path(DIGESTER_ONE_STEP)
				.queryParam("configuration", StreamUtils.getStringFromResource("xml2json2xmlwithconfigurations.xml"))
				.request(new String[]{MediaType.MULTIPART_FORM_DATA})
				.put(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
		if (response.getStatus() >= 0) {
			InputStream inputStream = response.readEntity(InputStream.class);
			assertEquals(StreamUtils.getStringFromResourceWithoutSpaces("xml2json2xmlwithconfigurationResult.xml"),
					StreamUtils.toStringFromStream(inputStream));
		}
	}
	
	@Test
	public void xml2xmlXsltOneStepTest() {
		Client client = ClientBuilder.newClient().register(MultiPartFeature.class).register(MultiPartWriter.class);
		WebTarget target = client.target(baseUri);
		FormDataMultiPart multipartEntity = new FormDataMultiPart()
	     .field("data", getClass().getClassLoader().getResourceAsStream("processingInput.xml"),
	    		MediaType.APPLICATION_XML_TYPE)
	     .field("configuration", StreamUtils.getStringFromResource("processingOneStepXsl.xml"),
	    		MediaType.APPLICATION_XML_TYPE)
	     .field("processingMap.xsl", getClass().getClassLoader().getResourceAsStream("processingMap.xsl"),
	    		MediaType.APPLICATION_XML_TYPE);
		Response response = target.path("rest").path("transform").path(DIGESTER_ONE_STEP)
				.request(new String[]{MediaType.MULTIPART_FORM_DATA})
				.put(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
		if (response.getStatus() >= 0) {
			InputStream inputStream = response.readEntity(InputStream.class);
			assertEquals(StreamUtils.getStringFromResourceWithoutSpaces("processingOutput.xml"),
					StreamUtils.toStringFromStream(inputStream));
		}
	}
	
	@Test
	public void xml2xml2xmlXsltOneStepTest() {
		Client client = ClientBuilder.newClient().register(MultiPartFeature.class).register(MultiPartWriter.class);
		WebTarget target = client.target(baseUri);
		FormDataMultiPart multipartEntity = new FormDataMultiPart()
	     .field("data", getClass().getClassLoader().getResourceAsStream("processingInput.xml"),
	    		MediaType.APPLICATION_XML_TYPE)
	     .field("configuration", StreamUtils.getStringFromResource("processingTwoStepsXsl.xml"),
	    		MediaType.APPLICATION_XML_TYPE)
	     .field("processingMap.xsl", getClass().getClassLoader().getResourceAsStream("processingMap.xsl"),
	    		MediaType.APPLICATION_XML_TYPE)
	     .field("processingMap1.xsl", getClass().getClassLoader().getResourceAsStream("processingMap1.xsl"),
	    		MediaType.APPLICATION_XML_TYPE);
		Response response = target.path("rest").path("transform").path(DIGESTER_ONE_STEP)
				.request(new String[]{MediaType.MULTIPART_FORM_DATA})
				.put(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
		if (response.getStatus() >= 0) {
			InputStream inputStream = response.readEntity(InputStream.class);
			assertEquals(StreamUtils.getStringFromResourceWithoutSpaces("processingInput.xml"),
					StreamUtils.toStringFromStream(inputStream));
		}
	}

	@Test
	public void xml2xmlXsltOneStepWParametersTest() {
		Client client = ClientBuilder.newClient().register(MultiPartFeature.class).register(MultiPartWriter.class);
		WebTarget target = client.target(baseUri);
		FormDataMultiPart multipartEntity = new FormDataMultiPart()
	     .field("data", getClass().getClassLoader().getResourceAsStream("processingInput.xml"),
	    		MediaType.APPLICATION_XML_TYPE)
	     .field("configuration", StreamUtils.getStringFromResource("processingOneStepXslParameters.xml"),
	    		MediaType.APPLICATION_XML_TYPE)
	     .field("processingMapParameters.xsl", getClass().getClassLoader().getResourceAsStream("processingMapParameters.xsl"),
	    		MediaType.APPLICATION_XML_TYPE);
		Response response = target.path("rest").path("transform").path(DIGESTER_ONE_STEP)
				.queryParam("param1", "buru")
				.queryParam("param2", "-1000")
				.request(new String[]{MediaType.MULTIPART_FORM_DATA})
				.put(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
		if (response.getStatus() >= 0) {
			InputStream inputStream = response.readEntity(InputStream.class);
			assertEquals(StreamUtils.getStringFromResourceWithoutSpaces("processingOutputParameters.xml"),
					StreamUtils.toStringFromStream(inputStream));
		}
	}

}
