package org.joow.extremestartup.kickoff;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ExtremeStartupKickoff {
	private static final String DEFAULT_PORT = "1234";

	public static void main(String[] args) throws IOException {
		final String port = System.getProperty("PORT") == null ? DEFAULT_PORT : System.getProperty("PORT");
		final HttpServer server = HttpServer.create(new InetSocketAddress(asInt(port)), 0);
		server.createContext("/", new HttpHandler() {
			@Override
			public void handle(HttpExchange exchange) throws IOException {
				log(exchange.getRequestURI().toString());
				final String response = "Walter White";
				exchange.sendResponseHeaders(200, response.getBytes(Charset.forName("UTF-8")).length);

				try(final OutputStream outputStream = exchange.getResponseBody()) {
					outputStream.write(response.getBytes(Charset.forName("UTF-8")));
				} catch (IOException e) {
					err(e.getMessage());
				}
			}
		});

		log("Starting server on port " + port + "...");
		server.start();

		log("Server started and accessible @ http://localhost:" + port);
		log("Press CTRL+C to stop it.");
	}

	private static int asInt(final String number) {
		return Integer.valueOf(number);
	}

	private static void log(final String message) {
		System.out.println(message);
	}

	private static void err(final String message) {
		System.err.println(message);
	}
}
