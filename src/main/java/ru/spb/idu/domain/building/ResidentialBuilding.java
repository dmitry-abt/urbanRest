package ru.spb.idu.domain.building;

import javax.persistence.*;

@Entity
@Table(name = "residential_buildings")
@PrimaryKeyJoinColumn(name = "id_buildings", referencedColumnName = "id")
public class ResidentialBuilding extends Building {
    @Column(name = "resident_number")
    private Integer residentNumber;

    @Column(name = "failure")
    private Boolean failure;

    public Integer getResidentNumber() {
        return residentNumber;
    }

    public void setResidentNumber(Integer residentNumber) {
        this.residentNumber = residentNumber;
    }

    public Boolean getFailure() { return failure; }

    public void setFailure(Boolean failure) { this.failure = failure; }
}
