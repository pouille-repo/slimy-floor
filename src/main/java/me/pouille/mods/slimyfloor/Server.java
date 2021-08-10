package me.pouille.mods.slimyfloor;

import java.util.Optional;
import java.util.Random;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.ModContainer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkRandom;

public class Server implements DedicatedServerModInitializer {
    private static final Logger LOGGER = LogManager.getLogger("SlimyFloor");

    @Override
    public void onInitializeServer() {
        FabricLoader fabric = FabricLoader.getInstance();
        Optional<net.fabricmc.loader.api.ModContainer> mc = fabric.getModContainer("slimyfloor");
        String version = "?version?";
        if (mc.get() instanceof ModContainer) {
            ModContainer m = (ModContainer) mc.get();
            version = m.getInfo().getVersion().getFriendlyString();
        }
        LOGGER.info("SlimyFloor {} loaded", version);
    }

    public static Boolean isInSlimeChunk(final ServerWorld level, final PlayerEntity player) {
        if (player.getY() < 40 && level.getRegistryKey() == World.OVERWORLD) {
            ChunkPos cpos = new ChunkPos(player.getBlockPos());
            if (ChunkRandom.getSlimeRandom(cpos.x, cpos.z, level.getSeed(), 987234911l).nextInt(10) == 0) {
                return true;
            }
        }
        return false;
    }

    public static void displaySlimeParticle(final ServerWorld level, final ServerPlayerEntity player, final Random random) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        level.spawnParticles((ParticleEffect) ParticleTypes.ITEM_SLIME, x, y, z, 10, 0.2d, 0.0d, 0.2d, 0.0d);
        level.playSound((PlayerEntity) null, x, y, z, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.PLAYERS, 0.2f,
                0.1f /* random.nextFloat() * 0.2f */);
    }

}
