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

import java.io.ByteArrayInputStream;

/**
 * This is wrapper over ByteArrayInputStream to have access to the buffer.
 * This is used by StreamHolder to not duplicate the buffer in case of transformation chain.
 */
public class ByteArrayInputStreamWrapper extends ByteArrayInputStream {

	/**
	 * @param buf the buffer
	 */
	public ByteArrayInputStreamWrapper(byte[] buf) {
		super(buf);
	}

	/**
	 * @param buf the buffer
	 * @param offset the offset from buffer
	 * @param length the length of the message
	 */
	public ByteArrayInputStreamWrapper(byte[] buf, int offset, int length) {
		super(buf, offset, length);
		// TODO Auto-generated constructor stub
	}

	/**
	 * get the buffer without copying it.
	 * @return the buffer.
	 */
	public synchronized byte[] getBuffer() {
		return this.buf;
	}
	
	/**
	 * return the real size or the buffer.
	 * @return size
	 */
	public synchronized int size() {
		return this.count;
	}
}
