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
package transformationsEngine.serverRest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Exception Mapper which map a throwable to response status.
 * This will be use by rest to send message from throwable to rest.
 */
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Throwable> {

	/**
	 * convert the exception which was thrown by transformation into a HTTP response.
	 * @param throwable which was catched.
	 * @return http response.
	 */
	public Response toResponse(Throwable throwable) {
		return Response.status( Status.INTERNAL_SERVER_ERROR ).entity( throwable.getMessage() ).type( "text/plain" ).build();
	}

}
