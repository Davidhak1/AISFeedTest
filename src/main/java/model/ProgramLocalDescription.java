package model;

import resources.Utils;


import java.sql.Clob;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ProgramLocalDescription {
    private long id;
    private String local;
    private long programLocalId;
    private String consumer;
    private String dealer;
    private String shortTitle;
    private String title;
    private String taxStatus;
    private String priceBase;
    private LocalDateTime created;

    public ProgramLocalDescription(long id, String local, long programLocalId, String consumer, String dealer, String shortTitle,
                                   String title, String taxStatus, String priceBase, LocalDateTime created) {
        this.id = id;
        this.local = local;
        this.programLocalId = programLocalId;
        this.consumer = consumer;
        this.dealer = dealer;
        this.shortTitle = shortTitle;
        this.title = title;
        this.taxStatus = taxStatus;
        this.priceBase = priceBase;
        this.created = created;
    }

    public ProgramLocalDescription(long id, String local, long programLocalId, String consumer, String dealer, String shortTitle,
                                   String title, String taxStatus, String priceBase, Timestamp created) {
        this.id = id;
        this.local = local;
        this.programLocalId = programLocalId;
        this.consumer = consumer;
        this.dealer = dealer;
        this.shortTitle = shortTitle;
        this.title = title;
        this.taxStatus = taxStatus;
        this.priceBase = priceBase;
        if(created!=null) {
            this.created = created.toLocalDateTime();
        }
    }

    public ProgramLocalDescription(long id, String local, long programLocalId, String consumer, String dealer, String shortTitle,
                                   String title, String taxStatus, String priceBase) {
        this.id = id;
        this.local = local;
        this.programLocalId = programLocalId;
        this.consumer = consumer;
        this.dealer = dealer;
        this.shortTitle = shortTitle;
        this.title = title;
        this.taxStatus = taxStatus;
        this.priceBase = priceBase;
    }

    public ProgramLocalDescription(long id, String local, long programLocalId, String consumer, Clob dealer, String shortTitle,
                                   String title, String taxStatus, String priceBase) {
        this.id = id;
        this.local = local;
        this.programLocalId = programLocalId;
        this.consumer = consumer;
        this.dealer = Utils.clobToString(dealer);
        this.shortTitle = shortTitle;
        this.title = title;
        this.taxStatus = taxStatus;
        this.priceBase = priceBase;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public long getProgramLocalId() {
        return programLocalId;
    }

    public void setProgramLocalId(long programLocalId) {
        this.programLocalId = programLocalId;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    public String getPriceBase() {
        return priceBase;
    }

    public void setPriceBase(String priceBase) {
        this.priceBase = priceBase;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }


}
