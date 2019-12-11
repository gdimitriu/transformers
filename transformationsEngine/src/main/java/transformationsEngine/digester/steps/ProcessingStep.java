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
package transformationsEngine.digester.steps;

import java.io.IOException;

import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import transformationsEngine.interfaces.AbstractStep;
import transformationsEngine.wrappers.ByteArrayInputStreamWrapper;
import transformationsEngine.wrappers.ByteArrayOutputStreamWrapper;
import transformationsEngine.wrappers.StreamHolder;

/**
 * This is the Processing step class, this in the test case is the second transformation done by digester.
 */
public class ProcessingStep extends AbstractStep {

	/** mapping or processing */
	private String transformationMap = null;
	/** factory for the engine */
	private String factoryEngine = null;
	
	public ProcessingStep() {
	}

	/**
	 * get the processing mapping
	 * @return mapping
	 */
	public String getTransformationMap() {
		return transformationMap;
	}

	/**
	 * set the processing mapping
	 * @param mapping
	 */
	public void setTransformationMap(final String mapping) {
		this.transformationMap = mapping;
	}

	/**
	 * get the factory for the mapping.
	 * @return factory
	 */
	public String getFactoryEngine() {
		return factoryEngine;
	}

	/**
	 * set the factory for the mapping.
	 * @param tfFactoryEngine
	 */
	public void setFactoryEngine(final String tfFactoryEngine) {
		this.factoryEngine = tfFactoryEngine;
	}

	/* (non-Javadoc)
	 * @see transformationsEngine.digester.steps.AbstractStep#execute(transformationsEngine.wrappers.StreamHolder)
	 */
	@Override
	public void execute(final StreamHolder holder, final FormDataMultiPart multipart, final MultivaluedMap<String, String> queryParams)
			throws IOException {
		ByteArrayInputStreamWrapper input = holder.getInputStream();
		ByteArrayOutputStreamWrapper output = new ByteArrayOutputStreamWrapper();
		byte[] buffer = new byte[1024];
		int len;
		len = input.read(buffer);
		while (len != -1) {
			output.write(buffer, 0, len);
		    len = input.read(buffer);
		}
		String addedMode = "=>processing:" + transformationMap + " factory =" + factoryEngine;
		output.write(addedMode.getBytes());
		holder.setOutputStream(output);
	}

}
