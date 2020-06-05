/*
 * 3D Printer Queue Management API
 * # Summary ### This is the OpenApi specification active and matching the latest published backend application version seen on the Heroku Production servers. This API defines all the client REST endpoints public and available to other systems. The API definition follows the OpenApi 3.0 specification and there are provisioning for automatic code generation for this API clients. ## The current API specification matches the Printer3D backend system published with version **0.4.2**. # System Description The system is composed of 4 subsystems interconnected by interchange lines. The first system represents the external user demand. They have a catalog of Parts and generate a queue of Requests. This is connected to the second subsystem that is the Storage system. The storage contains the elements available to other systems or the elements to generate Parts. This second system connects with the third subsystem called Production that is the responsible to calculate and control the elements to be produced and the tool to build them. The input are the demand of Storage to keep the stocks or to fulfill the Requests. And finally this reports to the four subsystem to buy materials and tools to maintain the Production operative and at the right levels.  Not all the systems have an application implementation. There are two main applications. One called **frontend** to deal with the Graphical User Interface to create Reports and to control the Storage and another called **backend** to persist all the system data and to run the different Production algorithms that are required.  This API is the communication channel between the backend and the frontend and represents a contract so the two applications can be developed independently but having the assurance that they will interface properly when deployed into their corresponding hardware. # API Description The API will expose endpoints aggregated by the information unit. Entities under the same information unit shre part of the path. For example the Inventory is an information unit that will report the existence of different types of elements like Parts or Materials. All these entities are part of the system Inventory.  Each of the subsystems can be view as an autonomous information section sharing the same repository and server. We have currently identified two of those information sections, the **Inventory** and the **Production** ## INFORMATION UNITS ### INVENTORY UNIT Under the **Inventory** information unit we now can found two entities. The **Parts** are models that can be built with the other entity that are the plastic filament material identified by the **Material** entity. There should be a third entity called **Machine** that is where the plastic filament is converted on the Part. * **Part** - This is the definition of an item that can be sold independently. The Part is the view side from the 3D printer stand view. Parts define the time slot use of the 3D printer and have some cost and price attached. The Part will also store the stock information so for active parts this information is used to calculate the jobs pending to stabilize a stock level. * **Coil** - To build a Part we require to use a plastic filament **Coil** on a 3D printer so we can print a thin plastic layer upon another layer until we have the model complete. The **Coils** are the storage for the plastic. It comes in long mono filament plastic lines of thousands of metres of length. Usually they are bought by weight. * **Finishing** - This is an especial response entity to collect all the distinct colors available for an specific type of material. * **Machine** - This is the entity that describes a 3D printer model and its configuration. The information about Machines is hardcoded at initialization time and the machine records can only update the current build **Part** job. Each Machine has a slot to record the current build action is one is running. This block will keep track of the remaining time to complete the job and then update the stock records. ### PRODUCTION UNIT On this information unit we can find a virtual job generator that will use a set of rules to construct a list of the jobs required to reach a target on the **Inventory** unit. If the stock levels are below the stock limits then the missing Parts are added as build jobs until the number desired is reached. Other rules are able to increase or decrease this number depending on other measures or also decide the better schedule order for the list already generated. On the **Production** information unit we can find the list of Pending Jobs. * **PendingJob** - The information required to report the 3D machine owner the necessity to build a particular **Part**. It should indicate the correct model and **Finishing**. There is a job for every single Part. If the machine is able to build more than one identical part at the same time this should be controller at other information unit.
 *
 * OpenAPI spec version: 0.4.3
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.Objects;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * The record that describes a **Machine** and their current status. If a machine is running a build job then there is some more information persisted
 * on the repository record and also reflected on the exported api.
 */
@ApiModel(description = "The record that describes a **Machine** and their current status. If a machine is running a build job then there is some more information persisted on the repository record and also reflected on the exported api. ")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-06-05T10:30:15.118123+02:00[Europe/Madrid]")
public class Machinev2 {
	public static final String SERIALIZED_NAME_ID = "id";
	public static final String SERIALIZED_NAME_LABEL = "label";
	public static final String SERIALIZED_NAME_MODEL = "model";
	public static final String SERIALIZED_NAME_CHARACTERISTICS = "characteristics";
	public static final String SERIALIZED_NAME_BUILD_RECORD = "buildRecord";
	@SerializedName(SERIALIZED_NAME_ID)
	private UUID id = null;
	@SerializedName(SERIALIZED_NAME_LABEL)
	private String label = null;
	@SerializedName(SERIALIZED_NAME_MODEL)
	private String model = null;
	@SerializedName(SERIALIZED_NAME_CHARACTERISTICS)
	private String characteristics = null;
	@SerializedName(SERIALIZED_NAME_BUILD_RECORD)
	private BuildRecord buildRecord = null;

