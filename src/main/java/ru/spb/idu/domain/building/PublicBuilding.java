package ru.spb.idu.domain.building;

import javax.persistence.*;

@Entity
@Table(name = "public_buildings")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "id_buildings", referencedColumnName = "id")
public class PublicBuilding extends Building {
}
