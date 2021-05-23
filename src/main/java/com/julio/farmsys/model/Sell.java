package com.julio.farmsys.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Sell {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double value;
    private Date date;
    @OneToOne
    @JoinColumn( name = "animaId" )
    private Animal animal;

}
