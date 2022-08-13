package ru.spb.idu.repository;

import org.springframework.stereotype.Repository;
import ru.spb.idu.domain.building.Building;

@Repository
public interface BuildingRepository extends GeometricEntityRepository<Building> {
}
