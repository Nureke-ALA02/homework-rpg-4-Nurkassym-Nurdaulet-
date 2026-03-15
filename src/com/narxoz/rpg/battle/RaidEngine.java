package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;
import java.util.Random;
public class RaidEngine {
    private Random random = new Random(1L);
    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        if (teamA == null || teamB == null || teamASkill == null || teamBSkill == null) {
            throw new IllegalArgumentException("Raid inputs cannot be null");
        }
        RaidResult result = new RaidResult();
        int rounds = 0;
        int maxRounds = 50;
        result.addLine("Raid started: " + teamA.getName() + " vs " + teamB.getName());
        while (teamA.isAlive() && teamB.isAlive() && rounds < maxRounds) {
            rounds++;
            result.addLine("Round " + rounds);

            teamASkill.cast(teamB);
            result.addLine(teamA.getName() + " casts " + teamASkill.getSkillName());

            if (!teamB.isAlive()) {
                break;
            }

            teamBSkill.cast(teamA);
            result.addLine(teamB.getName() + " casts " + teamBSkill.getSkillName());

            boolean critA = random.nextInt(100) < 10;
            boolean critB = random.nextInt(100) < 10;
            if (critA) {
                result.addLine("Critical strike by " + teamA.getName());
            }
            if (critB) {
                result.addLine("Critical strike by " + teamB.getName());
            }
        }
        result.setRounds(rounds);

        if (teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner(teamA.getName());
        } else if (teamB.isAlive() && !teamA.isAlive()) {
            result.setWinner(teamB.getName());
        } else {
            result.setWinner("Draw");
        }
        result.addLine("Winner: " + result.getWinner());
        return result;
    }
}
