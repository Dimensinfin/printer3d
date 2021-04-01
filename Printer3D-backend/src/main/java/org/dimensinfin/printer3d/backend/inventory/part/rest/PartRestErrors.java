package org.dimensinfin.printer3d.backend.inventory.part.rest;public class PartRestErrors {
private PartRestErrors(){}
// - B U I L D E R
public static class Builder {
private final PartRestErrors onConstruction;
public Builder (){
this.onConstruction = new PartRestErrors();
}
public PartRestErrors build(){
return this.onConstruction;
}
}
}