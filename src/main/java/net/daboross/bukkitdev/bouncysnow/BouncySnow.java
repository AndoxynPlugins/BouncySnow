/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.bouncysnow;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/**
 *
 * @author daboross
 */
public class BouncySnow extends JavaPlugin implements Listener {

    private int snowBounce;

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        this.saveDefaultConfig();
        snowBounce = this.getConfig().getInt("snow-bounce");
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onFall(EntityDamageEvent ede) {
        if (ede.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Location location = ede.getEntity().getLocation();
            if (location.getY() > 79
                    && (location.getBlock().getType() == Material.SNOW_BLOCK
                    || location.add(0, -1, 0).getBlock().getType() == Material.SNOW_BLOCK)) {
                ede.setCancelled(true);
                ede.getEntity().setVelocity(new Vector(0, 20, 0));
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("reloadbouncysnow")) {
            reloadConfig();
            snowBounce = this.getConfig().getInt("snow-bounce");
            sender.sendMessage(ChatColor.GREEN + "BouncySnow factor is now " + ChatColor.RED + snowBounce + ChatColor.GREEN + "!");
        } else {
            sender.sendMessage("BouncySnow doesn't know about the command /" + cmd.getName());
        }
        return true;
    }
}
