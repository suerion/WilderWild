package net.frozenblock.wilderwild.misc.mod_compat.clothconfig;

import net.frozenblock.wilderwild.misc.config.BlockConfig;
import net.frozenblock.wilderwild.misc.config.EntityConfig;
import net.frozenblock.wilderwild.misc.config.ItemConfig;
import net.frozenblock.wilderwild.misc.config.MiscConfig;
import net.frozenblock.wilderwild.misc.config.WilderWildConfig;
import net.frozenblock.wilderwild.misc.config.WorldgenConfig;

public class ClothConfigIntegration extends AbstractClothConfigIntegration {
	public ClothConfigIntegration() {
		super();
	}

	private static final BlockConfig BLOCK = WilderWildConfig.get().block;
	private static final EntityConfig ENTITY = WilderWildConfig.get().entity;
	private static final ItemConfig ITEM = WilderWildConfig.get().item;
	private static final WorldgenConfig WORLDGEN = WilderWildConfig.get().worldgen;
	private static final MiscConfig MISC = WilderWildConfig.get().misc;

	private static final EntityConfig.AllayConfig ALLAY = ENTITY.allay;
	private static final ItemConfig.AncientHornConfig ANCIENT_HORN = ITEM.ancientHorn;
	private static final MiscConfig.BiomeAmbienceConfig BIOME_AMBIENCE = MISC.biomeAmbience;
	private static final MiscConfig.BiomeMusicConfig BIOME_MUSIC = MISC.biomeMusic;
	private static final WorldgenConfig.BiomeGeneration BIOME_GENERATION = WORLDGEN.biomeGeneration;
	private static final WorldgenConfig.BiomePlacement BIOME_PLACEMENT = WORLDGEN.biomePlacement;
	private static final BlockConfig.BlockSoundsConfig BLOCK_SOUNDS = BLOCK.blockSounds;
	private static final EntityConfig.EnderManConfig ENDER_MAN = ENTITY.enderMan;
	private static final ItemConfig.ProjectileLandingSoundsConfig PROJECTILE_LANDING_SOUNDS = WilderWildConfig.get().item.projectileLandingSounds;
	private static final BlockConfig.StoneChestConfig STONE_CHEST = BLOCK.stoneChest;
	private static final EntityConfig.WardenConfig WARDEN = ENTITY.warden;
	private static final EntityConfig.FireflyConfig FIREFLY = ENTITY.firefly;
	private static final EntityConfig.JellyfishConfig JELLYFISH = ENTITY.jellyfish;
	private static final EntityConfig.TumbleweedConfig TUMBLEWEED = ENTITY.tumbleweed;

	@Override
	public void init() {}

	@Override
	public boolean mcLiveSensorTendrils() {
		return BLOCK.mcLiveSensorTendrils;
	}

	@Override
	public boolean billboardTendrils() {
		return BLOCK.billboardTendrils;
	}

	@Override
	public boolean cactusSounds() {
		return BLOCK_SOUNDS.cactusSounds;
	}

	@Override
	public boolean claySounds() {
		return BLOCK_SOUNDS.claySounds;
	}

	@Override
	public boolean coarseDirtSounds() {
		return BLOCK_SOUNDS.coarseDirtSounds;
	}

	@Override
	public boolean cobwebSounds() {
		return BLOCK_SOUNDS.cobwebSounds;
	}

	@Override
	public boolean deadBushSounds() {
		return BLOCK_SOUNDS.deadBushSounds;
	}

	@Override
	public boolean flowerSounds() {
		return BLOCK_SOUNDS.flowerSounds;
	}

	@Override
	public boolean gravelSounds() {
		return BLOCK_SOUNDS.gravelSounds;
	}

	@Override
	public boolean frostedIceSounds() {
		return BLOCK_SOUNDS.frostedIceSounds;
	}

	@Override
	public boolean leafSounds() {
		return BLOCK_SOUNDS.leafSounds;
	}

