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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * When the machine has a running job this record reflects the **Part** being built and the timing information about when the job was submitted and
 * the time left to completion of the job. Date references are kept local to the backs so there is no date formaf conversion on the api interchange.
 */
@ApiModel(description = "When the machine has a running job this record reflects the **Part** being built and the timing information about when the job was submitted and the time left to completion of the job. Date references are kept local to the backs so there is no date formaf conversion on the api interchange. ")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-06-05T10:30:15.118123+02:00[Europe/Madrid]")
public class BuildRecord {
	/**
	 * This is a virtual api field that will reflect the state of the Machine so the front end will not have to relay on any logic to detect idle or
	 * running machines. * **IDLE** - The Machine has no job running. * **RUNNING** - There is a Part being built. Once this job completes the Machine
	 * continues on this state until the next request where the record is updated and the stocks updated.
	 */
	@JsonAdapter(StateEnum.Adapter.class)
	public enum StateEnum {
		IDLE( "IDLE" ),
		RUNNING( "RUNNING" );

		public static StateEnum fromValue( String text ) {
			for (StateEnum b : StateEnum.values()) {
				if (String.valueOf( b.value ).equals( text )) {
					return b;
				}
			}
			return null;
		}

		private String value;

		// - C O N S T R U C T O R S
		StateEnum( String value ) {
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

		public static class Adapter extends TypeAdapter<StateEnum> {
			@Override
			public void write( final JsonWriter jsonWriter, final StateEnum enumeration ) throws IOException {
				jsonWriter.value( enumeration.getValue() );
			}

			@Override
			public StateEnum read( final JsonReader jsonReader ) throws IOException {
				String value = jsonReader.nextString();
				return StateEnum.fromValue( String.valueOf( value ) );
			}
		}
	}

	public static final String SERIALIZED_NAME_STATE = "state";
	public static final String SERIALIZED_NAME_PART = "part";
	public static final String SERIALIZED_NAME_PART_COPIES = "partCopies";
	public static final String SERIALIZED_NAME_JOB_INSTALLMENT_DATE = "jobInstallmentDate";
	public static final String SERIALIZED_NAME_REMAINING_TIME = "remainingTime";
	@SerializedName(SERIALIZED_NAME_STATE)
	private StateEnum state = null;
	@SerializedName(SERIALIZED_NAME_PART)
	private Part part = null;
	@SerializedName(SERIALIZED_NAME_PART_COPIES)
	private Integer partCopies = 1;
	@SerializedName(SERIALIZED_NAME_JOB_INSTALLMENT_DATE)
	private LocalDateTime jobInstallmentDate = null;

	// - G E T T E R S   &   S E T T E R S

	/**
	 * The date and time when the job was stared on the machine. The Part information says the time it is expected that the machine will be occupied
	 * by this job. If the current time is greater that this point it is expected that the job has finished. If the machine is IDLE then this field
	 * contains **null**.
	 *
	 * @return jobInstallmentDate
	 **/
	@ApiModelProperty(value = "The date and time when the job was stared on the machine. The Part information says the time it is expected that the machine will be occupied by this job. If the current time is greater that this point it is expected that the job has finished. If the machine is IDLE then this field contains **null**. ")
	public LocalDateTime getJobInstallmentDate() {
		return jobInstallmentDate;
	}

	public void setJobInstallmentDate( LocalDateTime jobInstallmentDate ) {
		this.jobInstallmentDate = jobInstallmentDate;
	}

	/**
	 * The part reference that is being built. If the Machine is IDLE then this field contains **null**.
	 *
	 * @return part
	 **/
	@ApiModelProperty(value = "The part reference that is being built. If the Machine is IDLE then this field contains **null**. ")
	public Part getPart() {
		return part;
	}

	public void setPart( Part part ) {
		this.part = part;
		if (null == part) this.state = StateEnum.IDLE;
		else this.state = StateEnum.RUNNING;
	}

	/**
	 * The is the number of exact identical parts are being built at the same time on a single printer job.
	 * minimum: 1
	 *
	 * @return partCopies
	 **/
	@ApiModelProperty(example = "1", value = "The is the number of exact identical parts are being built at the same time on a single printer job. ")
	public Integer getPartCopies() {
		return partCopies;
	}

