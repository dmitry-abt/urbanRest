package ru.spb.idu.repository;

import org.springframework.stereotype.Repository;
import ru.spb.idu.domain.block.Block;

@Repository
public interface BlockRepository extends GeometricEntityRepository<Block> {
}
