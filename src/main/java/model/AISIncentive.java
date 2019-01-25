package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AISIncentive {
    private long id;
    private String accountId;
    private String feedRunId;
    private String zipCode;
    private String make;
    private LocalDateTime created;

    public AISIncentive(long id, String accountId, String feedRunId, String zipCode, String make, LocalDateTime created) {
        this.id = id;
        this.accountId = accountId;
        this.feedRunId = feedRunId;
        this.zipCode = zipCode;
        this.make = make;
        this.created = created;
    }

    public AISIncentive(long id, String accountId, String feedRunId, String zipCode, String make, Timestamp created) {
        this.id = id;
        this.accountId = accountId;
        this.feedRunId = feedRunId;
        this.zipCode = zipCode;
        this.make = make;

        if(created!=null)
        {
            this.created = created.toLocalDateTime();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFeedRunId() {
        return feedRunId;
    }

    public void setFeedRunId(String feedRunId) {
        this.feedRunId = feedRunId;
    }

    public String getzipCode() {
        return zipCode;
    }

    public void setzipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return String.format("AISIncentive{id=%d, accountId='%s', feedRunId='%s', zipCode='%s', make='%s', created=%s}", id, accountId, feedRunId, zipCode, make, created);
    }
}
