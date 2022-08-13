package ru.spb.idu.repository;

import org.springframework.stereotype.Repository;
import ru.spb.idu.domain.road.Road;

@Repository
public interface RoadRepository extends GeometricEntityRepository<Road> {
}
