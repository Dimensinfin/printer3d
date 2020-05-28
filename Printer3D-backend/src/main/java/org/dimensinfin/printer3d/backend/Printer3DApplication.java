package org.dimensinfin.printer3d.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.io.ClassPathResource;

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
		private boolean printedVersion = false;

		private void printVersion(final String banner ) {
			LogWrapper.info( "\n\n" +banner+"\n");
		}

		private String readAllBytesJava7( String filePath ) {
		 	String content = "";
			try {
				content = new String( Files.readAllBytes( Paths.get( filePath ) ) );
			} catch (final IOException ioe) {
				LogWrapper.error( ioe );
			}
			return content;
		}

		void print() {
			this.printVersion(this.readAllBytesJava7(   new ClassPathResource("classpath:banner.txt").getPath()));
		}
	}
}
