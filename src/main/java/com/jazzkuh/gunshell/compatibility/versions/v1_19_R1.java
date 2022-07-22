package com.jazzkuh.gunshell.compatibility.versions;

import com.jazzkuh.gunshell.api.objects.GunshellRayTraceResult;
import com.jazzkuh.gunshell.compatibility.CompatibilityLayer;
import org.bukkit.FluidCollisionMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import java.util.Optional;

public class v1_19_R1 implements CompatibilityLayer {
    @Override
    public GunshellRayTraceResult performRayTrace(Player player, int range) {
        RayTraceResult result = player.getWorld()
                .rayTrace(player.getEyeLocation(), player.getLocation().getDirection(), range, FluidCollisionMode.NEVER, true, 0.2, null);
        if (result == null) {
            return new GunshellRayTraceResult(Optional.empty(), Optional.empty(), false);
        }

        if (result.getHitBlock() != null) {
            return new GunshellRayTraceResult(Optional.empty(), Optional.of(result.getHitBlock()), false);
        }

        if (result.getHitEntity() == null) {
            return new GunshellRayTraceResult(Optional.empty(), Optional.empty(), false);
        }

        Entity entity = result.getHitEntity();
        if (!(entity instanceof LivingEntity) || entity instanceof ArmorStand) {
            return new GunshellRayTraceResult(Optional.empty(), Optional.empty(), false);
        }
        boolean isHeadshot = (result.getHitPosition().getY() - entity.getLocation().getY()) > 1.35;
        LivingEntity livingEntity = (LivingEntity) entity;
        return new GunshellRayTraceResult(Optional.of(livingEntity), Optional.empty(), isHeadshot);
    }

    @Override
    public String getRayTraceResult(Player player, int range) {
        RayTraceResult result = player.getWorld()
                .rayTrace(player.getEyeLocation(), player.getLocation().getDirection(), range, FluidCollisionMode.NEVER, true, 0.2, null);
        return result != null ? result.toString() : "No result found";
    }
}
