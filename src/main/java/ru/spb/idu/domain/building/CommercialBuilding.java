package ru.spb.idu.domain.building;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "commercial_buildings")
@PrimaryKeyJoinColumn(name = "id_buildings", referencedColumnName = "id")
public class CommercialBuilding extends Building {
}
