package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.lib.sound.impl.EntityLoopingSoundInterface;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.client.ClientMethodInteractionHandler;
import net.frozenblock.wilderwild.misc.interfaces.WilderEnderman;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderMan.class)
public final class EnderManMixin extends Monster implements WilderEnderman {

	@Unique
	private boolean wilderWild$canPlayLoopingSound = true;

	@Shadow
	private int lastStareSound;
	@Shadow @Final
	private static EntityDataAccessor<Boolean> DATA_CREEPY;

    private EnderManMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

	@Unique
	@Override
	public void wilderWild$setCanPlayLoopingSound(boolean bl) {
		this.wilderWild$canPlayLoopingSound = bl;
	}

	@Unique
	@Override
	public boolean wilderWild$getCanPlayLoopingSound() {
		return this.wilderWild$canPlayLoopingSound;
	}

	@Inject(method = "playStareSound", at = @At(value = "HEAD"), cancellable = true)
    public void wilderWild$playStareSound(CallbackInfo info) {
        //NOTE: This only runs on the client.
		if (WilderSharedConstants.CONFIG().movingStareSound()) {
			info.cancel();
			if (this.tickCount >= this.lastStareSound + 400) {
				this.lastStareSound = this.tickCount;
				if (!this.isSilent()) {
					ClientMethodInteractionHandler.playClientEnderManSound(EnderMan.class.cast(this));
					this.level.playLocalSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENDERMAN_STARE, this.getSoundSource(), 2.5F, 1.0F, false);
				}
			}
		}
    }

	@Inject(method = "onSyncedDataUpdated", at = @At("HEAD"))
	public void wilderWild$onSyncedDataUpdated(EntityDataAccessor<?> key, CallbackInfo info) {
		if (isCreepy()) {
			wilderWild$createAngerLoop();
		}
	}

	@Shadow
	public boolean isCreepy() {
		throw new AssertionError("Mixin injection failed - Wilder Wild EnderManMixin.");
	}

	@Unique
	@Override
	public void wilderWild$createAngerLoop() {
		if (WilderSharedConstants.CONFIG().angerLoopSound()) {
			EnderMan enderMan = EnderMan.class.cast(this);
			if (enderMan.level.isClientSide && this.wilderWild$canPlayLoopingSound) {
				((EntityLoopingSoundInterface) enderMan).addSound(Registry.SOUND_EVENT.getKey(RegisterSounds.ENTITY_ENDERMAN_ANGER_LOOP), SoundSource.HOSTILE, 1.0F, 0.9F, WilderSharedConstants.id("enderman_anger"));
				this.wilderWild$canPlayLoopingSound = false;
			}
		}
	}
}
