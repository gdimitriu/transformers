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
package transformationsEngine.wrappers;

import java.io.IOException;
import java.io.InputStream;

/**
 * This contains utils for transformation of IO stream into wrapper.
 */
public class WrapperUtils {


	public WrapperUtils() {
	}
	
	/**
	 * This will transform the InputStream into ByteArrayOutputStreamWrapper. 
	 * @param inputStream
	 * @return ByteArrayOutputStreamWrapper
	 * @throws IOException
	 */
	public static ByteArrayOutputStreamWrapper fromInputStreamToOutputWrapper(final InputStream inputStream) throws IOException {
		ByteArrayOutputStreamWrapper bos = new ByteArrayOutputStreamWrapper();
		byte[] buffer = new byte[1024];
		int len;
		len = inputStream.read(buffer);
		while (len != -1) {
			bos.write(buffer, 0, len);
		    len = inputStream.read(buffer);
		}
		return bos;
	}

}
