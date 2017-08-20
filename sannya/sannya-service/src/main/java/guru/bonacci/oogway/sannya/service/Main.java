package guru.bonacci.oogway.sannya.service;

/**
 * Allow the servers to be invoked from the command-line. The jar is built with
 * this as the <code>Main-Class</code> in the jar's <code>MANIFEST.MF</code>.
 * 
 * @author Thank you Paul Chapman
 */
public class Main {

	public static void main(String[] args) {

		if (args.length == 1) {
			// Optionally set the HTTP port to listen on, overrides
			// value in the <server-name>-server.properties file
			System.setProperty("server.port", args[0]);
		}

		SannyasServer.main(args);
	}
}
