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


/**
 * This is holder of inputStream and outputStream and is send between transformation.
 * 
 * This is due to how the digester use the stack.
 */
public class StreamHolder {

	/** input stream */
	ByteArrayInputStreamWrapper inputStream = null;
	/** output stream */
	ByteArrayOutputStreamWrapper outputStream = null;
	
	public StreamHolder() {
		inputStream = null;
		outputStream = null;
	}
	/**
	 * Constructor using input stream.
	 * This should be the start of the transformation chain.
	 */
	public StreamHolder(final ByteArrayInputStreamWrapper input) {
		inputStream = input;
		outputStream = null;
	}
	
	/**
	 * Get the input stream which is assigned
	 * or a new input stream which hold the buffer from the output stream.
	 * If it has output stream only one call of the method is allowed.
	 * @return input stream or null if is not allowed
	 */
	public ByteArrayInputStreamWrapper getInputStream() {
		if (outputStream == null && inputStream != null) {
			return inputStream;
		}
		if (outputStream != null) {
			ByteArrayInputStreamWrapper out =  new ByteArrayInputStreamWrapper(outputStream.getBuffer(), 0, outputStream.size());
			inputStream = null;
			outputStream = null;
			return out;
		}
		return null;
	}
	
	/**
	 * If the conversion is allowed the output stream will be converted
	 * only one call of the getInputStream is allowed.
	 * @return true if is a conversion
	 */
	public boolean isOutputToInputConversion() {
		if (outputStream != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Set the output stream.
	 * @param output
	 */
	public void setOutputStream(final ByteArrayOutputStreamWrapper output) {
		outputStream = output;
		inputStream = null;
	}

	/**
	 * Set the input stream.
	 * @param input
	 */
	public void setInputStream(final ByteArrayInputStreamWrapper input) {
		inputStream = input;
		outputStream = null;
	}
	
	/**
	 * Get the output buffer. This is used for transformation to another type of stream.
	 * @return byte buffer.
	 */
	public byte[] getOutputBuffer() {
		return outputStream.getBuffer();
	}
	
	/**
	 * Get the input buffer. This is used for transformation to another type of stream. 
	 * @return byte buffer.
	 */
	public byte[] getInputBuffer() {
		return inputStream.getBuffer();
	}
}
