package com.shaowei.bigdatalife.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Simulation.
 */
@Entity
@Table(name = "simulation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "simulation")
public class Simulation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "tax_level")
    private String taxLevel;

    @Column(name = "jhi_condition")
    private String condition;

    @Column(name = "intention")
    private String intention;

    @Column(name = "ip")
    private String ip;

    @Column(name = "information")
    private String information;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

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

    public Simulation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Simulation email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Simulation telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTaxLevel() {
        return taxLevel;
    }

    public Simulation taxLevel(String taxLevel) {
        this.taxLevel = taxLevel;
        return this;
    }

    public void setTaxLevel(String taxLevel) {
        this.taxLevel = taxLevel;
    }

    public String getCondition() {
        return condition;
    }

    public Simulation condition(String condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIntention() {
        return intention;
    }

    public Simulation intention(String intention) {
        this.intention = intention;
        return this;
    }

    public void setIntention(String intention) {
        this.intention = intention;
    }

    public String getIp() {
        return ip;
    }

    public Simulation ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getInformation() {
        return information;
    }

    public Simulation information(String information) {
        this.information = information;
        return this;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Simulation date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
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
        Simulation simulation = (Simulation) o;
        if (simulation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), simulation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Simulation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", taxLevel='" + getTaxLevel() + "'" +
            ", condition='" + getCondition() + "'" +
            ", intention='" + getIntention() + "'" +
            ", ip='" + getIp() + "'" +
            ", information='" + getInformation() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
