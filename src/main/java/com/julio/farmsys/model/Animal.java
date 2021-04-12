package com.julio.farmsys.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Animal {
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String specie;
    private Date bornDate;
    private Date acquisitionDate;
    private Double weight;
    private Double height;
    
    public Animal(Long id, String specie, Date bornDate, Date acquisitionDate, Double weight, Double height) {
        this.id = id;
        this.specie = specie;
        this.bornDate = bornDate;
        this.acquisitionDate = acquisitionDate;
        this.weight = weight;
        this.height = height;
    }

    public Animal(String specie, Date bornDate, Date acquisitionDate, Double weight, Double height) {
        this.specie = specie;
        this.bornDate = bornDate;
        this.acquisitionDate = acquisitionDate;
        this.weight = weight;
        this.height = height;
    }

    public Animal()
    {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    
}
