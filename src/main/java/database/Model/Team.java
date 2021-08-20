package database.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import view.home.Main;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name="`Team`")
public class Team {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name="points")
    private int points;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="image")
    private byte[] image;

    @OneToOne
    @JoinColumn(name="`phase`")
    private Phase phase;

    @Column(name="`goalsScored`")
    private int goalsScored;

    @Column(name="`goalsLost`")
    private int goalsLost;


    public Team(){}

    public Team(String name, String descripion, byte[] image) {
        this.name = name == "" ? setDefaultName() : name;
        this.description = descripion;
        this.image = image;
        this.points = 0;
        this.phase = null;
        this.goalsScored = 0;
        this.goalsLost = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public final StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String setDefaultName(){
        int teamNumber = Main.teamService.getTeamsNumber() +1;
        name = "Drużyna " + ((teamNumber > 9) ? teamNumber : "0" + teamNumber);

        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalsLost() {
        return goalsLost;
    }

    public void setGoalsLost(int goalsLost) {
        this.goalsLost = goalsLost;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", points=" + points +
                ", name='" + name + '\'' +
                ", descripion='" + description + '\'' +
                ", image=" + Arrays.toString(image) +
                ", phase=" + phase +
                '}';
    }
}
