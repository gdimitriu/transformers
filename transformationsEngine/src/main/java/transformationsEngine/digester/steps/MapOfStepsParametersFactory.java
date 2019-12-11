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

/**
 * This will provide parameters for digester set properties.
 */
public class MapOfStepsParametersFactory {
	
	final private static MapOfStepsParametersFactory singleton = new MapOfStepsParametersFactory();

	private MapOfStepsParametersFactory() {
	}
	
	/**
	 * get the singleton factory.
	 * @return singleton
	 */
	public static MapOfStepsParametersFactory getSingletion() {
		return singleton;
	}
	
	/**
	 * Return the parameters (first argument) for digester addSetProprieties.
	 * @return parameters
	 */
	public String[] getParameters() {
		return new String[]{"mode", "transformationMap", "factoryEngine", "engineConfiguration", "mappingName"};
	}

	/**
	 * Return the parameters (second argument) for digester addSetProprieties.
	 * @return parameters
	 */
	public String[] getMapFields() {
		return new String[]{"mode", "transformationMap", "factoryEngine", "engineConfiguration", "mappingName"};
	}
}
