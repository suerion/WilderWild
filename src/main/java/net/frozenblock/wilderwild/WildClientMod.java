package net.frozenblock.wilderwild;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.frozenblock.api.mathematics.AdvancedMath;
import net.frozenblock.wilderwild.entity.AncientHornProjectileEntity;
import net.frozenblock.wilderwild.entity.render.AncientHornProjectileModel;
import net.frozenblock.wilderwild.entity.render.AncientHornProjectileRenderer;
import net.frozenblock.wilderwild.entity.render.FireflyEntityRenderer;
import net.frozenblock.wilderwild.misc.FireflyCounter;
import net.frozenblock.wilderwild.misc.PVZGWSound.FlyBySoundHub;
import net.frozenblock.wilderwild.misc.PVZGWSound.MovingSoundLoop;
import net.frozenblock.wilderwild.particle.FloatingSculkBubbleParticle;
import net.frozenblock.wilderwild.particle.PollenParticle;
import net.frozenblock.wilderwild.particle.TermiteParticle;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class WildClientMod implements ClientModInitializer {
    public static final EntityModelLayer ANCIENT_HORN_PROJECTILE_LAYER = new EntityModelLayer(new Identifier(WilderWild.MOD_ID, "ancient_horn_projectile"), "main");

    @Override
    public void onInitializeClient() {
        RegisterParticles.RegisterParticles();

        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CARNATION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.WHITE_DANDELION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_CARNATION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_WHITE_DANDELION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.DATURA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CATTAIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.MILKWEED, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POLLEN_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.SCULK_ECHOER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.ECHO_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HANGING_TENDRIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_ACACIA_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_BIRCH_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_DARK_OAK_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_JUNGLE_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_MANGROVE_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_OAK_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_SPRUCE_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.FLOWERED_LILY_PAD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BROWN_SHELF_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.RED_SHELF_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.BAOBAB_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.TERMITE_MOUND, RenderLayer.getSolid());

        ParticleFactoryRegistry.getInstance().register(RegisterParticles.POLLEN, PollenParticle.PollenFactory::new);
        ParticleFactoryRegistry.getInstance().register(RegisterParticles.DANDELION_SEED, PollenParticle.DandelionFactory::new);
        ParticleFactoryRegistry.getInstance().register(RegisterParticles.CONTROLLED_DANDELION_SEED, PollenParticle.ControlledDandelionFactory::new);
        ParticleFactoryRegistry.getInstance().register(RegisterParticles.MILKWEED_SEED, PollenParticle.MilkweedFactory::new);
        ParticleFactoryRegistry.getInstance().register(RegisterParticles.CONTROLLED_MILKWEED_SEED, PollenParticle.ControlledMilkweedFactory::new);
        ParticleFactoryRegistry.getInstance().register(RegisterParticles.FLOATING_SCULK_BUBBLE, FloatingSculkBubbleParticle.BubbleFactory::new);
        ParticleFactoryRegistry.getInstance().register(RegisterParticles.TERMITE, TermiteParticle.Factory::new);

        EntityRendererRegistry.register(RegisterEntities.FIREFLY, FireflyEntityRenderer::new);
        EntityRendererRegistry.register(RegisterEntities.ANCIENT_HORN_PROJECTILE_ENTITY, AncientHornProjectileRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ANCIENT_HORN_PROJECTILE_LAYER, AncientHornProjectileModel::getTexturedModelData);

        receiveAncientHornProjectilePacket();
        receiveEasyEchoerBubblePacket();
        receiveSeedPacket();
        receiveControlledSeedPacket();
        receiveTermitePacket();

        receiveFireflyCaptureInfoPacket();
        receiveFlybySoundPacket();
        receiveMovingLoopingSoundPacket();

        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return 7455580;
            }
            return 2129968;
        }), RegisterBlocks.FLOWERED_LILY_PAD);
        ColorProviderRegistry.BLOCK.register(((state,world,pos,tintIndex)-> {
                assert world != null;
                return BiomeColors.getFoliageColor(world, pos);
        }),RegisterBlocks.BAOBAB_LEAVES);

        ClientTickEvents.START_WORLD_TICK.register(e -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world != null) {
                FlyBySoundHub.clientFlyby.update(client, client.player, true); //CHANGE TO FALSE TO NOT AUTOMATICALLY ADD FLYBY SOUNDS
            }
        });
}


    public void receiveAncientHornProjectilePacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.HORN_PROJECTILE_PACKET_ID, (ctx, handler, byteBuf, responseSender) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = AncientHornProjectileEntity.EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
            float pitch = AncientHornProjectileEntity.EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            float yaw = AncientHornProjectileEntity.EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            WilderWild.log("Receiving Ancient Horn Projectile Packet At " + pos, WilderWild.DEV_LOGGING);
            ctx.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(MinecraftClient.getInstance().world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos.x, pos.y, pos.z);
                e.setPos(pos.x, pos.y, pos.z);
                e.setPitch(pitch);
                e.setYaw(yaw);
                e.setId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
                WilderWild.log("Spawned Ancient Horn Projectile", WilderWild.UNSTABLE_LOGGING);
            });
        });
    }

    public void receiveEasyEchoerBubblePacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.FLOATING_SCULK_BUBBLE_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            Vec3d pos = new Vec3d(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
            int size = byteBuf.readVarInt();
            int age = byteBuf.readVarInt();
            double yVel = byteBuf.readDouble();
            int count = byteBuf.readVarInt();
            ctx.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("why is your world null");
                for (int i=0; i<count; i++) {
                    MinecraftClient.getInstance().world.addParticle(RegisterParticles.FLOATING_SCULK_BUBBLE, pos.x, pos.y, pos.z, size, age, yVel);
                }
            });
        });
    }

    public void receiveSeedPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.SEED_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            Vec3d pos = new Vec3d(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
            int count = byteBuf.readVarInt();
            ParticleEffect particle = byteBuf.readBoolean() ? RegisterParticles.MILKWEED_SEED : RegisterParticles.DANDELION_SEED;
            ctx.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("why is your world null");
                for (int i=0; i<count; i++) {
                    MinecraftClient.getInstance().world.addParticle(particle, pos.x, pos.y, pos.z, 0, 0, 0);
                }
            });
        });
    }

    public void receiveControlledSeedPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.CONTROLLED_SEED_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            Vec3d pos = new Vec3d(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
            double velx = byteBuf.readDouble();
            double vely = byteBuf.readDouble();
            double velz = byteBuf.readDouble();
            int count = byteBuf.readVarInt();
            ParticleEffect particle = byteBuf.readBoolean() ? RegisterParticles.CONTROLLED_MILKWEED_SEED : RegisterParticles.CONTROLLED_DANDELION_SEED;
            ctx.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("why is your world null");
                for (int i=0; i<count; i++) {
                    MinecraftClient.getInstance().world.addParticle(particle, pos.x, pos.y, pos.z, velx, vely, velz);
                }
            });
        });
    }

    public void receiveTermitePacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.TERMITE_PARTICLE_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            Vec3d pos = new Vec3d(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
            int count = byteBuf.readVarInt();
            ctx.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("why is your world null");
                for (int i=0; i<count; i++) {
                    MinecraftClient.getInstance().world.addParticle(RegisterParticles.TERMITE, pos.x, pos.y, pos.z, AdvancedMath.randomPosNeg()/7,AdvancedMath.randomPosNeg()/7,AdvancedMath.randomPosNeg()/7);
                }
            });
        });
    }

    public void receiveFireflyCaptureInfoPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.CAPTURE_FIREFLY_NOTIFY_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            boolean creative = byteBuf.readBoolean();
            boolean natural = byteBuf.readBoolean();
            ctx.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("why is your world null");
                FireflyCounter.addCapture(creative, natural);
            });
        });
    }

    public void receiveMovingLoopingSoundPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.MOVING_LOOPING_SOUND_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            int id = byteBuf.readVarInt();
            SoundEvent sound = byteBuf.readRegistryValue(Registry.SOUND_EVENT);
            SoundCategory category = byteBuf.readEnumConstant(SoundCategory.class);
            float volume = byteBuf.readFloat();
            float pitch = byteBuf.readFloat();
            ctx.execute(() -> {
                ClientWorld world = MinecraftClient.getInstance().world;
                if (world == null)
                    throw new IllegalStateException("why is your world null");
                Entity entity = world.getEntityById(id);
                if (entity == null)
                    throw new IllegalStateException("Unable to play moving looping sound (from wilderwild) whilst entity does not exist!");
                MinecraftClient.getInstance().getSoundManager().play(new MovingSoundLoop(entity, sound, category, volume, pitch));
            });
        });
    }

    public void receiveFlybySoundPacket() {
        ClientPlayNetworking.registerGlobalReceiver(WilderWild.FLYBY_SOUND_PACKET, (ctx, handler, byteBuf, responseSender) -> {
            int id = byteBuf.readVarInt();
            SoundEvent sound = byteBuf.readRegistryValue(Registry.SOUND_EVENT);
            SoundCategory category = byteBuf.readEnumConstant(SoundCategory.class);
            float volume = byteBuf.readFloat();
            float pitch = byteBuf.readFloat();
            ctx.execute(() -> {
                ClientWorld world = MinecraftClient.getInstance().world;
                if (world == null)
                    throw new IllegalStateException("why is your world null");
                Entity entity = world.getEntityById(id);
                if (entity == null)
                    throw new IllegalStateException("Unable to add flyby sound to non-existent entity!");
                FlyBySoundHub.clientFlyby.addEntity(entity, sound, category, volume, pitch);
                WilderWild.log("ADDED ENTITY TO FLYBYS", true);
            });
        });
    }

}