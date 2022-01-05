package de.tobfal.basicgens.init;

import de.tobfal.basicgens.BasicGens;
import de.tobfal.basicgens.block.IronGeneratorBlock;
import de.tobfal.basicgens.block.NetherGeneratorBlock;
import de.tobfal.basicgens.block.StoneGeneratorBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BasicGens.MOD_ID);

    // Register Blocks

    public static final RegistryObject<Block> STONE_GENERATOR = registerBlock("stone_generator",
            () -> new StoneGeneratorBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()), CreativeModeTab.TAB_MISC);

    public static final RegistryObject<Block> IRON_GENERATOR = registerBlock("iron_generator",
            () -> new IronGeneratorBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()), CreativeModeTab.TAB_MISC);

    public static final RegistryObject<Block> NETHER_GENERATOR = registerBlock("nether_generator",
            () -> new NetherGeneratorBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()), CreativeModeTab.TAB_MISC);

    //----------------

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
