package Main;

import entity.Player;
import java.util.Timer;
import java.util.TimerTask;

public class EldenSkills extends Skill {
    int skillNumber;
    private Timer stealthTimer; // Timer for stealth mode

    public EldenSkills(int skillNumber) {
        super(getSkillName(skillNumber), getSkillDescription(skillNumber));
        this.skillNumber = skillNumber;
    }

    private static String getSkillName(int skillNumber) {
        switch (skillNumber) {
            case 1: return "Healing Bloom";
            case 2: return "Silent Steps";
            case 3: return "Puzzle Sense";
            default: return "Unknown Skill";
        }
    }

    private static String getSkillDescription(int skillNumber) {
        switch (skillNumber) {
            case 1: return "Summons a healing flower that restores her health.";
            case 2: return "Moves quietly to avoid certain dangers.";
            case 3: return "Eliminates one incorrect answer from a quiz question.";
            default: return "No description available.";
        }
    }

    @Override
    public void use(Player player) {
        switch (skillNumber) {
            case 1:
                player.hp += 20; // Restore 20 HP
                System.out.println("Healing Bloom used! HP restored by 20.");
                break;
            case 2:
                activateStealth(player, 10); // Activate stealth for 10 seconds
                System.out.println("Silent Steps used! Player is now stealthy.");
                break;
            case 3:
                System.out.println("Puzzle Sense used! One incorrect answer eliminated.");
                break;
            default:
                System.out.println("Unknown skill used.");
                break;
        }
    }

    private void activateStealth(Player player, int duration) {
        player.isStealth = true;
        player.alpha = 0.5f; // Set opacity to 50%

        // Reset stealth after the duration
        if (stealthTimer != null) {
            stealthTimer.cancel();
        }
        stealthTimer = new Timer();
        stealthTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                player.isStealth = false;
                player.alpha = 1.0f; // Reset opacity to 100%
                System.out.println("Stealth mode ended.");
            }
        }, duration * 1000); // Duration in milliseconds
    }
}