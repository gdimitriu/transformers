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
package transformationsEngine.digester.steps.transformers;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import transformationsEngine.digester.steps.transformers.configurations.StaxonConfiguration;
import transformationsEngine.interfaces.AbstractStep;
import transformationsEngine.wrappers.ByteArrayInputStreamWrapper;
import transformationsEngine.wrappers.ByteArrayOutputStreamWrapper;
import transformationsEngine.wrappers.StreamHolder;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;

/**
 * This hold the basic configuration and transformation of staxon step.
 * This will be inherited by xml2json and json2xml steps.
 */
public class StaxonStep extends AbstractStep{
	/** configuration for xml builder */
	protected JsonXMLConfig config = null;
	/** configuration builder */
	protected JsonXMLConfigBuilder builder = null;
	/** staxon configuration this will hold configuration send by client */
	protected StaxonConfiguration configuration = null;
	/**
	 * standard constructor and default feature of staxon.
	 */
	public StaxonStep() {
		builder = new JsonXMLConfigBuilder().multiplePI(true);
	}
	
	public void setConfiguration(final StaxonConfiguration config) {
		this.configuration = config;
	}
	
	/**
	 * This will set the client serialized configuration.
	 * This will unmarshall the configration using jaxb.
	 * @param config serialized configuration
	 */
	public void setSerializedConfiguration(final String config) {
		if (config == null || config.equals("")) {
			configuration = null;
			return;
		}
		try {
			JAXBContext context = JAXBContext.newInstance(StaxonConfiguration.class);
			configuration = (StaxonConfiguration) context.createUnmarshaller()
					.unmarshal(new StringReader(config));
		} catch (JAXBException e) {
			e.printStackTrace();
			configuration = null;
		}
	}
	
	/**
	 * This will configure the builder factory of staxon.
	 * If it doesn't have configuration it will set the default safe features for staxon.
	 * If it has configuration it will call loadConfiguration.
	 */
	public void configure() {
		if (configuration == null) {
			builder = builder.autoArray(false).autoPrimitive(true);
		} else {
			loadConfiguration();
		}
		config = builder.build();
	}
	
	/**
	 * load the configuration and initialize the builder
	 */
	protected void loadConfiguration() {
		builder = builder.autoArray(configuration.getAutoArray())
				.autoPrimitive(configuration.getAutoPrimitive());
		if (configuration.getVirtualNode() != null && !configuration.getVirtualNode().equals("")) {
				builder = builder.virtualRoot(configuration.getVirtualNode());
		}
	}

	/**
	 * This call special processing if is needed.
	 * This should be overriden by inherited classes to obtain the desired functionality.
	 * This could be used to inject elements in front of generated message.
	 * @param eventReader
	 * @param eventWriter
	 * @return true if had
	 */
	protected boolean specialProcessing(final XMLEventReader eventReader,
			final XMLEventWriter eventWriter) {
		return false;
	}

	/**
	 * Create Event Reader which should be overriden by inherited classes.
	 * @param input
	 * @return XmlEventReader
	 * @throws XMLStreamException
	 */
	protected XMLEventReader createEventReader(final InputStream input) throws XMLStreamException {
		return null;
	}
	
	/**
	 * Create Event Writer which should be overriden by inherited classes.
	 * @param output
	 * @return XmlEventWriter
	 * @throws XMLStreamException
	 */
	protected XMLEventWriter createEventWriter(final OutputStream output) throws XMLStreamException{
		return null;
	}

	/**
	 * This is the main method which is called by digester.
	 * This will configure and then create the output message as stream back into the holder. 
	 */
	@Override
	public void execute(StreamHolder holder,
			final FormDataMultiPart multipart,
			final MultivaluedMap<String, String> queryParams)
			throws XMLStreamException {
		configure();
		ByteArrayInputStreamWrapper input = holder.getInputStream();
		ByteArrayOutputStreamWrapper output = new ByteArrayOutputStreamWrapper();
		XMLEventReader eventReader = null;
		XMLEventWriter eventWriter = null;
		
		try {
			eventReader = createEventReader(input);
			eventWriter = createEventWriter(output);
			if (!specialProcessing(eventReader,eventWriter)) {
				while (eventReader.hasNext()) {
					XMLEvent event = eventReader.nextEvent();
					eventWriter.add(event);
				}
			}
		} finally {
			//close all event reader/writer
			if (eventReader != null) {
				try {
					eventReader.close();
				} catch (XMLStreamException e) {
				}
			}
			if (eventWriter != null) {
				try {
					eventWriter.close();
				} catch (XMLStreamException e) {
				}
			}
		}
		holder.setOutputStream(output);
	}
}
