package fr.anthony.procedural;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jnbt.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CommandPaste implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        player.sendMessage("Start pasting...");

        int pX = player.getLocation().getBlockX(), pY = player.getLocation().getBlockY(), pZ = player.getLocation().getBlockZ();

        try {
            File f = new File("/home/container/plugins/18097.schematic");
            FileInputStream fis = new FileInputStream(f);

            NBTInputStream nbt = new NBTInputStream(fis);
            CompoundTag backuptag = (CompoundTag) nbt.readTag();

            var tagCollection = backuptag.getValue();

            short width = (Short) getChildTag(tagCollection, "Width", ShortTag.class).getValue();
            short height = (Short) getChildTag(tagCollection, "Height", ShortTag.class).getValue();
            short length = (Short) getChildTag(tagCollection, "Length", ShortTag.class).getValue();

            byte[] blocks = (byte[]) getChildTag(tagCollection, "Blocks", ByteArrayTag.class).getValue();
            byte[] data = (byte[]) getChildTag(tagCollection, "Data", ByteArrayTag.class).getValue();

            List entities = (List) getChildTag(tagCollection, "Entities", ListTag.class).getValue();
            List tileentities = (List) getChildTag(tagCollection, "TileEntities", ListTag.class).getValue();

            nbt.close();
            fis.close();

            player.sendMessage("width");
            player.sendMessage(String.valueOf(width));
            player.sendMessage("heigth");
            player.sendMessage(String.valueOf(height));
            player.sendMessage("length");

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    for (int z = 0; z < length; z++) {

                        final int index = x + z * width + y * width * length; // flatten (x,y,z) into a single dimension
                        final int blockId = blocks[index] & 0xFF;

                        Location loc = new Location(player.getWorld(), pX + width, pY + height, pZ + length);
                        loc.getBlock().setType(Material.DIAMOND_BLOCK);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        player.sendMessage("Finish.");
        return true;
    }

    private static Tag getChildTag(Map<String, Tag> items, String key, Class<? extends Tag> expected) {
        Tag tag = items.get(key);
        return tag;
    }
}
