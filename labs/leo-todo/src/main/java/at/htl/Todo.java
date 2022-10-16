package at.htl;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Todo extends PanacheEntity {

    @Column(unique = true)
    public String title;

    public boolean completed;

    @Column(name = "ordering")
    public int order;
}
