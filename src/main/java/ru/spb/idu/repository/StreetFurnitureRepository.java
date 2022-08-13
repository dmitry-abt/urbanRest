package ru.spb.idu.repository;

import org.springframework.stereotype.Repository;
import ru.spb.idu.domain.sfurniture.StreetFurniture;

@Repository
public interface StreetFurnitureRepository extends GeometricEntityRepository<StreetFurniture> {
}
