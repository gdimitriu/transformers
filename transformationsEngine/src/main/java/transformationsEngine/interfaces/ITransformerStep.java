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
import transformationsEngine.wrappers.WrapperUtils;

/**
 * This is the interface which every step should implement.
 */
public interface ITransformerStep {

	/**
	 * set disabled state for the step.
	 * @param disabled if the step will be disabled.
	 */
	public default void setDisabled(String disabled) {
		
	}

	/**
	 * return disabled state of step.
	 * @return true if the step is disabled
	 */
	public default boolean isDisabled() {
		return false;
	}

	/**
	 * This is the execute method, this is used to execute the actual transformation.
	 * The input is holder in which will be also the return and intermediary values. 
	 * @param holder StreamHolder.
	 * @param multipart multipart from rest request
	 * @throws Exception 
	 */
	public default void execute(StreamHolder holder,
			FormDataMultiPart multipart,
			MultivaluedMap<String, String> queryParams) throws Exception {
		holder.setOutputStream(
				WrapperUtils.fromInputStreamToOutputWrapper(holder.getInputStream()));
	}

}