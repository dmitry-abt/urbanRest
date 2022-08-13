package ru.spb.idu.service;

import ru.spb.idu.domain.layer.Layer;

import java.util.List;
import java.util.Optional;

public interface LayerService {
    Optional<Layer> findById(Long id);

    List<Layer> findByNameStartingWith(String startsWith, Integer maxRows);
}
