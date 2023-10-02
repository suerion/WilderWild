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

package net.frozenblock.wilderwild.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.wilderwild.config.defaults.DefaultMiscConfig;
import static net.frozenblock.wilderwild.WilderSharedConstants.MOD_ID;
import static net.frozenblock.wilderwild.WilderWildConfigUtilsKt.configPath;
import static net.frozenblock.wilderwild.WilderWildConfigUtilsKt.jsonConfig;

public final class MiscConfig {

	private static final Config<MiscConfig> INSTANCE = ConfigRegistry.register(
		jsonConfig(
			MOD_ID,
			MiscConfig.class,
			configPath("misc", true),
			JsonType.JSON5
		)
	);

	public boolean cloudMovement = DefaultMiscConfig.CLOUD_MOVEMENT;

	public int particleWindMovement = DefaultMiscConfig.PARTICLE_WIND_MOVEMENT;

	@CollapsibleObject
	public BiomeAmbienceConfig biomeAmbience = new BiomeAmbienceConfig();

	@CollapsibleObject
	public BiomeMusicConfig biomeMusic = new BiomeMusicConfig();

	public static MiscConfig get() {
		return INSTANCE.config();
	}

	public static Config<MiscConfig> getConfigInstance() {
		return INSTANCE;
	}

	public double getParticleWindIntensity() {
		return ((double) this.particleWindMovement) * 0.01;
	}

	public static class BiomeAmbienceConfig {
		public boolean deepDarkAmbience = DefaultMiscConfig.BiomeAmbienceConfig.DEEP_DARK_AMBIENCE;
		public boolean dripstoneCavesAmbience = DefaultMiscConfig.BiomeAmbienceConfig.DRIPSTONE_CAVES_AMBIENCE;
		public boolean lushCavesAmbience = DefaultMiscConfig.BiomeAmbienceConfig.LUSH_CAVES_AMBIENCE;
	}

	public static class BiomeMusicConfig {
		public boolean wilderForestMusic = DefaultMiscConfig.BiomeMusicConfig.WILDER_FOREST_MUSIC;
	}
}
