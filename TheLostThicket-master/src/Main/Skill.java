package Main;

import entity.Player;

public class Skill {
    public String name;
    public String description;

    public Skill(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void use(Player player) {
        // Default skill behavior (can be overridden for specific skills)
        System.out.println("Using skill: " + name);
    }
}
