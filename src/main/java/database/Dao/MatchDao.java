package database.Dao;

import database.Model.Match;
import database.Model.Team;

import java.util.List;

public class MatchDao extends DaoImpl implements Dao<Match>{

    @Override
    public void save(Match entity) {
        openCurrentSessionwithTransaction().save(entity);
        closeCurrentSessionwithTransaction();
    }

    @Override
    public void update(Match entity) {
        openCurrentSessionwithTransaction().update(entity);
        closeCurrentSessionwithTransaction();
    }

    public Match getFirstNotPlayed(){
        Match entity = openCurrentSessionwithTransaction()
                .createQuery("from Match WHERE played = false AND team1 != null AND team2 != null ORDER BY id", Match.class)
                .setMaxResults(1)
                .uniqueResult();

        closeCurrentSessionwithTransaction();

        return entity;
    }

    @Override
    public List<Match> findAll() {
        List<Match> entities = openCurrentSessionwithTransaction().createQuery("from Match", Match.class).list();
        closeCurrentSessionwithTransaction();
        return entities;
    }
}
