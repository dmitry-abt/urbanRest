package ru.spb.idu.domain.sfurniture;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "playground")
@PrimaryKeyJoinColumn(name = "id_street_furniture", referencedColumnName = "id")
public class Playground extends StreetFurniture {
}
