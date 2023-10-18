/*
 * Copyright 2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.entity.crab;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {

	@Unique @Nullable
	private static BlockPos wilderWild$mutableCrabPos;

	@WrapOperation(
		method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;set(III)Lnet/minecraft/core/BlockPos$MutableBlockPos;", ordinal = 0)
	)
	private static BlockPos.MutableBlockPos wilderWild$spawnCategoryForPosition(BlockPos.MutableBlockPos mutableBlockPos, int x, int y, int z, Operation<BlockPos.MutableBlockPos> operation, MobCategory category, ServerLevel level, ChunkAccess chunk, BlockPos pos, NaturalSpawner.SpawnPredicate filter, NaturalSpawner.AfterSpawnCallback callback) {
		if (category.getName().equals("wilderwildcrab")) {
			wilderWild$mutableCrabPos = wilderWild$findFloorPos(level, x, y, z, 11);
			return mutableBlockPos.set(wilderWild$mutableCrabPos.getX(), wilderWild$mutableCrabPos.getY(), wilderWild$mutableCrabPos.getZ());
		}
		wilderWild$mutableCrabPos = null;
		return operation.call(mutableBlockPos, x, y, z);
	}

	@WrapOperation(
		method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;moveTo(DDDFF)V", ordinal = 0)
	)
	private static void wilderWild$spawnCategoryForPosition(Mob mob, double x, double y, double z, float yRot, float xRot, Operation<Void> operation, MobCategory category, ServerLevel level, ChunkAccess chunk, BlockPos pos, NaturalSpawner.SpawnPredicate filter, NaturalSpawner.AfterSpawnCallback callback) {
		if (category.getName().equals("wilderwildcrab") && wilderWild$mutableCrabPos != null) {
			operation.call(mob, (double) wilderWild$mutableCrabPos.getX(), (double) wilderWild$mutableCrabPos.getY(), (double) wilderWild$mutableCrabPos.getZ(), yRot, xRot);
		} else {
			operation.call(mob, x, y, z, yRot, xRot);
		}
	}

	@Unique
	@NotNull
	private static BlockPos wilderWild$findFloorPos(ServerLevel level, int x, int startY, int z, int maxSearch) {
		BlockPos original = new BlockPos(x, startY, z);
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(x, startY, z);
		BlockPos.MutableBlockPos mutableBlockPosBelow = new BlockPos.MutableBlockPos(x, startY, z).move(Direction.DOWN);
		BlockState inBlockState = level.getBlockState(mutableBlockPos);
		Direction moveDirection = !(inBlockState.isAir() || inBlockState.is(Blocks.WATER)) ? Direction.UP : Direction.DOWN;
		for (int i = 0; i < maxSearch; i++) {
			inBlockState = level.getBlockState(mutableBlockPos);
			if (level.getBlockState(mutableBlockPosBelow).isSolid() && (inBlockState.isAir() || inBlockState.is(Blocks.WATER))) {
				return mutableBlockPos;
			}
			mutableBlockPos.move(moveDirection);
			mutableBlockPosBelow.set(mutableBlockPos).move(Direction.DOWN);
		}
		return original;
	}

}
