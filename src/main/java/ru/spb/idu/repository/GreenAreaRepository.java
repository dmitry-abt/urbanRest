package ru.spb.idu.repository;

import org.springframework.stereotype.Repository;
import ru.spb.idu.domain.garea.GreenArea;

@Repository
public interface GreenAreaRepository extends GeometricEntityRepository<GreenArea> {
}
