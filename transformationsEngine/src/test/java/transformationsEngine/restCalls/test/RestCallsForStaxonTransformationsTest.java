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

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import transformationsEngine.serverRest.ServerJetty;
import transformationsEngine.utils.StreamUtils;

public class RestCallsForStaxonTransformationsTest {
	
	private static final String CONFIGURATION_AUTOPRIMITIVE = "<?xml version=\"1.0\"?><configuration><autoPrimitive>false</autoPrimitive><autoArray>false</autoArray></configuration>";

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
	public void xml2jsonStepTest() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(baseUri).register(MultiPartFeature.class);
		Response response = target.path("rest").path("transform").path("staxon")
				.queryParam("mode", "xml2json")
				.queryParam("configuration", CONFIGURATION_AUTOPRIMITIVE)
				.request(MediaType.APPLICATION_XML)
				.put(Entity.entity(getClass().getClassLoader().getResourceAsStream("xml2json2xml.xml"),
						MediaType.APPLICATION_XML));
		if (response.getStatus() >= 0) {
			InputStream inputStream = response.readEntity(InputStream.class);
			assertEquals(StreamUtils.getStringFromResourceWithoutSpaces("xml2json2xml.json"),
					StreamUtils.toStringFromStream(inputStream));
		}
	}
	
	@Test
	public void json2xmlStepTest() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(baseUri).register(MultiPartFeature.class);
		Response response = target.path("rest").path("transform").path("staxon")
				.queryParam("mode", "json2xml")
				.queryParam("configuration", CONFIGURATION_AUTOPRIMITIVE)
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(getClass().getClassLoader().getResourceAsStream("xml2json2xml.json"),
						MediaType.APPLICATION_JSON));
		if (response.getStatus() >= 0) {
			InputStream inputStream = response.readEntity(InputStream.class);
			assertEquals(StreamUtils.getStringFromResourceWithoutSpaces("xml2json2xml.xml"),
					StreamUtils.toStringFromStream(inputStream));
		}
	}

}
