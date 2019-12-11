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
import javax.xml.bind.annotation.XmlAttribute;

/**
 * This hold the parameters for step.
 * This is spread in digester steps.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class StepParameters {
	
	@XmlAttribute(name = "mode")
	private String mode = null;
	
	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @param mappingName the mappingName to set
	 */
	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	/**
	 * @param factoryEngine the factoryEngine to set
	 */
	public void setFactoryEngine(String factoryEngine) {
		this.factoryEngine = factoryEngine;
	}

	@XmlAttribute(name = "mappingName")
	private String mappingName = null;
	
	@XmlAttribute(name = "factoryEngine")
	private String factoryEngine = null;

	public StepParameters() {
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @return the mappingName
	 */
	public String getMappingName() {
		return mappingName;
	}

	/**
	 * @return the factoryEngine
	 */
	public String getFactoryEngine() {
		return factoryEngine;
	}

}
