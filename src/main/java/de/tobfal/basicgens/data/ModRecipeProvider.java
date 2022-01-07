package de.tobfal.basicgens.data;

import de.tobfal.basicgens.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ShapedRecipeBuilder.shaped(ModBlocks.STONE_GENERATOR.get())
                .define('F', Items.FURNACE)
                .define('G', Items.GUNPOWDER)
                .define('P', Items.PISTON)
                .define('S', Items.STONE)
                .pattern("SSS")
                .pattern("SPS")
                .pattern("GFG")
                .unlockedBy("has_material", has(Items.FURNACE))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlocks.IRON_GENERATOR.get())
                .define('G', ModBlocks.STONE_GENERATOR.get().asItem())
                .define('I', Items.IRON_BLOCK)
                .define('R', Items.REDSTONE)
                .define('P', Items.GUNPOWDER)
                .pattern("III")
                .pattern("IGI")
                .pattern("RPR")
                .unlockedBy("has_material", has(ModBlocks.STONE_GENERATOR.get().asItem()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlocks.GOLD_GENERATOR.get())
                .define('G', ModBlocks.IRON_GENERATOR.get().asItem())
                .define('B', Items.GOLD_BLOCK)
                .define('R', Items.REDSTONE)
                .define('P', Items.GUNPOWDER)
                .pattern("BBB")
                .pattern("BGB")
                .pattern("RPR")
                .unlockedBy("has_material", has(ModBlocks.IRON_GENERATOR.get().asItem()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlocks.NETHER_GENERATOR.get())
                .define('G', ModBlocks.STONE_GENERATOR.get().asItem())
                .define('B', Items.NETHER_BRICKS)
                .define('S', Items.GLOWSTONE)
                .define('L', Items.LAVA_BUCKET)
                .pattern("BBB")
                .pattern("BGB")
                .pattern("SLS")
                .unlockedBy("has_material", has(ModBlocks.STONE_GENERATOR.get().asItem()))
                .save(pFinishedRecipeConsumer);
    }

}
