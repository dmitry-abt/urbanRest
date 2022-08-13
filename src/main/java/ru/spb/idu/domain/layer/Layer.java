package ru.spb.idu.domain.layer;

import ru.spb.idu.domain.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "layers")
public class Layer extends AbstractEntity {
    private String name;

    private String info;

    public Layer() {
    }

    public Layer(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Layer{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

}
