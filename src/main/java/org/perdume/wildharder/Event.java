package org.perdume.wildharder;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Random;


public class Event implements Listener {

    Wild_Harder main = Wild_Harder.getPlugin(Wild_Harder.class);


    @EventHandler
    public void FirstJoin(PlayerJoinEvent e){
        if(main.mn.getConfig().get("User." + e.getPlayer().getUniqueId() + ".FirstSpawnLoc") == null){
            Location loc = null;
            while(loc == null){
                loc = RandomTP(e.getPlayer().getWorld());
            }
            e.getPlayer().teleport(loc);
            main.mn.getConfig().set("User." + e.getPlayer().getUniqueId() + ".FirstSpawnLoc", e.getPlayer().getLocation());
            main.mn.getConfig().set("User." + e.getPlayer().getUniqueId() + ".SpawnLoc", e.getPlayer().getLocation());
            main.mn.saveconfig();
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Random random = new Random();
        Location FirstLocation = (Location) main.mn.getConfig().get("User." + e.getPlayer().getUniqueId() + ".SpawnLoc");
        Location Loc = e.getPlayer().getWorld().getHighestBlockAt((int) (FirstLocation.getX() + random.nextInt(16)-8), (int) (FirstLocation.getZ() + random.nextInt(16)-8)).getLocation();
        int Radius = 0;
        while(!isSafeLocation(Loc)){
            Radius += 1;
            Loc = e.getPlayer().getWorld().getHighestBlockAt((int) (FirstLocation.getX() + random.nextInt(Radius)-Radius/2), (int) (FirstLocation.getZ() + random.nextInt(Radius)-Radius/2)).getLocation();
        }
        if(!e.isBedSpawn()&&!e.isAnchorSpawn()){
            e.setRespawnLocation(Loc);
            main.mn.getConfig().set("User." + e.getPlayer().getUniqueId() + ".SpawnLoc", e.getRespawnLocation());
            main.mn.saveconfig();
        }
        else{
            main.mn.getConfig().set("User." + e.getPlayer().getUniqueId() + ".SpawnLoc", e.getRespawnLocation());
            main.mn.saveconfig();
        }
    }

    private Location RandomTP(World w){
        int Randomx = (int) (Math.random() * 5000) - 2500;
        int Randomz = (int) (Math.random() * 5000) - 2500;
        Location loc = w.getHighestBlockAt(Randomx, Randomz).getLocation();
        if (loc.add(0, 1, 0).getBlock().getBlockData().getMaterial().equals(Material.AIR) && loc.getBlock().getBlockData().getMaterial().equals(Material.AIR)) {
            if(isSafeLocation(loc)){
                return loc;
            }
        }
        return null;
    }

    private boolean isSafeLocation(Location location) {
        try {
            Block feet = location.getBlock();
            if (!feet.getType().isTransparent() && !feet.getLocation().add(0, 1, 0).getBlock().getType().isTransparent()) {
                return false; // not transparent (will suffocate)
            }
            Block head = feet.getRelative(BlockFace.UP);
            if (!head.getType().isTransparent()) {
                return false; // not transparent (will suffocate)
            }
            Block ground = feet.getRelative(BlockFace.DOWN);
            // returns if the ground is solid or not.
            return ground.getType().isSolid();
        } catch (Exception ignored) {}
        return false;
    }


}
