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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

/**
 * MachineListv2
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-06-05T10:30:15.118123+02:00[Europe/Madrid]")
public class MachineListv2 {
	public static final String SERIALIZED_NAME_COUNT = "count";
	//  @SerializedName(SERIALIZED_NAME_COUNT)
	//  private Double count = null;

	public static final String SERIALIZED_NAME_MACHINES = "machines";
	@SerializedName(SERIALIZED_NAME_MACHINES)
	private List<Machinev2> machines = new ArrayList<Machinev2>();

	//  public MachineListv2 count(Double count) {
	//    this.count = count;
	//    return this;
	//  }

	// - G E T T E R S   &   S E T T E R S

	/**
	 * The number of machines that follow on the response.
	 *
	 * @return count
	 **/
	@SerializedName(SERIALIZED_NAME_COUNT)
	@ApiModelProperty(example = "2", required = true, value = "The number of machines that follow on the response. ")
	public Integer getCount() {
		return this.machines.size();
	}

	//  public void setCount(Double count) {
	//    this.count = count;
	//  }

	/**
	 * Get machines
	 *
	 * @return machines
	 **/
	@ApiModelProperty(required = true, value = "")
	public List<Machinev2> getMachines() {
		return machines;
	}

	public void setMachines( List<Machinev2> machines ) {
		this.machines = machines;
	}

	public MachineListv2 addMachinesItem( Machinev2 machinesItem ) {
		this.machines.add( machinesItem );
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash( machines );
	}

	@Override
	public boolean equals( Object o ) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		MachineListv2 machineListv2 = (MachineListv2) o;
		return Objects.equals( this.machines, machineListv2.machines );
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "class MachineListv2 {\n" );

		sb.append( "    count: " ).append( toIndentedString( this.getCount() ) ).append( "\n" );
		sb.append( "    machines: " ).append( toIndentedString( machines ) ).append( "\n" );
		sb.append( "}" );
		return sb.toString();
	}

	public MachineListv2 machines( List<Machinev2> machines ) {
		this.machines = machines;
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

