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
package transformationsEngine.flow.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This hold the JAXB flow configuration.
 * This corresponds to the top configuration received by digester.
 */
@XmlRootElement(name = "flow")
@XmlAccessorType(XmlAccessType.FIELD)
public class FlowConfiguration {
	
	@XmlElement(name = "steps")
	StepsConfiguration steps = null;

	public FlowConfiguration() {
	}

	/**
	 * get the steps of the flow.
	 * @return
	 */
	public StepConfiguration[] getSteps() {
		return steps.getSteps();
	}
}
