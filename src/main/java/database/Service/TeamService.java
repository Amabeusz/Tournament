package database.Service;

import database.Dao.TeamDao;
import database.Model.Match;
import database.Model.Team;

import java.util.List;


public class TeamService {

    private TeamDao teamDao;

    public TeamService(){
        teamDao = new TeamDao();
    }

    public void addTeam(Team team){
        teamDao.save(team);
    }

    public void updateTeam(Team team){
        teamDao.update(team);
    }

    public List<Team> getTeams(){
        return teamDao.findAll();
    }

    public int getTeamsNumber(){
        return teamDao.findAll().size();
    }

}
