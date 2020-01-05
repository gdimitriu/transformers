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

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This is the jetty HTTP server in which run the jersey REST server.
 */
public class ServerJetty {
	/** http jetty server */
	private Server server = null;
	/** rest response port */
	private int serverPort = 8099;
	/** context handler for jersey */
	private ServletContextHandler context = null;
	/** servlet handler for jersey */
	private ServletHolder sh = null;

	private AtomicBoolean started = new AtomicBoolean();

	public ServerJetty(final int port) {
		serverPort = port;
	}
	
	public ServerJetty() {
	}
	
	/**
	 * stop the REST server.
	 * @throws Exception
	 */
	public void stopServer() throws Exception {
		if(server != null) {
			server.stop();
		}
	}
	
	public static void main(String[] args) {
		ServerJetty jetty = null;
		if (args.length > 0) {
			jetty = new ServerJetty(Integer.parseInt(args[0]));
		}
		 jetty = new ServerJetty();
		try {
			jetty.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Application started. Write EXIT to stop it...");
		try {
			Scanner scanner = new Scanner(System.in);
			String str = scanner.nextLine();
			while(!"EXIT".equals(str)) {
				str = scanner.nextLine();
			}
			jetty.stopServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		stopServer();
	}
	
	
	/**
	 * Start the REST server.
	 * @throws Exception
	 */
	public void startServer() throws Exception {

		started.set(false);
		sh = new ServletHolder(ServletContainer.class);

		sh.setInitParameter("jersey.config.server.provider.packages", "transformationsEngine.serverRest.resources");
		sh.setInitParameter("com.sun.jersey.config.property.packages", "transformationsEngine.serverRest.AppConfig");
		sh.setInitParameter("javax.ws.rs.Application", "transformationsEngine.serverRest.AppConfig");
		sh.setInitParameter("com.sun.jersey.config.property.packages", "rest");
		server = new Server(serverPort);

		context = new ServletContextHandler(server, "/",
				ServletContextHandler.SESSIONS);

		context.addServlet(sh, "/*");

		server.start();
		started.set(true);
	}

	public boolean isStarted() {
		return started.get();
	}
}