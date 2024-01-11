package com.nayef.plugin.idk;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public final class Idk extends JavaPlugin implements Listener {
    Idk plugin = this;
    @Override
    public void onEnable() {
      this.getServer().getPluginManager().registerEvents(this, this);
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Location location = player.getLocation().add(4, 0, 0);
        double random = new Random().nextDouble();
        double start = 0.3;
        double end = 0.8;

        if (e.getAction() == Action.LEFT_CLICK_AIR) {
            new BukkitRunnable() {
                Location loc = player.getLocation().clone();

                @Override
                public void run() {
                    loc.add(Vector.getRandom().normalize().setY(0));
                    loc.setY(player.getWorld().getHighestBlockYAt(loc));
                    if(player.getWorld().getHighestBlockAt(loc).getType().equals(Material.WATER)) {
                        cancel();
                    }
                    new BukkitRunnable() {
                        double t = 0;
                        double Ystepsize = start + (random * (end - start));
                        int counter = 0;

                        public void run() {
                            counter++;
                            t = t + Math.PI / 16;
                            double y = 0;

                            for (double r = 0; r <= 1; r += .09) {
                                double x = r * Math.cos(t);
                                y+= Ystepsize;
                                double z = r * Math.sin(t);
                                Vector v = new Vector(x, 0, z);
                                loc.add(v.getX(), y, v.getZ());

                                player.spawnParticle(Particle.REDSTONE, loc, 0, new Particle.DustOptions(Color.GRAY, 1.0F));
                                loc.subtract(v.getX(), y, v.getZ());
                            }
                            if (player.getWorld().getHighestBlockAt(loc).getType().equals(Material.WATER)) {
                                cancel();
                            }
                            if (t > Math.PI * 8) {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0, 1);
                }
            }.runTaskTimer(this, 0, 8L);


        }
    }

    }

