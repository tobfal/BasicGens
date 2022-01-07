package de.tobfal.basicgens.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab {
    public static final CreativeModeTab BASICGENS_TAB = new CreativeModeTab("basicgenstab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.STONE_GENERATOR.get());
        }
    };
}
