package ru.spb.idu.repository;

import org.springframework.stereotype.Repository;
import ru.spb.idu.domain.wtrbody.WaterBody;

@Repository
public interface WaterBodyRepository extends GeometricEntityRepository<WaterBody> {
}
