package ru.spb.idu.domain.population;

import ru.spb.idu.domain.AbstractEntity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PopulationGroup extends AbstractEntity {
    private String description;

    private Double value;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PopulationGroup{" +
                "description='" + description + '\'' +
                ", value=" + value +
                '}';
    }

}