package model;

public class VehicleGroupMatchDetail {
    private long id;
    private long vehicleGroupId;
    private String vehicleElement;
    private String vehicleHint;
    private String valuesVehicleGroup;
    private String vehicleHintSourse;
    private String matchStatus;

    public VehicleGroupMatchDetail(long id, long vehicleGroupId, String vehicleElement, String vehicleHint, String valuesVehicleGroup, String vehicleHintSourse, String matchStatus) {
        this.id = id;
        this.vehicleGroupId = vehicleGroupId;
        this.vehicleElement = vehicleElement;
        this.vehicleHint = vehicleHint;
        this.valuesVehicleGroup = valuesVehicleGroup;
        this.vehicleHintSourse = vehicleHintSourse;
        this.matchStatus = matchStatus;
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

    public String getVehicleElement() {
        return vehicleElement;
    }

    public void setVehicleElement(String vehicleElement) {
        this.vehicleElement = vehicleElement;
    }

    public String getVehicleHint() {
        return vehicleHint;
    }

    public void setVehicleHint(String vehicleHint) {
        this.vehicleHint = vehicleHint;
    }

    public String getValuesVehicleGroup() {
        return valuesVehicleGroup;
    }

    public void setValuesVehicleGroup(String valuesVehicleGroup) {
        this.valuesVehicleGroup = valuesVehicleGroup;
    }

    public String getVehicleHintSourse() {
        return vehicleHintSourse;
    }

    public void setVehicleHintSourse(String vehicleHintSourse) {
        this.vehicleHintSourse = vehicleHintSourse;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    @Override
    public String toString() {
        return String.format("VehicleGroupMatchDetail{id=%d, vehicleGroupId=%d, vehicleElement='%s', vehicleHint='%s', valuesVehicleGroup='%s', vehicleHintSourse='%s', matchStatus='%s'}", id, vehicleGroupId, vehicleElement, vehicleHint, valuesVehicleGroup, vehicleHintSourse, matchStatus);
    }
}
