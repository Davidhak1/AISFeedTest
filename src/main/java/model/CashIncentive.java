package model;

public class CashIncentive {
    private long id;
    private int dealScenarioTypeId;
    private long programId;
    private String type;
    private long cashTotal;
    private String cashName;
    private long vehicleGroupId;

    public CashIncentive(long id, int dealScenarioTypeId, long programId, String type, long cashTotal, String cashName, long vehicleGroupId) {
        this.id = id;
        this.dealScenarioTypeId = dealScenarioTypeId;
        this.programId = programId;
        this.type = type;
        this.cashTotal = cashTotal;
        this.cashName = cashName;
        this.vehicleGroupId = vehicleGroupId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDealScenarioTypeId() {
        return dealScenarioTypeId;
    }

    public void setDealScenarioTypeId(int dealScenarioTypeId) {
        this.dealScenarioTypeId = dealScenarioTypeId;
    }

    public long getProgramId() {
        return programId;
    }

    public void setProgramId(long programId) {
        this.programId = programId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCashTotal() {
        return cashTotal;
    }

    public void setCashTotal(long cashTotal) {
        this.cashTotal = cashTotal;
    }

    public String getCashName() {
        return cashName;
    }

    public void setCashName(String cashName) {
        this.cashName = cashName;
    }

    public long getVehicleGroupId() {
        return vehicleGroupId;
    }

    public void setVehicleGroupId(long vehicleGroupId) {
        this.vehicleGroupId = vehicleGroupId;
    }

}
