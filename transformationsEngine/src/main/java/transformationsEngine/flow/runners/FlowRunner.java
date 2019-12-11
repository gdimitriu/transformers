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
package transformationsEngine.flow.runners;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import transformationsEngine.flow.configuration.FlowConfiguration;
import transformationsEngine.flow.configuration.StepConfiguration;
import transformationsEngine.interfaces.ITransformerStep;
import transformationsEngine.wrappers.StreamHolder;

/**
 * This hold the configuration of the flow.
 * This also create internally the step classes from the flow.
 * This will call the execute from the steps.
 */
public class FlowRunner {

	/** flow configuration steps */
	private FlowConfiguration configuration = null;
	/** multi-part request from rest which contains mapping. */
	private FormDataMultiPart multipart;
	/** query parameters which are parameters to be send to the steps */
	private MultivaluedMap<String, String> queryParams;
	/** list of steps to be executed */
	private List<ITransformerStep> stepList = new ArrayList<ITransformerStep>();

	/**
	 * flow runner constructor, this will create the flow configuration.
	 * The flow configuration will be created using JAXB.
	 * @param configurationStream
	 * @param multipart
	 * @param queryParams 
	 * @throws JAXBException 
	 */
	public FlowRunner(final InputStream configurationStream,
			final FormDataMultiPart multipart,
			final MultivaluedMap<String, String> queryParams) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(FlowConfiguration.class);
		this.configuration = (FlowConfiguration) context.createUnmarshaller().unmarshal(configurationStream); 
		this.multipart = multipart;
		this.queryParams = queryParams;
	}

	/**
	 * This will create the steps from the configuration.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 */
	public void createSteps() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, NoSuchFieldException, SecurityException {
		StepConfiguration[] steps = configuration.getSteps();
		for (StepConfiguration conf : steps) {
			ITransformerStep step = createStep(conf.getName());
			step.setDisabled(String.valueOf(conf.isDisabled()));
			conf.setStageParameters(step);
			stepList.add(step);
		}
		
	}

	/**
	 * execute the flow constructed using the configuration.
	 * @param holder input/output holder
	 * @throws Exception
	 */
	public void executeSteps(final StreamHolder holder) throws Exception {
		for (ITransformerStep step : stepList) {
			step.execute(holder, multipart, queryParams);
		}
	}
	
	/**
	 * This will create the concrete step. 
	 * This is equivalent with the StepsFactory from digester.
	 * @param name of the step (class)
	 * @return created steps
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private ITransformerStep createStep(final String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> result = null;
		String className = "transformationsEngine.digester.steps." + name;
		try {
			result = Class.forName(className);
		} catch (ClassNotFoundException e) {
			className = "transformationsEngine.digester.steps.transformers." + name;
			result = Class.forName(className);
		}
		return (ITransformerStep) result.newInstance();
	}

}
