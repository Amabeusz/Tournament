package view.home.controller;

import database.Model.Match;
import database.Model.Phase;
import database.Model.Team;
import view.home.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchesGenerator {
    public static void generateMatches() {
        generateGroupsMatches();
        createFinalStageMatches();
    }

    static void generateGroupsMatches() {
        generateGroupMatches(GroupsTabController.getAGroupTeams());
        generateGroupMatches(GroupsTabController.getBGroupTeams());
        generateGroupMatches(GroupsTabController.getCGroupTeams());
        generateGroupMatches(GroupsTabController.getDGroupTeams());
        generateGroupMatches(GroupsTabController.getEGroupTeams());
        generateGroupMatches(GroupsTabController.getFGroupTeams());
        generateGroupMatches(GroupsTabController.getGGroupTeams());
        generateGroupMatches(GroupsTabController.getHGroupTeams());
    }

    static void generateGroupMatches(List<Team> teams) {
        List<Match> groupMatches = new ArrayList<>();
        Phase phase = teams.get(0).getPhase();

        groupMatches.add(new Match(teams.get(0), teams.get(1), phase));
        groupMatches.add(new Match(teams.get(0), teams.get(2), phase));
        groupMatches.add(new Match(teams.get(0), teams.get(3), phase));
        groupMatches.add(new Match(teams.get(1), teams.get(2), phase));
        groupMatches.add(new Match(teams.get(1), teams.get(3), phase));
        groupMatches.add(new Match(teams.get(2), teams.get(3), phase));

        groupMatches.forEach(match -> Main.matchService.addMatch(match));
    }

    static void createFinalStageMatches() {
        createMatchesOctofinals();
        createMatchesQuarterfinals();
        createMatchesSemifinals();
        createMatchFinal();
    }

    static void createMatchesOctofinals() {
        List<Phase> octofinalsPhase = Main.phaseService.getPhases().stream().filter(phase -> phase.getType().equals("octofinal")).collect(Collectors.toList());
        octofinalsPhase.forEach(octofinal -> Main.matchService.addMatch(new Match(null, null, octofinal)));
    }

    static void createMatchesQuarterfinals() {
        List<Phase> quarterfinalsPhase = Main.phaseService.getPhases().stream().filter(phase -> phase.getType().equals("quarterfinal")).collect(Collectors.toList());
        quarterfinalsPhase.forEach(quarterfinal -> Main.matchService.addMatch(new Match(null, null, quarterfinal)));
    }

    static void createMatchesSemifinals() {
        List<Phase> semifinalsPhase = Main.phaseService.getPhases().stream().filter(phase -> phase.getType().equals("semifinal")).collect(Collectors.toList());
        semifinalsPhase.forEach(semifinal -> Main.matchService.addMatch(new Match(null, null, semifinal)));
    }

    static void createMatchFinal() {
        Phase finalPhase = Main.phaseService.getPhases().stream().filter(phase -> phase.getType().equals("final")).findAny().orElse(null);
        Main.matchService.addMatch(new Match(null, null, finalPhase));
    }
}
