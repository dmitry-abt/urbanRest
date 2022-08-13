package ru.spb.idu.configuration;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.spatial4j.io.jackson.GeometryAsGeoJSONSerializer;
import org.locationtech.spatial4j.io.jackson.GeometryDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {
    @Bean
    public SimpleModule JtsModule() {
        SimpleModule jtsModule = new SimpleModule();
        jtsModule.addSerializer(Geometry.class, new GeometryAsGeoJSONSerializer());
        jtsModule.addDeserializer(Geometry.class, new GeometryDeserializer());
        return jtsModule;
    }

}

