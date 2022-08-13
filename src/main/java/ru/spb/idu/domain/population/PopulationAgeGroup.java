package ru.spb.idu.domain.population;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "population_groups_age")
public class PopulationAgeGroup extends PopulationGroup {
    public PopulationAgeGroup() {
    }

}
