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
package transformationsEngine.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * This contains utils for read resources and string transformation.
 */
public final class StreamUtils {

	private StreamUtils() {
	}
	
	static public String getStringFromResource(final String resourceFile) {
		return  new BufferedReader(new InputStreamReader(
				StreamUtils.class.getClassLoader().getResourceAsStream(resourceFile)))
		  .lines().collect(Collectors.joining("\n"));
	}
	
	static public String getStringFromResourceWithoutSpaces(final String resourceFile) {
		return  removeXmlHeader(new BufferedReader(new InputStreamReader(
				StreamUtils.class.getClassLoader().getResourceAsStream(resourceFile)))
		  .lines().map(s -> s.trim()).collect(Collectors.joining("")));
	}
	
	static public String toStringFromStream(final InputStream stream) {
		return removeXmlHeader(new BufferedReader(new InputStreamReader(stream)).lines()
				.collect(Collectors.joining("\n")));
	}

	private static String removeXmlHeader(String str) {
		if (str.indexOf("?>") > 0  && str.indexOf("<?xml") == 0) {
			return str.substring(str.indexOf("?>") + 2);
		} else {
			return str;
		}
	}
}
