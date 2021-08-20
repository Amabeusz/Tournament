package database.Dao;

import database.Model.Phase;

import java.util.List;

public class PhaseDao extends DaoImpl implements Dao<Phase>{

    @Override
    public void save(Phase entity) {
        openCurrentSessionwithTransaction().save(entity);
        closeCurrentSessionwithTransaction();
    }

    @Override
    public void update(Phase entity) {

    }

    @Override
    public List<Phase> findAll() {
        List<Phase> entities = openCurrentSessionwithTransaction().createQuery("from Phase", Phase.class).list();
        closeCurrentSessionwithTransaction();

        return entities;
    }
}
