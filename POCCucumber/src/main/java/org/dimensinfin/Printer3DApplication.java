package org.dimensinfin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

/**
 * This is the spring boot application starter. Once the application instance is created then the spring boot will create all the beans and
 * components.
 *
 * @author Adam Antinoo
 * @since 0.9.0
 */
@Slf4j
@EnableDiscoveryClient
@EnableScheduling
@SpringBootApplication
public class Printer3DApplication {
	public static final String APPLICATION_ERROR_CODE_PREFIX = "dimensinfin.printer3d";

	public static void main( final String[] args ) {
		log.info( "ENTER" );
		SpringApplication.run( Printer3DApplication.class, args );
		new LogoPrinter().print();
		log.info( "EXIT" );
	}

	private static final class LogoPrinter {
		public void print() {
			this.printVersion( this.readAllBytes() );
		}

		private void printVersion( final String bannerData ) {
			log.info( "\n\n" + bannerData + "\n" );
		}

		private String readAllBytes() {
			try {
				final File resource = new File( System.getenv( "BANNER_LOCATION" ) );
				return new String( Files.readAllBytes( resource.toPath() ) );
			} catch (final NullPointerException | IOException ioe) {
				log.error( ioe.toString() );
				return "        ___      ___      ___  \n" +
						"__   __/ _ \\    / _ \\    / _ \\ \n" +
						"\\ \\ / / | | |  | | | |  | | | |\n" +
						" \\ V /| |_| |  | |_| |  | |_| |\n" +
						"  \\_/  \\___(_)  \\___(_)  \\___/ \n" +
						"                               \n";
			}
		}
	}
}
