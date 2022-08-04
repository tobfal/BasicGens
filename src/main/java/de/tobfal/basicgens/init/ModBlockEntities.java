package de.tobfal.basicgens.init;

import de.tobfal.basicgens.BasicGens;
import de.tobfal.basicgens.block.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BasicGens.MOD_ID);

    // Register BlockEntities

    public static final RegistryObject<BlockEntityType<StoneGeneratorBlockEntity>> STONE_GENERATOR = BLOCK_ENTITIES.register("stone_generator",
            () -> BlockEntityType.Builder.of(StoneGeneratorBlockEntity::new, ModBlocks.STONE_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<IronGeneratorBlockEntity>> IRON_GENERATOR = BLOCK_ENTITIES.register("iron_generator",
            () -> BlockEntityType.Builder.of(IronGeneratorBlockEntity::new, ModBlocks.IRON_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<GoldGeneratorBlockEntity>> GOLD_GENERATOR = BLOCK_ENTITIES.register("gold_generator",
            () -> BlockEntityType.Builder.of(GoldGeneratorBlockEntity::new, ModBlocks.GOLD_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<NetherGeneratorBlockEntity>> NETHER_GENERATOR = BLOCK_ENTITIES.register("nether_generator",
            () -> BlockEntityType.Builder.of(NetherGeneratorBlockEntity::new, ModBlocks.NETHER_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<GlowstoneGeneratorBlockEntity>> GLOWSTONE_GENERATOR = BLOCK_ENTITIES.register("glowstone_generator",
            () -> BlockEntityType.Builder.of(GlowstoneGeneratorBlockEntity::new, ModBlocks.GLOWSTONE_GENERATOR.get()).build(null));

    //----------------

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
