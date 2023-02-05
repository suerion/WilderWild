package net.frozenblock.wilderwild.mixin.worldgen.general;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.additions.feature.WilderTreeConfigured;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.MangroveTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MangroveTreeGrower.class, priority = 69420)
public class MangroveTreeGrowerMixin {

    @Inject(method = "getConfiguredFeature", at = @At("RETURN"), cancellable = true)
    public void wilderWild$getConfiguredFeature(RandomSource randomSource, boolean bl, CallbackInfoReturnable<Holder<? extends ConfiguredFeature<?, ?>>> info) {
		if (WilderSharedConstants.CONFIG().wildTrees()) {
			if (randomSource.nextFloat() < 0.1F) {
				info.setReturnValue(WilderTreeConfigured.NEW_SWAMP_TREE);
			}
		}
    }

}
