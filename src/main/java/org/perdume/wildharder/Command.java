package org.perdume.wildharder;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    Wild_Harder main = Wild_Harder.getPlugin(Wild_Harder.class);
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)){
            return true;
        }
        Player player = (Player) commandSender;
        if (!player.isOp()){
            return true;
        }
        if(args[0].equalsIgnoreCase("getSpawn")){
            Player pl2 = null;
            try{
                pl2 = Bukkit.getPlayer(args[1]);
            }
            catch (Exception e){
                player.sendMessage("ERROR-PLAYERFIND");
            }
            Location locf = (Location) main.mn.getConfig().get("User." + pl2.getUniqueId().toString() + ".FirstSpawnLoc");
            Location locl = (Location) main.mn.getConfig().get("User." + pl2.getUniqueId().toString() + ".SpawnLoc");
            player.sendMessage("First: " + locf.toString());
            player.sendMessage("Final: " + locl.toString());
        }
        return true;
    }
}
