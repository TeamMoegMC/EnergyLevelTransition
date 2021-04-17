package com.teammoeg.elt.mixin;

import com.teammoeg.elt.capability.ELTCapabilities;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class Shield {

    @Inject(at = @At("TAIL"), method = "hurtCurrentlyUsedShield")
    private void init(float damage, CallbackInfo ci) {
        PlayerEntity playerEntity = ((PlayerEntity) (Object) this);
        playerEntity.getCapability(ELTCapabilities.fightCapability).ifPresent((cap) -> {
            if (cap.getPhysicalStrength() < 1) {
                playerEntity.disableShield(true);
            } else {
                cap.decreasePhysicalStrength(playerEntity.getUseItem().getItem(), (int) damage);
            }

        });
    }
}

