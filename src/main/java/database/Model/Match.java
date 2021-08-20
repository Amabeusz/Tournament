package database.Model;

import javax.persistence.*;

@Entity
@Table(name="`Match`")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    @JoinColumn(name="`team1Id`")
    private Team team1;

    @OneToOne
    @JoinColumn(name="`team2Id`")
    private Team team2;

    @Column(name="`goalsScoredTeam1`")
    private int goalsScoredTeam1;

    @Column(name="`goalsScoredTeam2`")
    private int goalsScoredTeam2;

    @Column(name="`played`")
    private boolean played;

    @OneToOne
    @JoinColumn(name="`phase`")
    private Phase phase;

    public Match() {}

    public Match(Team team1, Team team2, Phase phase) {
        this.team1 = team1;
        this.team2 = team2;
        this.goalsScoredTeam1 = 0;
        this.goalsScoredTeam2 = 0;
        this.played = false;
        this.phase = phase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public int getGoalsScoredTeam1() {
        return goalsScoredTeam1;
    }

    public void setGoalsScoredTeam1(int goalsScoredTeam1) {
        this.goalsScoredTeam1 = goalsScoredTeam1;
    }

    public int getGoalsScoredTeam2() {
        return goalsScoredTeam2;
    }

    public void setGoalsScoredTeam2(int goalsScoredTeam2) {
        this.goalsScoredTeam2 = goalsScoredTeam2;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", team1=" + team1 +
                ", team2=" + team2 +
                ", goalsScoredTeam1=" + goalsScoredTeam1 +
                ", goalsScoredTeam2=" + goalsScoredTeam2 +
                ", played=" + played +
                ", phase=" + phase +
                '}';
    }
}
