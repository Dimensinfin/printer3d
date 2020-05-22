package org.dimensinfin.printer3d.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.dimensinfin.logging.LogWrapper;

/**
 * This is the spring boot application starter. Once the application instance is created then the spring boot will create all the beans and
 * components.
 *
 * @author Adam Antinoo
 * @since 0.9.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
public class Printer3DApplication {
	public static final String APPLICATION_ERROR_CODE_PREFIX = "dimensinfin.printer3d";
	private static LogoPrinter printer;

	public static void main( String[] args ) {
		LogWrapper.enter();
		SpringApplication.run( Printer3DApplication.class, args );
		printer = new LogoPrinter();
		printer.print();
		LogWrapper.exit();
	}

	public static boolean hasPrinter() {
		return null != printer;
	}

	private static final class LogoPrinter {
		private boolean printedVersion = false;

		private void printVersion() {
			LogWrapper.info( "\n\n" +
					"        ___   _ ____    ___  \n" +
					"__   __/ _ \\ / |___ \\  / _ \\ \n" +
					"\\ \\ / / | | || | __) || | | |\n" +
					" \\ V /| |_| || |/ __/ | |_| |\n" +
					"  \\_/  \\___(_)_|_____(_)___/ \n" +
					"                             \n" );
		}

		void print() {
			if (!this.printedVersion) this.printVersion();
			this.printedVersion = true;
		}
	}
}
