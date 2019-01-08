package model;

public class IMDBVehicle {
    long id;
    int version;
    String uuid;
    String vin;
    String accountId;
    String incentiveFeed;
    String make;
    String type;
    int year;
    String checksum;
    String model;
    String modelCode;

    public IMDBVehicle(long id, int version, String uuid, String vin, String accountId, String incentiveFeed, String make, String type, int year, String checksum, String model, String modelCode) {
        this.id = id;
        this.version = version;
        this.uuid = uuid;
        this.vin = vin;
        this.accountId = accountId;
        this.incentiveFeed = incentiveFeed;
        this.make = make;
        this.type = type;
        this.year = year;
        this.checksum = checksum;
        this.model = model;
        this.modelCode = modelCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getIncentiveFeed() {
        return incentiveFeed;
    }

    public void setIncentiveFeed(String incentiveFeed) {
        this.incentiveFeed = incentiveFeed;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    @Override
    public String toString() {
        return "IMDBVehicle{" +
                "id=" + id +
                ", version=" + version +
                ", uuid='" + uuid + '\'' +
                ", vin='" + vin + '\'' +
                ", accountId='" + accountId + '\'' +
                ", incentiveFeed='" + incentiveFeed + '\'' +
                ", make='" + make + '\'' +
                ", type='" + type + '\'' +
                ", year=" + year +
                ", checksum='" + checksum + '\'' +
                ", model='" + model + '\'' +
                ", modelCode='" + modelCode + '\'' +
                '}';
    }
}
