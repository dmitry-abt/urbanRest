package ru.spb.idu.domain.building;

import javax.persistence.*;

@Entity
@Table(name = "schools")
@PrimaryKeyJoinColumn(name = "id_public_buildings", referencedColumnName = "id_buildings")
public class School extends PublicBuilding {

    private Integer vacancies;

    public Integer getVacancies() {
        return vacancies;
    }

    public void setVacancies(Integer vacancies) {
        this.vacancies = vacancies;
    }

}
