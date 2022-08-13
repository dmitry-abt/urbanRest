package ru.spb.idu.repository;

import org.springframework.stereotype.Repository;
import ru.spb.idu.domain.carroad.CarRoad;

@Repository
public interface CarRoadRepository extends GeometricEntityRepository<CarRoad> {
}
