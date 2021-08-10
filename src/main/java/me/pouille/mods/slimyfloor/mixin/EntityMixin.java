package me.pouille.mods.slimyfloor.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Random;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.pouille.mods.slimyfloor.Server;

@Mixin(Entity.class)
public class EntityMixin {

    @Shadow
    @Final
    protected Random random;

    @Inject(method = "playStepSound(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
    protected final void playStepSoundMixin(final CallbackInfo info) {
        // Only 10% of footsteps might produce particles
        // TODO Make this a configurable property
        if (random.nextFloat() >= 0.10f)
            return;

        if ((Object) this instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
            if (player.world instanceof ServerWorld) {
                ServerWorld level = (ServerWorld) player.world;

                if (Boolean.TRUE.equals(Server.isInSlimeChunk(level, player))) {
                    Server.displaySlimeParticle(level, player, random);
                }

            }
        }
    }

}
