package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class VehicleGroup {
    private long id;
    private String vin;
    private String make;
    private String aisIncentiveId;
    private  String aisVehicleGroupId;
    private String vehicleGroupName;
    private long vehicleGroupId;
    private int modelYear;
    private int marketingYear;
    private long regionId;
    private String hash;
    private String vehicleHints;
    private String exclusionHints;
    private LocalDateTime created;

    public VehicleGroup(long id, String vin, String make, String aisIncentiveId, String aisVehicleGroupId,
                        String vehicleGroupName, long vehicleGroupId, int modelYear, int marketingYear,
                        long regionId, String hash, String vehicleHints, String exclusionHints, LocalDateTime created) {
        this.id = id;
        this.vin = vin;
        this.make = make;
        this.aisIncentiveId = aisIncentiveId;
        this.aisVehicleGroupId = aisVehicleGroupId;
        this.vehicleGroupName = vehicleGroupName;
        this.vehicleGroupId = vehicleGroupId;
        this.modelYear = modelYear;
        this.marketingYear = marketingYear;
        this.regionId = regionId;
        this.hash = hash;
        this.vehicleHints = vehicleHints;
        this.exclusionHints = exclusionHints;
        this.created = created;
    }

    public VehicleGroup(long id, String vin, String make, String aisIncentiveId, String aisVehicleGroupId, String vehicleGroupName,
                        long vehicleGroupId, int modelYear, int marketingYear, long regionId, String hash,
                        String vehicleHints, String exclusionHints, Timestamp created) {

        this.id = id;
        this.vin = vin;
        this.make = make;
        this.aisIncentiveId = aisIncentiveId;
        this.aisVehicleGroupId = aisVehicleGroupId;
        this.vehicleGroupName = vehicleGroupName;
        this.vehicleGroupId = vehicleGroupId;
        this.modelYear = modelYear;
        this.marketingYear = marketingYear;
        this.regionId = regionId;
        this.hash = hash;
        this.vehicleHints = vehicleHints;
        this.exclusionHints = exclusionHints;

        if(created!=null)
            this.created = created.toLocalDateTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAisIncentiveId() {
        return aisIncentiveId;
    }

    public void setAisIncentiveId(String aisIncentiveId) {
        this.aisIncentiveId = aisIncentiveId;
    }

    public String getAisVehicleGroupId() {
        return aisVehicleGroupId;
    }

    public void setAisVehicleGroupId(String aisVehicleGroupId) {
        this.aisVehicleGroupId = aisVehicleGroupId;
    }

    public String getVehicleGroupName() {
        return vehicleGroupName;
    }

    public void setVehicleGroupName(String vehicleGroupName) {
        this.vehicleGroupName = vehicleGroupName;
    }

    public long getVehicleGroupId() {
        return vehicleGroupId;
    }

    public void setVehicleGroupId(long vehicleGroupId) {
        this.vehicleGroupId = vehicleGroupId;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public int getMarketingYear() {
        return marketingYear;
    }

    public void setMarketingYear(int marketingYear) {
        this.marketingYear = marketingYear;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getVehicleHints() {
        return vehicleHints;
    }

    public void setVehicleHints(String vehicleHints) {
        this.vehicleHints = vehicleHints;
    }

    public String getExclusionHints() {
        return exclusionHints;
    }

    public void setExclusionHints(String exclusionHints) {
        this.exclusionHints = exclusionHints;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Override
    public String toString() {
        return String.format("VehicleGroup{id=%d, aisIncentiveId='%s', aisVehicleGroupId='%s', vehicleGroupName='%s', vehicleGroupId='%d', modelYear=%d, " +
                "marketingYear=%d, regionId=%d, hash='%s', vehicleHints='%s', exclusionHints='%s', created=%s}", id, aisIncentiveId, aisVehicleGroupId, vehicleGroupName, vehicleGroupId, modelYear, marketingYear, regionId, hash, vehicleHints, exclusionHints, created);
    }
}

