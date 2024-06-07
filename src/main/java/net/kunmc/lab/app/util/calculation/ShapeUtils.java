package net.kunmc.lab.app.util.calculation;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class ShapeUtils {

    public static Set<Block> getBlockSphereAround(Location location, int radius) {
        Set<Block> sphere = new HashSet<>();
        Block center = location.getBlock();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b = center.getRelative(x, y, z);
                    if (center.getLocation().distance(b.getLocation()) <= radius) {
                        sphere.add(b);
                    }
                }
            }
        }
        return sphere;
    }

    public static Set<Location> getLocationSphereAround(Location center, int radius) {
        Set<Location> sphere = new HashSet<>();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location target = center.clone().add(x, y, z);
                    if (center.distance(target) <= radius) {
                        sphere.add(target);
                    }
                }
            }
        }
        return sphere;
    }
}
