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
package transformationsEngine.flow.configuration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Test;

import transformationsEngine.flow.configuration.FlowConfiguration;
import transformationsEngine.flow.configuration.StepParameters;

/**
 * Unitests for JAXB flow.
 */
public class FlowConfigurationTest {

	/**
	 * test a flow bindings using jaxb composes from two xsl steps.
	 * @throws JAXBException
	 */
	@Test
	public void FlowConfigurationTwoStepsXslUnmarshallerTest() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(FlowConfiguration.class);
		FlowConfiguration configuration = (FlowConfiguration) context.createUnmarshaller()
				.unmarshal(getClass().getClassLoader().getResourceAsStream("processingTwoStepsXsl.xml"));
		assertEquals("should be two xsl steps", 2, configuration.getSteps().length);
		assertEquals("Name first step", "XslStep", configuration.getSteps()[0].getName());
		assertEquals("enabled first step", false, configuration.getSteps()[0].isDisabled());
		StepParameters parameters = configuration.getSteps()[0].getParam();
		assertEquals("factoryEngine first step", "net.sf.saxon.TransformerFactoryImpl", parameters.getFactoryEngine());
		assertEquals("map fist step", "processingMap.xsl", parameters.getMappingName());
		assertTrue("mode first step", parameters.getMode() == null);
		assertEquals("Name second step", "XslStep", configuration.getSteps()[1].getName());
		parameters = configuration.getSteps()[1].getParam();
		assertEquals("factoryEngine second step", "net.sf.saxon.TransformerFactoryImpl", parameters.getFactoryEngine());
		assertEquals("map second step", "processingMap1.xsl", parameters.getMappingName());
		assertTrue("mode second step", parameters.getMode() == null);
		assertEquals("enabled second step", false, configuration.getSteps()[1].isDisabled());
	}
	
	/**
	 * test a flow bindings using jaxb composes from one xml2json and one json2xml steps.
	 * @throws JAXBException
	 */
	@Test
	public void FlowConfigurationxml2jsonxmlUnmarshallerTest() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(FlowConfiguration.class);
		FlowConfiguration configuration = (FlowConfiguration) context.createUnmarshaller()
				.unmarshal(getClass().getClassLoader().getResourceAsStream("xml2json2xml.xml"));
		assertEquals("should be two xsl steps", 2, configuration.getSteps().length);
		assertEquals("Name first step", "Xml2JsonStep", configuration.getSteps()[0].getName());
		assertEquals("disabled first step", true, configuration.getSteps()[0].isDisabled());
		StepParameters parameters = configuration.getSteps()[0].getParam();
		assertEquals("mode xml2json first step", "xml2json", parameters.getMode());
		assertEquals("Name first step", "Json2XmlStep", configuration.getSteps()[1].getName());
		assertEquals("enabled second step", false, configuration.getSteps()[1].isDisabled());
		parameters = configuration.getSteps()[1].getParam();
		assertEquals("mode json2xml second step", "json2xml", parameters.getMode());
	}
}
