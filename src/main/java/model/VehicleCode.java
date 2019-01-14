package model;

public class VehicleCode {
    private long id;
    private long vehicleGroupId;
    private String acode;
    private String modelCode;
    private long styleID;

    public VehicleCode(long id, long vehicleGroupId, String acode, String modelCode, long styleID) {
        this.id = id;
        this.vehicleGroupId = vehicleGroupId;
        this.acode = acode;
        this.modelCode = modelCode;
        this.styleID = styleID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVehicleGroupId() {
        return vehicleGroupId;
    }

    public void setVehicleGroupId(long vehicleGroupId) {
        this.vehicleGroupId = vehicleGroupId;
    }

    public String getAcode() {
        return acode;
    }

    public void setAcode(String acode) {
        this.acode = acode;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public long getStyleID() {
        return styleID;
    }

    public void setStyleID(long styleID) {
        this.styleID = styleID;
    }

    @Override
    public String toString() {
        return String.format("VehicleCode{id=%d, vehicleGroupId=%d, acode='%s', modelCode='%s', styleID=%d}", id, vehicleGroupId, acode, modelCode, styleID);
    }
}