	// - G E T T E R S   &   S E T T E R S

	/**
	 * Get buildRecord
	 *
	 * @return buildRecord
	 **/
	@ApiModelProperty(required = true, value = "")
	public BuildRecord getBuildRecord() {
		return buildRecord;
	}

	public void setBuildRecord( BuildRecord buildRecord ) {
		this.buildRecord = buildRecord;
	}

	/**
	 * A free text with the list of the machine characteristics like special configurations, limits or sizes.
	 *
	 * @return characteristics
	 **/
	@ApiModelProperty(example = "Max size set to 200mm. Has adaptor for flexible plastic filament.", value = "A free text with the list of the machine characteristics like special configurations, limits or sizes. ")
	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics( String characteristics ) {
		this.characteristics = characteristics;
	}

	/**
	 * The unique identifier for a single machine. Used for machine-job identification.
	 *
	 * @return id
	 **/
	@ApiModelProperty(example = "009ab011-03ad-4e84-9a88-25708d1cfd64", required = true, value = "The unique identifier for a single machine. Used for machine-job identification. ")
	public UUID getId() {
		return id;
	}

	public void setId( UUID id ) {
		this.id = id;
	}

	/**
	 * The user readable label to be assigned to a single Machine. Can be used instead of the unique identifier UUID.
	 *
	 * @return label
	 **/
	@ApiModelProperty(example = "Machine A", required = true, value = "The user readable label to be assigned to a single Machine. Can be used instead of the unique identifier UUID. ")
	public String getLabel() {
		return label;
	}

	public void setLabel( String label ) {
		this.label = label;
	}

	/**
	 * The model type of the 3D printer Machine.
	 *
	 * @return model
	 **/
	@ApiModelProperty(example = "Creality 3D Ender 3 Pro", required = true, value = "The model type of the 3D printer Machine. ")
	public String getModel() {
		return model;
	}

	public void setModel( String model ) {
		this.model = model;
	}

	public int getRemainingTime() {
		return this.buildRecord.getRemainingTime();
	}

	public boolean isRunning() {
		return (this.buildRecord.getState() == BuildRecord.StateEnum.RUNNING);
	}

	public boolean isSetup() {
		return (this.buildRecord.getState() == BuildRecord.StateEnum.SETUP);
	}

	public Machinev2 buildRecord( BuildRecord buildRecord ) {
		this.buildRecord = buildRecord;
		return this;
	}

	public Machinev2 characteristics( String characteristics ) {
		this.characteristics = characteristics;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash( id, label, model, characteristics, buildRecord );
	}

	@Override
	public boolean equals( Object o ) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Machinev2 machinev2 = (Machinev2) o;
		return Objects.equals( this.id, machinev2.id ) &&
				Objects.equals( this.label, machinev2.label ) &&
				Objects.equals( this.model, machinev2.model ) &&
				Objects.equals( this.characteristics, machinev2.characteristics ) &&
				Objects.equals( this.buildRecord, machinev2.buildRecord );
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "class Machinev2 {\n" );

		sb.append( "    id: " ).append( toIndentedString( id ) ).append( "\n" );
		sb.append( "    label: " ).append( toIndentedString( label ) ).append( "\n" );
		sb.append( "    model: " ).append( toIndentedString( model ) ).append( "\n" );
		sb.append( "    characteristics: " ).append( toIndentedString( characteristics ) ).append( "\n" );
		sb.append( "    buildRecord: " ).append( toIndentedString( buildRecord ) ).append( "\n" );
		sb.append( "}" );
		return sb.toString();
	}

	public Machinev2 id( UUID id ) {
		this.id = id;
		return this;
	}

	public Machinev2 label( String label ) {
		this.label = label;
		return this;
	}

	public Machinev2 model( String model ) {
		this.model = model;
		return this;
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString( Object o ) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace( "\n", "\n    " );
	}

}

