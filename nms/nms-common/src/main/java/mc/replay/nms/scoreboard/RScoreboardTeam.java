package mc.replay.nms.scoreboard;

import mc.replay.packetlib.data.team.CollisionRule;
import mc.replay.packetlib.data.team.NameTagVisibility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Collection;
import java.util.HashSet;

public final class RScoreboardTeam {

    private final String name;
    private Component displayName = Component.empty();
    private Component prefix = Component.empty();
    private Component suffix = Component.empty();
    private NamedTextColor color = NamedTextColor.WHITE;
    private NameTagVisibility visibility = NameTagVisibility.ALWAYS;
    private CollisionRule collisionRule = CollisionRule.ALWAYS;
    private final Collection<String> entries = new HashSet<>();

    public RScoreboardTeam(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public Component displayName() {
        return this.displayName;
    }

    public Component prefix() {
        return this.prefix;
    }

    public Component suffix() {
        return this.suffix;
    }

    public NamedTextColor color() {
        return this.color;
    }

    public NameTagVisibility visibility() {
        return this.visibility;
    }

    public CollisionRule collisionRule() {
        return this.collisionRule;
    }

    public Collection<String> entries() {
        return this.entries;
    }

    public RScoreboardTeam addEntry(String entry) {
        this.entries.add(entry);
        return this;
    }

    public RScoreboardTeam removeEntry(String entry) {
        this.entries.remove(entry);
        return this;
    }

    public boolean isEntry(String entry) {
        return this.entries.contains(entry);
    }

    public RScoreboardTeam displayName(Component displayName) {
        this.displayName = displayName;
        return this;
    }

    public RScoreboardTeam prefix(Component prefix) {
        this.prefix = prefix;
        return this;
    }

    public RScoreboardTeam suffix(Component suffix) {
        this.suffix = suffix;
        return this;
    }

    public RScoreboardTeam color(NamedTextColor color) {
        this.color = color;
        return this;
    }

    public RScoreboardTeam visibility(NameTagVisibility visibility) {
        this.visibility = visibility;
        return this;
    }

    public RScoreboardTeam collisionRule(CollisionRule collisionRule) {
        this.collisionRule = collisionRule;
        return this;
    }
}