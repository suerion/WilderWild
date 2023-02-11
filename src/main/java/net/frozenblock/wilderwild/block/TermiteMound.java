/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TermiteMound extends BaseEntityBlock {

    public TermiteMound(Properties settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TermiteMoundBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RegisterProperties.NATURAL, RegisterProperties.TERMITES_AWAKE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(RegisterProperties.NATURAL, false).setValue(RegisterProperties.TERMITES_AWAKE, false);
    }

	@Override
	public BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		boolean isSafe = isPosSafeForTermites(level, neighborPos, neighborState);
		if (isSafe != state.getValue(RegisterProperties.TERMITES_AWAKE)) {
			state.setValue(RegisterProperties.TERMITES_AWAKE, isSafe);
		}
		return state;
	}

	@Override
	public void onPlace(BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean isMoving) {
		level.scheduleTick(pos, this, 40);
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		boolean canAwaken = canTermitesWaken(level, pos);
		if (canAwaken != state.getValue(RegisterProperties.TERMITES_AWAKE)) {
			state.setValue(RegisterProperties.TERMITES_AWAKE, canAwaken);
		}
		level.scheduleTick(pos, this, 100);
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		level.scheduleTick(pos, this, 1);
	}

	public static boolean canTermitesWaken(@NotNull Level level, @NotNull BlockPos pos) {
		for (Direction direction : Direction.values()) {
			if (!isPosSafeForTermites(level, pos.relative(direction))) {
				return false;
			}
		}
		return !shouldTermitesSleep(level, getLightLevel(level, pos));
	}

	public static boolean isPosSafeForTermites(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		return isStateSafeForTermites(level.getBlockState(pos)) && level.getFluidState(pos).isEmpty();
	}

	public static boolean isPosSafeForTermites(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return isStateSafeForTermites(state) && level.getFluidState(pos).isEmpty();
	}

	public static boolean isStateSafeForTermites(@NotNull BlockState state) {
		return !state.is(WilderBlockTags.KILLS_TERMITE) && !(state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED));
	}

	public static boolean shouldTermitesSleep(Level level, int light) {
		return level.isNight() && light < 7;
	}

	public static int getLightLevel(Level level, BlockPos blockPos) {
		int finalLight = 0;
		for (Direction direction : Direction.values()) {
			BlockPos pos = blockPos.relative(direction);
			int skyLight = 0;
			int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
			if (level.isDay() && !level.isRaining()) {
				skyLight = level.getBrightness(LightLayer.SKY, pos);
			}
			finalLight = Math.max(finalLight, Math.max(skyLight, blockLight));
		}
		return finalLight;
	}

	@Override
    public RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return !level.isClientSide ? createTickerHelper(type, RegisterBlockEntities.TERMITE_MOUND, (worldx, pos, statex, blockEntity) -> blockEntity.tick(worldx, pos)) : null;
    }
}
