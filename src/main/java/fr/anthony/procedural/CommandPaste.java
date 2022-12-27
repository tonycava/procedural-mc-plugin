package fr.anthony.procedural;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jnbt.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class CommandPaste implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        player.sendMessage("Start pasting...");

        int pX = player.getLocation().getBlockX(), pY = player.getLocation().getBlockY(), pZ = player.getLocation().getBlockZ();

        try {
            File f = new File("/home/container/plugins/18097.schematic");
            FileInputStream fis = new FileInputStream(f);

            NBTInputStream nbt = new NBTInputStream(fis);
            CompoundTag backuptag = (CompoundTag) nbt.readTag();

            Map<String, Tag> tagCollection = backuptag.getValue();

            short width = (Short) Utils.getChildTag(tagCollection, "Width");
            short height = (Short) Utils.getChildTag(tagCollection, "Height");
            short length = (Short) Utils.getChildTag(tagCollection, "Length");

            byte[] blocks = (byte[]) Utils.getChildTag(tagCollection, "Blocks");
            byte[] data = (byte[]) Utils.getChildTag(tagCollection, "Data");

            List entities = (List) Utils.getChildTag(tagCollection, "Entities");
            List tileentities = (List) Utils.getChildTag(tagCollection, "TileEntities");

            nbt.close();
            fis.close();

            player.sendMessage("width: " + String.valueOf(width));
            player.sendMessage("height: " + String.valueOf(height));
            player.sendMessage("length: " + String.valueOf(length));

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    for (int z = 0; z < length; z++) {
                        final int index = x + z * width + y * width * length; // flatten (x,y,z) into a single dimension
                        final int blockId = blocks[index];
                        final int dataProp = data[index];

                        Location loc = new Location(player.getWorld(), pX + x, pY + y, pZ + z);

                        loc.getBlock().setType(Utils.getMaterialById(String.valueOf(Math.abs(blockId)), String.valueOf(dataProp)), false);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        player.sendMessage("Finish.");
        return true;
    }
}
