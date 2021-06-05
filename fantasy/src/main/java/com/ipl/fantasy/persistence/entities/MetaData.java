package com.ipl.fantasy.persistence.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class MetaData {

    @Id
    @GeneratedValue
    private UUID id;

    private String dataVersion;

    private String revision;

    private String created;
}
