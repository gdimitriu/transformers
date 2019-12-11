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

import transformationsEngine.utils.StreamUtils;
/**
 * This is test class for REST server with digeser.
 */
public class ProcessingRestTestManual {

	private int port = 8099;

	private URI baseUri = UriBuilder.fromUri("{arg}").build(new String[]{"http://localhost:"+ port + "/"},false);

	/**
	 * 
	 */
	public ProcessingRestTestManual() {

	}
	
	/** send the test message to the REST with digester server */
	public void sendDigester() {
		System.out.println("digester IO");
		Client client = ClientBuilder.newClient()
				.register(MultiPartFeature.class)
				.register(MultiPartWriter.class);
		WebTarget target = client.target(baseUri);
		@SuppressWarnings("resource")
		FormDataMultiPart multipartEntity = new FormDataMultiPart()
	    .field("data",getClass().getClassLoader().getResourceAsStream("xml2json2xml.xml"),
	    		MediaType.APPLICATION_XML_TYPE)
	    .field("configuration", StreamUtils.getStringFromResource("xml2json2xml.xml"),
	    		MediaType.APPLICATION_XML_TYPE);
		Response response = target.path("rest").path("transform").path("flow")
				.request(new String[]{MediaType.MULTIPART_FORM_DATA})
				.put(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
		if (response.getStatus() >= 0) {
			System.out.println(StreamUtils.toStringFromStream(response.readEntity(InputStream.class)));
		}
	}
	
	public void sendOnlyRequest() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(baseUri).register(MultiPartFeature.class);
		Response response = target.path("rest").path("transform").path("staxon")
				.queryParam("mode", "xml2json")
				.queryParam("configuration", "<?xml version=\"1.0\"?><configuration><array>true</array><autoPrimitive>false</autoPrimitive></configuration>")
				.request(MediaType.APPLICATION_XML)
				.put(Entity.entity(getClass().getClassLoader().getResourceAsStream("xml2json2xml.xml"),
						MediaType.APPLICATION_XML));
		if (response.getStatus() >= 0) {
			System.out.println(StreamUtils.toStringFromStream(response.readEntity(InputStream.class)));
		}
	}

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
		Response response = target.path("rest").path("transform").path("digesterOneStep")
				.request(new String[]{MediaType.MULTIPART_FORM_DATA})
				.put(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
		if (response.getStatus() >= 0) {
			System.out.println(StreamUtils.toStringFromStream(response.readEntity(InputStream.class)));
		}
	}
	
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
		Response response = target.path("rest").path("transform").path("digesterOneStep")
				.queryParam("param1", "buru")
				.queryParam("param2", "-1000")
				.request(new String[]{MediaType.MULTIPART_FORM_DATA})
				.put(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
		if (response.getStatus() >= 0) {
			System.out.println(StreamUtils.toStringFromStream(response.readEntity(InputStream.class)));
		}
	}
	
	public void xml2json2xmlStepsFlowWithConfigurationTest() {
		Client client = ClientBuilder.newClient().register(MultiPartFeature.class).register(MultiPartWriter.class);
		WebTarget target = client.target(baseUri);
		FormDataMultiPart multipartEntity = new FormDataMultiPart()
	     .field("data", getClass().getClassLoader().getResourceAsStream("xml2json2xml.xml"),
	    		MediaType.APPLICATION_XML_TYPE);
		Response response = target.path("rest").path("transform").path("flow")
				.queryParam("configuration", StreamUtils.getStringFromResource("xml2json2xmlwithconfigurations.xml"))
				.request(new String[]{MediaType.MULTIPART_FORM_DATA})
				.put(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
		if (response.getStatus() >= 0) {
			System.out.println(StreamUtils.toStringFromStream(response.readEntity(InputStream.class)));
		}
	}
	
	public void xml2xml2xmlXsltFlowOneStepTest() {
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
		Response response = target.path("rest").path("transform").path("flow")
				.request(new String[]{MediaType.MULTIPART_FORM_DATA})
				.put(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
		if (response.getStatus() >= 0) {
			System.out.println(StreamUtils.toStringFromStream(response.readEntity(InputStream.class)));
		}
	}
	
	public void xml2json2xmlFlowStepsTest() {
		Client client = ClientBuilder.newClient()
				.register(MultiPartFeature.class)
				.register(MultiPartWriter.class);
		WebTarget target = client.target(baseUri);
		FormDataMultiPart multipartEntity = new FormDataMultiPart()
	     .field("data", getClass().getClassLoader().getResourceAsStream("xml2json2xml.xml"),
	    		MediaType.APPLICATION_XML_TYPE)
	     .field("configuration", StreamUtils.getStringFromResource("xml2json2xml.xml"),
	    		MediaType.APPLICATION_XML_TYPE);
		Response response = target.path("rest").path("transform").path("flow")
				.request(new String[]{MediaType.MULTIPART_FORM_DATA})
				.put(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
		if (response.getStatus() >= 0) {
			System.out.println(StreamUtils.toStringFromStream(response.readEntity(InputStream.class)));
		}
	}
	
	public static void main(String[] args) {
		ProcessingRestTestManual test = new ProcessingRestTestManual();
		test.xml2json2xmlFlowStepsTest();
	}

}