	@Override
	public boolean lilyPadSounds() {
		return BLOCK_SOUNDS.lilyPadSounds;
	}

	@Override
	public boolean mushroomBlockSounds() {
		return BLOCK_SOUNDS.mushroomBlockSounds;
	}

	@Override
	public boolean podzolSounds() {
		return BLOCK_SOUNDS.podzolSounds;
	}

	@Override
	public boolean reinforcedDeepslateSounds() {
		return BLOCK_SOUNDS.reinforcedDeepslateSounds;
	}

	@Override
	public boolean sugarCaneSounds() {
		return BLOCK_SOUNDS.sugarCaneSounds;
	}

	@Override
	public boolean witherRoseSounds() {
		return BLOCK_SOUNDS.witherRoseSounds;
	}

	@Override
	public boolean snowballLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.snowballLandingSounds;
	}

	@Override
	public boolean eggLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.eggLandingSounds;
	}

	@Override
	public boolean enderPearlLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.enderPearlLandingSounds;
	}

	@Override
	public boolean potionLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.potionLandingSounds;
	}

	@Override
	public int stoneChestTimer() {
		return STONE_CHEST.stoneChestTimer;
	}

	@Override
	public boolean shriekerGargling() {
		return BLOCK.shriekerGargling;
	}

	@Override
	public boolean soulFireSounds() {
		return BLOCK.soulFireSounds;
	}

	// ENTITY

	@Override
	public boolean unpassableRail() {
		return ENTITY.unpassableRail;
	}

	@Override
	public boolean keyframeAllayDance() {
		return ALLAY.keyframeAllayDance;
	}

	@Override
	public boolean angerLoopSound() {
		return ENDER_MAN.angerLoopSound;
	}

	@Override
	public boolean movingStareSound() {
		return ENDER_MAN.movingStareSound;
	}

	@Override
	public int fireflySpawnCap() {
		return FIREFLY.fireflySpawnCap;
	}

	@Override
	public int jellyfishSpawnCap() {
		return JELLYFISH.jellyfishSpawnCap;
	}

	@Override
	public int tumbleweedSpawnCap() {
		return TUMBLEWEED.tumbleweedSpawnCap;
	}

	@Override
	public boolean leashedTumbleweed() {
		return TUMBLEWEED.leashedTumbleweed;
	}

	@Override
	public boolean wardenAttacksImmediately() {
		return WARDEN.wardenAttacksImmediately;
	}

	@Override
	public boolean wardenCustomTendrils() {
		return WARDEN.wardenCustomTendrils;
	}

	@Override
	public boolean wardenDyingAnimation() {
		return WARDEN.wardenDyingAnimation;
	}

	@Override
	public boolean wardenEmergesFromCommand() {
		return WARDEN.wardenEmergesFromCommand;
	}

	@Override
	public boolean wardenEmergesFromEgg() {
		return WARDEN.wardenEmergesFromEgg;
	}

	@Override
	public boolean wardenSwimAnimation() {
		return WARDEN.wardenSwimAnimation;
	}

	@Override
	public boolean wardenBedrockSniff() {
		return WARDEN.wardenBedrockSniff;
	}

	// ITEM

	@Override
	public boolean hornCanSummonWarden() {
		return ANCIENT_HORN.ancientHornCanSummonWarden;
	}

	@Override
	public int hornLifespan() {
		return ANCIENT_HORN.ancientHornLifespan;
	}

	@Override
	public int hornMobDamage() {
		return ANCIENT_HORN.ancientHornMobDamage;
	}

	@Override
	public int hornPlayerDamage() {
		return ANCIENT_HORN.ancientHornPlayerDamage;
	}

	@Override
	public boolean hornShattersGlass() {
		return ANCIENT_HORN.ancientHornShattersGlass;
	}

	@Override
	public float hornSizeMultiplier() {
		return ANCIENT_HORN.ancientHornSizeMultiplier;
	}

	@Override
	public boolean projectileBreakParticles() {
		return ITEM.projectileBreakParticles;
	}

	// WORLDGEN

	@Override
	public boolean betaBeaches() {
		return WORLDGEN.betaBeaches;
	}

	@Override
	public boolean modifyJunglePlacement() {
		return BIOME_PLACEMENT.modifyJunglePlacement;
	}

	@Override
	public boolean modifySwampPlacement() {
		return BIOME_PLACEMENT.modifySwampPlacement;
	}

	@Override
	public boolean modifyMangroveSwampPlacement() {
		return BIOME_PLACEMENT.modifyMangroveSwampPlacement;
	}

	@Override
	public boolean modifyWindsweptSavannaPlacement() {
		return BIOME_PLACEMENT.modifyWindsweptSavannaPlacement;
	}

	@Override
	public boolean generateCypressWetlands() {
		return BIOME_GENERATION.generateCypressWetlands;
	}

	@Override
	public boolean generateJellyfishCaves() {
		return BIOME_GENERATION.generateJellyfishCaves;
	}

	@Override
	public boolean generateMixedForest() {
		return BIOME_GENERATION.generateMixedForest;
	}

	@Override
	public boolean generateOasis() {
		return BIOME_GENERATION.generateOasis;
	}

	@Override
	public boolean generateWarmRiver() {
		return BIOME_GENERATION.generateWarmRiver;
	}

	@Override
	public boolean generateBirchTaiga() {
		return BIOME_GENERATION.generateBirchTaiga;
	}

	@Override
	public boolean generateOldGrowthBirchTaiga() {
		return BIOME_GENERATION.generateOldGrowthBirchTaiga;
	}

	@Override
	public boolean generateFlowerField() {
		return BIOME_GENERATION.generateFlowerField;
	}

	@Override
	public boolean generateAridSavanna() {
		return BIOME_GENERATION.generateAridSavanna;
	}

	@Override
	public boolean generateParchedForest() {
		return BIOME_GENERATION.generateParchedForest;
	}

	@Override
	public boolean generateAridForest() {
		return BIOME_GENERATION.generateAridForest;
	}

	@Override
	public boolean generateOldGrowthSnowyTaiga() {
		return BIOME_GENERATION.generateOldGrowthSnowyTaiga;
	}

	@Override
	public boolean generateBirchJungle() {
		return BIOME_GENERATION.generateBirchJungle;
	}

	@Override
	public boolean generateSparseBirchJungle() {
		return BIOME_GENERATION.generateSparseBirchJungle;
	}

	@Override
	public boolean dyingTrees() {
		return WORLDGEN.dyingTrees;
	}

	@Override
	public boolean fallenLogs() {
		return WORLDGEN.fallenLogs;
	}

	@Override
	public boolean wildTrees() {
		return WORLDGEN.wilderWildTreeGen;
	}

	@Override
	public boolean wildGrass() {
		return WORLDGEN.wilderWildGrassGen;
	}

	@Override
	public boolean cypressWitchHuts() {
		return WORLDGEN.cypressWitchHuts;
	}

	// MISC

	@Override
	public boolean deepDarkAmbience() {
		return BIOME_AMBIENCE.deepDarkAmbience;
	}

	@Override
	public boolean dripstoneCavesAmbience() {
		return BIOME_AMBIENCE.dripstoneCavesAmbience;
	}

	@Override
	public boolean lushCavesAmbience() {
		return BIOME_AMBIENCE.lushCavesAmbience;
	}

	@Override
	public boolean birchForestMusic() {
		return BIOME_MUSIC.birchForestMusic;
	}

	@Override
	public boolean flowerForestMusic() {
		return BIOME_MUSIC.flowerForestMusic;
	}

	@Override
	public boolean cloudMovement() {
		return MISC.cloudMovement;
	}
}
