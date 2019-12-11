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
package transformationsEngine.serverRest.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.digester3.Digester;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.xml.sax.SAXException;

import transformationsEngine.digester.StepsFactory;
import transformationsEngine.digester.steps.MapOfStepsParametersFactory;
import transformationsEngine.digester.steps.transformers.Json2XmlStep;
import transformationsEngine.digester.steps.transformers.StaxonStep;
import transformationsEngine.digester.steps.transformers.Xml2JsonStep;
import transformationsEngine.flow.runners.FlowRunner;
import transformationsEngine.serverRest.wrappers.RestStreamingOutput;
import transformationsEngine.wrappers.ByteArrayInputStreamWrapper;
import transformationsEngine.wrappers.ByteArrayOutputStreamWrapper;
import transformationsEngine.wrappers.StreamHolder;
import transformationsEngine.wrappers.WrapperUtils;

/**
 * This hold the transformation entry point (the resource for the jersey REST server).
 */
@Path("rest/transform")
public class TransformResources {

	@Context
	UriInfo uriInfo;
	
	public TransformResources() {
	}
	
	/**
	 * Transform a stream into an output stream using flow definition.
	 * @param multipart multipart input which contains xsl, data and configuration
	 * @param uriInfo contains query params for xsl or configuration
	 * @return http response
	 * @throws Exception 
	 */
	@Path("flow")
	@PUT
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response processDataStreamFlow(final FormDataMultiPart multipart,
			@Context UriInfo uriInfo) throws Exception {
		InputStream inputValue = multipart.getField("data").getEntityAs(InputStream.class);
		InputStream configurationStream = null;
		String configuration = null;
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters(); 
		if (queryParams != null) {
			configuration = queryParams.getFirst("configuration");
		}
		if (configuration == null || "".equals(configuration)) {
			configurationStream = multipart.getField("configuration").getEntityAs(InputStream.class);
		} else {
			configurationStream = new ByteArrayInputStream(configuration.getBytes());
		}
		ByteArrayOutputStreamWrapper bos = WrapperUtils.fromInputStreamToOutputWrapper(inputValue);
		
		StreamHolder holder = new StreamHolder(new ByteArrayInputStreamWrapper(bos.getBuffer(), 0, bos.size()));
		try {
			bos.close();
		} catch (IOException e) {
		}
		
		bos = null;
		
		FlowRunner runner = new FlowRunner(configurationStream, multipart, queryParams);
		runner.createSteps();
		runner.executeSteps(holder);
		
		ByteArrayInputStreamWrapper inputStream = holder.getInputStream();
		RestStreamingOutput stream = new RestStreamingOutput(inputStream.getBuffer(), 0, inputStream.size());
		return Response.ok().entity(stream).build();
	}

	/**
	 * Transform a stream into an output stream using digester in one step.
	 * @param multipart multipart input which contains xsl, data and configuration
	 * @param uriInfo contains query params for xsl or configuration
	 * @return http response
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Path("digesterOneStep")
	@PUT
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response processDataStreamDigesterOneStep(final FormDataMultiPart multipart,
			@Context UriInfo uriInfo) throws IOException, SAXException, ServletException {
		
		InputStream inputValue = multipart.getField("data").getEntityAs(InputStream.class);
		InputStream configurationStream = null;
		String configuration = null;
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters(); 
		if (queryParams != null) {
			configuration = queryParams.getFirst("configuration");
		}
		if (configuration == null || "".equals(configuration)) {
			configurationStream = multipart.getField("configuration").getEntityAs(InputStream.class);
		} else {
			configurationStream = new ByteArrayInputStream(configuration.getBytes());
		}
		ByteArrayOutputStreamWrapper bos = WrapperUtils.fromInputStreamToOutputWrapper(inputValue);
		
		StreamHolder holder = new StreamHolder(new ByteArrayInputStreamWrapper(bos.getBuffer(), 0, bos.size()));
		try {
			bos.close();
		} catch (IOException e) {
		}
		
		bos = null;
		
		Digester digester = new Digester();
		digester.addFactoryCreate("*/step", new StepsFactory());
		
		String[] paramTypes = new String[]{"java.lang.String"};
		digester.addCallMethod("*/step/disabled", "setDisabled", 1, paramTypes);
		digester.addCallParam("*/step/disabled", 0);

		digester.addObjectCreate("*/step/configuration",
				transformationsEngine.digester.steps.transformers.configurations.StaxonConfiguration.class);

		//this do not work so is not equivalent
//		digester.addSetProperties("*/step/configuration/*", new String[] {"autoArray", "autoPrimitive", "virtualNode"},
//				new String[] {"autoArray", "autoPrimitive", "virtualNode"});
		digester.addCallMethod("*/step/configuration/autoArray", "setAutoArray", 0);
		digester.addCallMethod("*/step/configuration/virtualNode", "setVirtualNode", 0);
		digester.addCallMethod("*/step/configuration/autoPrimitive", "setAutoPrimitive", 0);
		digester.addSetNext("*/step/configuration","setConfiguration");
		
		digester.addSetProperties("*/step/param", MapOfStepsParametersFactory.getSingletion().getParameters(),
				MapOfStepsParametersFactory.getSingletion().getMapFields());
		
		digester.push(holder);
		digester.push(multipart);
		digester.push(queryParams);
		
		paramTypes = new String[] {"transformationsEngine.wrappers.StreamHolder",
				"org.glassfish.jersey.media.multipart.FormDataMultiPart",
				"javax.ws.rs.core.MultivaluedMap"};
		digester.addCallMethod("*/step", "execute", 3, paramTypes);
		digester.addCallParam("*/step", 0, 3);
		digester.addCallParam("*/step", 1, 2);
		digester.addCallParam("*/step", 2, 1);
		
		//configuration is parsed
		StreamHolder output = digester.parse(configurationStream);
		ByteArrayInputStreamWrapper inputStream = output.getInputStream();
		RestStreamingOutput stream = new RestStreamingOutput(inputStream.getBuffer(), 0, inputStream.size());
		return Response.ok().entity(stream).build();
	}
	
	/**
	 * Transform a stream into an output stream using staxon.
	 * @param inputValue
	 * @param configuration
	 * @return http response
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws XMLStreamException 
	 */
	@Path("staxon")
	@PUT
	@Consumes({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON , MediaType.TEXT_XML})
	@Produces({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON , MediaType.TEXT_XML})
	public Response processDataStream(final InputStream inputValue,
			@QueryParam("mode") final String mode,
			@QueryParam("configuration") final String configuration) throws IOException, SAXException, XMLStreamException {
		
		ByteArrayOutputStreamWrapper bos = WrapperUtils.fromInputStreamToOutputWrapper(inputValue);
		
		StreamHolder holder = new StreamHolder(new ByteArrayInputStreamWrapper(bos.getBuffer(), 0, bos.size()));
		try {
			bos.close();
		} catch (IOException e) {
		}
		
		bos = null;
		
		StaxonStep step = null;
		switch (mode) {
			case "json2xml":
				step = new Json2XmlStep();
				step.setSerializedConfiguration(configuration);
				step.execute(holder, null, null);
				break;
			case "xml2json":
				step = new Xml2JsonStep();
				step.setSerializedConfiguration(configuration);
				step.execute(holder, null, null);
				break;
			default:
				return Response.status(Status.NOT_FOUND).build();
		}
		
		ByteArrayInputStreamWrapper inputStream = holder.getInputStream();
		RestStreamingOutput stream = new RestStreamingOutput(inputStream.getBuffer(), 0, inputStream.size());
		return Response.ok().entity(stream).build();
	}
}
