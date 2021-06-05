package com.ipl.fantasy.persistence.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

public class Player {

    private Integer id;

    private String name;

    private String alias;

    private String teams;


}
