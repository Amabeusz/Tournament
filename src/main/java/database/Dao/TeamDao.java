package database.Dao;

import database.Model.Team;

import java.util.List;

public class TeamDao extends DaoImpl implements Dao<Team> {
    @Override
    public void save(Team entity) {
        openCurrentSessionwithTransaction().save(entity);
        closeCurrentSessionwithTransaction();
    }

    @Override
    public void update(Team entity) {
        openCurrentSessionwithTransaction().update(entity);
        closeCurrentSessionwithTransaction();
    }

    @Override
    public List<Team> findAll() {
        List<Team> entities = openCurrentSessionwithTransaction().createQuery("from Team", Team.class).list();
        closeCurrentSessionwithTransaction();
        return entities;
    }
}
