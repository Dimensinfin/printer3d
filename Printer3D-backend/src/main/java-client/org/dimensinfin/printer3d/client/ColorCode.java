/*
 * 3D Printer Queue Management API
 * # Summary This is the OpenApi specification active and matching the latest published backend application version seen on the Heroku Production servers. This API defines all the client REST endpoints public and available to other systems. The API definition follows the OpenApi 3.0 specification and there are provisioning for automatic code generation for this API clients. The current API specification matches the Printer3D backend system published with version **0.2.0**. # API Description The API will expose the next list of entities: * **Part** - This is the definition of an item that can be sold independently. The Part is the view side from the 3D printer stand view. Parts define a tiem slot use of the 3D printer and have some cost and price attached. * ** Inventory** - This is the list of Parts declared on the system. Used as the base point to calculate stocks and generate the list of print jobs.
 *
 * OpenAPI spec version: 0.2.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.dimensinfin.printer3d.client;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * It is a shorl color code to be appended to the label for part identification. For the machine build job and the stock the color is a most important
 * element since differentiates to identical parts at the model level.
 */
@JsonAdapter(ColorCode.Adapter.class)
public enum ColorCode {

	GRN( "GRN" ),
	RED( "RED" ),

	PK( "PK" ),

	LB( "LB" ),

	BL( "BL" ),

	GR( "GR" );

	public static ColorCode fromValue( String text ) {
		for (ColorCode b : ColorCode.values()) {
			if (String.valueOf( b.value ).equals( text )) {
				return b;
			}
		}
		return null;
	}
	private String value;

// - C O N S T R U C T O R S
	ColorCode( String value ) {
		this.value = value;
	}

// - G E T T E R S   &   S E T T E R S
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf( value );
	}

	public static class Adapter extends TypeAdapter<ColorCode> {
		@Override
		public void write( final JsonWriter jsonWriter, final ColorCode enumeration ) throws IOException {
			jsonWriter.value( enumeration.getValue() );
		}

		@Override
		public ColorCode read( final JsonReader jsonReader ) throws IOException {
			String value = jsonReader.nextString();
			return ColorCode.fromValue( String.valueOf( value ) );
		}
	}
}

