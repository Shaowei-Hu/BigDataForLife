package com.shaowei.bigdatalife.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Visitor.
 */
@Entity
@Table(name = "visitor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "visitor")
public class Visitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ip")
    private String ip;

    @Column(name = "browser")
    private String browser;

    @Column(name = "information")
    private String information;

    @Column(name = "arrive_date")
    private ZonedDateTime arriveDate;

    @Column(name = "leave_date")
    private ZonedDateTime leaveDate;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Visitor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public Visitor ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBrowser() {
        return browser;
    }

    public Visitor browser(String browser) {
        this.browser = browser;
        return this;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getInformation() {
        return information;
    }

    public Visitor information(String information) {
        this.information = information;
        return this;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public ZonedDateTime getArriveDate() {
        return arriveDate;
    }

    public Visitor arriveDate(ZonedDateTime arriveDate) {
        this.arriveDate = arriveDate;
        return this;
    }

    public void setArriveDate(ZonedDateTime arriveDate) {
        this.arriveDate = arriveDate;
    }

    public ZonedDateTime getLeaveDate() {
        return leaveDate;
    }

    public Visitor leaveDate(ZonedDateTime leaveDate) {
        this.leaveDate = leaveDate;
        return this;
    }

    public void setLeaveDate(ZonedDateTime leaveDate) {
        this.leaveDate = leaveDate;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Visitor visitor = (Visitor) o;
        if (visitor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), visitor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Visitor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ip='" + getIp() + "'" +
            ", browser='" + getBrowser() + "'" +
            ", information='" + getInformation() + "'" +
            ", arriveDate='" + getArriveDate() + "'" +
            ", leaveDate='" + getLeaveDate() + "'" +
            "}";
    }
}
