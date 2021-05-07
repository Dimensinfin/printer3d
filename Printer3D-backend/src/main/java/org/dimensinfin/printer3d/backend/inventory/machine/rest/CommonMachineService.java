package org.dimensinfin.printer3d.backend.inventory.machine.rest;/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.16.1
 */
public class CommonMachineService {
private CommonMachineService(){}
// - B U I L D E R
public static class Builder {
private final CommonMachineService onConstruction;
public Builder (){
this.onConstruction = new CommonMachineService();
}
public CommonMachineService build(){
return this.onConstruction;
}
}
}