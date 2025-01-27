package com.elmakers.mine.bukkit.entity;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;

public class EntityCreeperData extends EntityExtraData {
    private boolean powered;

    public EntityCreeperData(ConfigurationSection parameters) {
        powered = parameters.getBoolean("powered");
    }

    public EntityCreeperData(Entity entity) {
        if (entity instanceof Creeper) {
            powered = ((Creeper)entity).isPowered();
        }
    }

    @Override
    public void apply(Entity entity) {
        if (entity instanceof Creeper) {
            ((Creeper)entity).setPowered(powered);
        }
    }
}
