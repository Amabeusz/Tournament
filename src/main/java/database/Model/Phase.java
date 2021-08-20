package database.Model;

import javax.persistence.*;

@Entity
@Table(name="`Phase`")
public class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="type")
    private String type;

    @Column(name="name")
    private String name;

    public Phase(){}

    public Phase(String type, String name){
        this.type = type;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
