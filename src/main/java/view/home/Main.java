package view.home;

import database.Model.Phase;
import database.Service.MatchService;
import database.Service.PhaseService;
import database.Service.TeamService;

import java.util.Arrays;

public class Main {
    public static TeamService teamService = new TeamService();
    public static MatchService matchService = new MatchService();
    public static PhaseService phaseService = new PhaseService();

    public static void main(String[] args) {
        addPhases();
        Home.main(args);
    }

    private static void addPhases(){
        if(checkIfPhasesExist()){
            Arrays.stream(createPhases()).forEach(phase -> phaseService.addPhase(phase));
        }
    }

    private static boolean checkIfPhasesExist(){
        return phaseService.getPhases().size() != 23;
    }

    private static Phase[] createPhases(){
        return new Phase[]{
                new Phase("group", "A"),
                new Phase("group", "B"),
                new Phase("group", "C"),
                new Phase("group", "D"),
                new Phase("group", "E"),
                new Phase("group", "F"),
                new Phase("group", "G"),
                new Phase("group", "H"),
                new Phase("octofinal", "o1"),
                new Phase("octofinal", "o2"),
                new Phase("octofinal", "o3"),
                new Phase("octofinal", "o4"),
                new Phase("octofinal", "o5"),
                new Phase("octofinal", "o6"),
                new Phase("octofinal", "o7"),
                new Phase("octofinal", "o8"),
                new Phase("quarterfinal", "q1"),
                new Phase("quarterfinal", "q2"),
                new Phase("quarterfinal", "q3"),
                new Phase("quarterfinal", "q4"),
                new Phase("semifinal", "s1"),
                new Phase("semifinal", "s2"),
                new Phase("final", "f"),
        };
    }
}
