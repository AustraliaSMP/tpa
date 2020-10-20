package xyz.australiasmp.tpa;

import java.util.HashMap;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TPA extends JavaPlugin {
    HashMap<Player, Player> tpa = new HashMap<>();

    public void onEnable() {}

    public void onDisable() {}

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        if (label.equalsIgnoreCase("tpa")) {
            if (args.length == 1) {
                Player target = player.getServer().getPlayer(args[0]);
                if (target != null) {
                    this.tpa.put(target, player);
                    TextComponent acceptMessage = new TextComponent(ChatColor.GOLD + ChatColor.BOLD + "TP Request Sent By: " + player.getName() + " Click Here To " + ChatColor.GREEN + "Accept!");
                    acceptMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
                    acceptMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (
                            new ComponentBuilder(ChatColor.GREEN + "Click Here To Accept The Teleport")).create()));
                    target.spigot().sendMessage((BaseComponent)acceptMessage);
                    TextComponent denyMessage = new TextComponent(ChatColor.DARK_RED + "Click Here To: " + ChatColor.RED + "Deny!");
                    denyMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"));
                    denyMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (
                            new ComponentBuilder(ChatColor.RED + "Click Here To Deny The Teleport")).create()));
                    target.spigot().sendMessage((BaseComponent)denyMessage);
                    player.sendMessage(ChatColor.GREEN + ChatColor.BOLD + "You Have Sent A TP Request To: " + ChatColor.GOLD + target.getName());
                    return true;
                }
                return true;
            }
            return true;
        }
        if (label.equalsIgnoreCase("tpaccept") &&
                this.tpa.get(player) != null) {
            ((Player)this.tpa.get(player)).teleport((Entity)player);
            player.sendMessage(ChatColor.AQUA + "You Successfully" + ChatColor.GOLD + " Accepted " + ChatColor.AQUA + "The Teleportation!");
            this.tpa.put(player, null);
            return true;
        }
        if (label.equalsIgnoreCase("tpdeny") &&
                this.tpa.get(player) != null) {
            player.sendMessage(ChatColor.AQUA + "You Successfully" + ChatColor.GOLD + " Denied " + ChatColor.AQUA + "The Teleportation!");
            this.tpa.put(player, null);
            return true;
        }
        return false;
    }
}
