package org.dimensinfin.printer3d.backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
		new LogoPrinter().print();
		LogWrapper.exit();
	}

	public static boolean hasPrinter() {
		return null != printer;
	}

	private static final class LogoPrinter {
		private final String banner = "app-banner.txt";

		public void print() {
			this.printVersion( this.readAllBytes( this.banner ) );
		}

		private void printVersion( final String bannerData ) {
			LogWrapper.info( "\n\n" + bannerData + "\n" );
		}

		private String readAllBytes( final String resourceName ) {
			try {
				File resource = new File ("build/resources/main/app-banner.txt");
				return new String( Files.readAllBytes( resource.toPath() ) );
			} catch (final IOException ioe) {
				LogWrapper.error( ioe );
				return "        ___     _____   _ \n" +
						"__   __/ _ \\   |___  | / |\n" +
						"\\ \\ / / | | |     / /  | |\n" +
						" \\ V /| |_| |    / /_  | |\n" +
						"  \\_/  \\___(_)  /_/(_) |_|\n" +
						"                          \n";
			}
		}
	}
}
