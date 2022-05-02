package pt.uminho.miei.ras.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "sports")
public class Sport {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(name = "name", length = 64, nullable = false, unique = true)
    private String name;

    @Column(name = "format", length = 64, nullable = false)
    private String format;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
