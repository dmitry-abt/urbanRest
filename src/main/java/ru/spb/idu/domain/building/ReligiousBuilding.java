package ru.spb.idu.domain.building;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "religious_buildings")
@PrimaryKeyJoinColumn(name = "id_buildings", referencedColumnName = "id")
public class ReligiousBuilding extends Building {
}
