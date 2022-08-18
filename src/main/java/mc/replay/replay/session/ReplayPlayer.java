package mc.replay.replay.session;

import lombok.Getter;
import mc.replay.utils.item.ItemBuilder;
import mc.replay.utils.item.skull.SkullBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class ReplayPlayer {

    private final Player player;
    private final ReplaySession session;

    private final Location startLocation;
    private final ItemStack[] contents;

    public ReplayPlayer(Player player, ReplaySession session) {
        this.player = player;
        this.session = session;

        this.startLocation = this.player.getLocation().clone();

        this.contents = this.player.getInventory().getContents();
    }

    public void restoreInventory() {
        if (this.player == null) return;
        this.player.getInventory().setContents(this.contents);
    }

    public void setReplayItems() {
        this.player.getInventory().clear();

        //        this.player.getInventory().setItem(0, new ItemBuilder(Material.COMPASS, "&aTeleporteer naar speler").build());
        this.player.getInventory().setItem(2, SkullBuilder.getSkullByURLBuilder("http://textures.minecraft.net/texture/c3e4b533e4ba2dff7c0fa90f67e8bef36428b6cb06c45262631b0b25db85b").displayName("&aReduce playback speed").build());
        this.player.getInventory().setItem(3, SkullBuilder.getSkullByURLBuilder("http://textures.minecraft.net/texture/c4e490e1658bfde4d4ef1ea7cd646c5353377905a1369b86ee966746ae25ca7").displayName("&a10s backward").build());
        this.updatePauseButton();
        this.player.getInventory().setItem(5, SkullBuilder.getSkullByURLBuilder("http://textures.minecraft.net/texture/98f293f294980d732f523321c34a4cdcc3e6f9e36c9320e150f1cce31aa5").displayName("&a10s forward").build());
        this.player.getInventory().setItem(6, SkullBuilder.getSkullByURLBuilder("http://textures.minecraft.net/texture/60b55f74681c68283a1c1ce51f1c83b52e2971c91ee34efcb598df3990a7e7").displayName("&aIncrease playback speed").build());
        this.player.getInventory().setHeldItemSlot(4);
    }

    private void updatePauseButton() {
        this.player.getInventory().setItem(4, new ItemBuilder(this.session.isPaused() ? Material.WHITE_DYE : Material.LIME_DYE, this.session.isPaused() ? "&aResume" : "&aPause").build());
    }

    public void clickButton(ItemStack stack) {
        if (stack.getItemMeta() == null) return;
        String displayName = stack.getItemMeta().getDisplayName();

        if (displayName.contains("Reduce playback speed")) {
            if (this.session.getSpeed() <= 0.25) return;
            double speed = this.session.getSpeed();
            this.session.setSpeed((speed <= 1) ? speed - 0.25 : speed - 1);
            return;
        }

        if (displayName.contains("10s backward")) {
            boolean isPaused = this.session.isPaused();
            this.session.setPaused(true);
            this.session.jumpBackwards(10);
            this.session.setPaused(isPaused);
            return;
        }

        if (displayName.contains("Resume") || displayName.contains("Pause")) {
            this.session.setPaused(!this.session.isPaused());
            this.updatePauseButton();
            return;
        }

        if (displayName.contains("10s forward")) {
            boolean isPaused = this.session.isPaused();
            this.session.setPaused(true);
            this.session.jumpForwards(10);
            this.session.setPaused(isPaused);
            return;
        }

        if (displayName.contains("Increase playback speed")) {
            if (this.session.getSpeed() >= 4) return;
            double speed = this.session.getSpeed();
            this.session.setSpeed((speed < 1) ? speed + 0.25 : speed + 1);
        }
    }
}
