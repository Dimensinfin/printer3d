package org.dimensinfin.printer3d.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.io.Resource;

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
		@Value("classpath:app-banner.txt")
		private Resource banner;

		public void print() {
			this.printVersion( this.readAllBytesJava7( this.banner ) );
		}

		private void printVersion( final String banner ) {
			final String bannelHard = "  ___     _  _       ___  \n" +
					" / _ \\   | || |     / _ \\ \n" +
					"| | | |  | || |_   | | | |\n" +
					"| |_| |  |__   _|  | |_| |\n" +
					" \\___(_)    |_|(_)  \\___/ \n" +
					"                          \n";
			LogWrapper.info( "\n\n" + bannelHard + "\n" );
		}

		private String readAllBytesJava7( final Resource banner ) {
			String content = "";
			if (null != banner) {
				try {
					final String bannerPath = Paths.get( banner.getURI() ).toAbsolutePath().toString();
					content = new String( Files.readAllBytes( Paths.get( banner.getURI() ) ) );
				} catch (final IOException ioe) {
					LogWrapper.error( ioe );
				}
			}
			return content;
		}
	}
}
