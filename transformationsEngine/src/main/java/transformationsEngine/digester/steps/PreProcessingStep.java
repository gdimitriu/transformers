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
 * This is the PreProcessing step class, this in the test case is the first transformation done by digester.
 */
public class PreProcessingStep extends AbstractStep {

	/** mode ex: xml2json, json2xml */
	private String mode = null;

	public PreProcessingStep() {
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(final String mode) {
		this.mode = mode;
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
		String addedMode = "=>preProcessing:" + mode;
		output.write(addedMode.getBytes());
		holder.setOutputStream(output);
	}

}
