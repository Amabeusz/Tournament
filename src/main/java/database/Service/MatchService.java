package database.Service;

import database.Dao.MatchDao;
import database.Model.Match;

import java.util.List;

public class MatchService {

    private MatchDao matchDao;

    public MatchService(){
        matchDao = new MatchDao();
    }

    public void addMatch(Match match){
        matchDao.save(match);
    }

    public void updateMatch(Match match){
         matchDao.update(match);
    }

    public Match getMatchNotPlayed(){
        return matchDao.getFirstNotPlayed();
    }

    public List<Match> getMatches(){
        return matchDao.findAll();
    }
}
