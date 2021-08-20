package database.Service;

import database.Dao.PhaseDao;
import database.Model.Phase;

import java.util.List;

public class PhaseService {
    private PhaseDao phaseDao;

    public PhaseService(){
        phaseDao = new PhaseDao();
    }

    public void addPhase(Phase phase){
        phaseDao.save(phase);
    }

    public List<Phase> getPhases(){
        return phaseDao.findAll();
    }
}
