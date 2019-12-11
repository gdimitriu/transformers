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
package transformationsEngine.interfaces;

import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import transformationsEngine.wrappers.StreamHolder;

/**
 * This is the abstract step class, the base of classes uses as transformation step by digester.
 */

public abstract class AbstractStep implements ITransformerStep {

	/** disable the step */
	private String disabled = "false";
	
	/* (non-Javadoc)
	 * @see transformationsEngine.digester.steps.ITransformerStep#setDisabled(java.lang.String)
	 */
	@Override
	public void setDisabled(final String disabled) {
		this.disabled = disabled;
	}
	
	/* (non-Javadoc)
	 * @see transformationsEngine.digester.steps.ITransformerStep#isDisabled()
	 */
	@Override
	public boolean isDisabled() {
		return "true".equals(disabled);
	}

	public AbstractStep() {
	}

	/* (non-Javadoc)
	 * @see transformationsEngine.digester.steps.ITransformerStep#execute(transformationsEngine.wrappers.StreamHolder, org.glassfish.jersey.media.multipart.FormDataMultiPart, javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	public abstract void execute(final StreamHolder holder, final FormDataMultiPart multipart, final MultivaluedMap<String, String> queryParams)
			throws Exception;
}
