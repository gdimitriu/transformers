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
package transformationsEngine.digester;

import org.apache.commons.digester3.AbstractObjectCreationFactory;
import org.xml.sax.Attributes;

/**
 * This factory will create the step instances used by digester engine.
 */
public class StepsFactory extends AbstractObjectCreationFactory<Object> {

	@Override
	public Object createObject(Attributes attributes) throws Exception {
		Class<?> result;
		String className = attributes.getValue("class");
		className = "transformationsEngine.digester.steps." + className;
		try {
			result = Class.forName(className);
		} catch (ClassNotFoundException e) {
			className = attributes.getValue("class");
			className = "transformationsEngine.digester.steps.transformers." + className;
			result = Class.forName(className);
		}
		return result.newInstance();
	}

}
