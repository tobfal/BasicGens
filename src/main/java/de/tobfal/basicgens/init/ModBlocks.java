package de.tobfal.basicgens.init;

import de.tobfal.basicgens.BasicGens;
import de.tobfal.basicgens.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BasicGens.MOD_ID);

    public static final RegistryObject<Block> STONE_GENERATOR = registerBlock("stone_generator",
            () -> new StoneGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3f).noOcclusion()));
    public static final RegistryObject<Block> IRON_GENERATOR = registerBlock("iron_generator",
            () -> new IronGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3f).noOcclusion()));
    public static final RegistryObject<Block> GOLD_GENERATOR = registerBlock("gold_generator",
            () -> new GoldGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3f).noOcclusion()));
    public static final RegistryObject<Block> NETHER_GENERATOR = registerBlock("nether_generator",
            () -> new NetherGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3f).noOcclusion()));
    public static final RegistryObject<Block> GLOWSTONE_GENERATOR = registerBlock("glowstone_generator",
            () -> new GlowstoneGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3f).noOcclusion()));

    private static <T extends Block> void registerBlockItem(String name, boolean addToTab, RegistryObject<T> block) {
        if (addToTab) {
            ModCreativeTabs.addToTab(ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties())));
        } else {
            ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        }
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name, true, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, boolean addToTab, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, addToTab, toReturn);
        return toReturn;
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