	public void setPartCopies( Integer partCopies ) {
		this.partCopies = partCopies;
	}

	/**
	 * Represents the number of minutes left between the **jobInstallmentDate** and the current date. If this number is 0 or negative then the job is
	 * completed. Detecting this event will clear the Machine state to IDLE and update the stocks.
	 * minimum: 0
	 *
	 * @return remainingTime
	 **/
	@ApiModelProperty(example = "18", value = "Represents the number of minutes left between the **jobInstallmentDate** and the current date. If this number is 0 or negative then the job is completed. Detecting this event will clear the Machine state to IDLE and update the stocks. ")
	//	public Integer getRemainingTime() {
	//		return remainingTime;
	//	}
	@SerializedName(SERIALIZED_NAME_REMAINING_TIME)
	public int getRemainingTime() {
		if (null == this.jobInstallmentDate) return 0;
		final long elapsed = this.getJobInstallmentDate().until( LocalDateTime.now(), ChronoUnit.MINUTES );
		if ((this.part.getBuildTime() - elapsed) < 0) return 0;
		else return this.part.getBuildTime() - (int) elapsed;
	}

	/**
	 * This is a virtual api field that will reflect the state of the Machine so the front end will not have to relay on any logic to detect idle or
	 * running machines. * **IDLE** - The Machine has no job running. * **RUNNING** - There is a Part being built. Once this job completes the Machine
	 * continues on this state until the next request where the record is updated and the stocks updated.
	 *
	 * @return state
	 **/
	@ApiModelProperty(required = true, value = "This is a virtual api field that will reflect the state of the Machine so the front end will not have to relay on any logic to detect idle or running machines. * **IDLE** - The Machine has no job running. * **RUNNING** - There is a Part being built. Once this job completes the Machine continues on this state until the next request where the record is updated and the stocks updated. ")
	public StateEnum getState() {
		return state;
	}

	//	public void setRemainingTime( Integer remainingTime ) {
	//		this.remainingTime = remainingTime;
	//	}

	public void setState( StateEnum state ) {
		this.state = state;
	}

	public void clearJob() {
		this.part = null;
		this.jobInstallmentDate = null;
		this.state = StateEnum.IDLE;
		this.partCopies = 1;
	}

	@Override
	public int hashCode() {
		return Objects.hash( state, part, partCopies, jobInstallmentDate );
	}

	@Override
	public boolean equals( Object o ) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BuildRecord buildRecord = (BuildRecord) o;
		return Objects.equals( this.state, buildRecord.state ) &&
				Objects.equals( this.part, buildRecord.part ) &&
				Objects.equals( this.partCopies, buildRecord.partCopies ) &&
				Objects.equals( this.jobInstallmentDate, buildRecord.jobInstallmentDate );
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "class BuildRecord {\n" );

		sb.append( "    state: " ).append( toIndentedString( state ) ).append( "\n" );
		sb.append( "    part: " ).append( toIndentedString( part ) ).append( "\n" );
		sb.append( "    partCopies: " ).append( toIndentedString( partCopies ) ).append( "\n" );
		sb.append( "    jobInstallmentDate: " ).append( toIndentedString( jobInstallmentDate ) ).append( "\n" );
		sb.append( "    remainingTime: " ).append( toIndentedString( this.getRemainingTime() ) ).append( "\n" );
		sb.append( "}" );
		return sb.toString();
	}

	public BuildRecord jobInstallmentDate( LocalDateTime jobInstallmentDate ) {
		this.jobInstallmentDate = jobInstallmentDate;
		return this;
	}

	public BuildRecord part( Part part ) {
		this.part = part;
		return this;
	}

	public BuildRecord partCopies( Integer partCopies ) {
		this.partCopies = partCopies;
		return this;
	}

	//	public BuildRecord remainingTime( Integer remainingTime ) {
	//		this.remainingTime = remainingTime;
	//		return this;
	//	}

	public BuildRecord state( StateEnum state ) {
		this.state = state;
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

