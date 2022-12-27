package fr.anthony.procedural;

import org.bukkit.Material;
import org.jnbt.Tag;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    static HashMap<String, Material> hashMap = new HashMap<>() {{
        put("0-0", Material.AIR);
        put("1-0", Material.STONE);
        put("2-0", Material.GRASS_BLOCK);
        put("3-0", Material.DIRT);
        put("8-0", Material.WATER);
        put("9-0", Material.WATER);
        put("17-0", Material.OAK_LOG);
        put("18-0", Material.OAK_LEAVES);
        put("31-0", Material.DEAD_BUSH);
        put("31-1", Material.GRASS);
        put("31-2", Material.FERN);
        put("37-0", Material.DANDELION);
        put("38-0", Material.POPPY);
        put("48-0", Material.MOSSY_COBBLESTONE);
        put("98-0", Material.STONE_BRICKS);
        put("106-0", Material.VINE);
        put("111-0", Material.LILY_PAD);
    }};

    public static Material getMaterialById(String id, String data) {
        Material material = hashMap.getOrDefault(String.format("%s-%s", id, data), Material.DIAMOND_BLOCK);
        if (material == Material.DIAMOND_BLOCK) System.out.println(String.format("%s-%s", id, data));
        return material;
    }

    public static Object getChildTag(Map<String, Tag> items, String key) {
        return items.get(key).getValue();
    }
}
