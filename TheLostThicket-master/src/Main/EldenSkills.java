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
        int manaCost = getManaCost(skillNumber);

        // Check if the player has enough mana to use the skill
        if (player.mana < manaCost) {
            System.out.println("Not enough mana to use this skill!");
            return; // Exit the method if the player doesn't have enough mana
        }

        // Deduct mana cost
        player.mana -= manaCost;

        // Apply skill effects
        switch (skillNumber) {
            case 1:
                player.hp += 20; // Restore 20 HP
                player.hp = Math.min(player.hp, player.getMaxHp()); // Ensure HP doesn't exceed max
                System.out.println("Healing Bloom used! HP restored by 20. (Mana Cost: " + manaCost + ")");
                break;
            case 2:
                activateStealth(player, 10); // Activate stealth for 10 seconds
                System.out.println("Silent Steps used! Player is now stealthy. (Mana Cost: " + manaCost + ")");
                break;
            case 3:
                System.out.println("Puzzle Sense used! One incorrect answer eliminated. (Mana Cost: " + manaCost + ")");
                break;
            default:
                System.out.println("Unknown skill used.");
                break;
        }

        // Ensure HP and Mana are within valid ranges
        player.hp = Math.max(player.hp, 0); // Ensure HP doesn't go below 0
        player.mana = Math.max(player.mana, 0); // Ensure Mana doesn't go below 0

        // Show character stats
        showCharacterStats(player);
    }

    private int getManaCost(int skillNumber) {
        switch (skillNumber) {
            case 1: return 15; // Healing Bloom
            case 2: return 15; // Silent Steps
            case 3: return 20; // Puzzle Sense
            default: return 0; // Unknown skill
        }
    }

    public static void showCharacterStats(Player player) {
        System.out.println("HP: " + player.hp);
        System.out.println("Mana: " + player.mana);
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