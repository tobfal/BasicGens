package de.tobfal.basicgens.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.IntValue STONE_GENERATOR_PERTICK;
    public static ForgeConfigSpec.IntValue STONE_GENERATOR_TRANSFER;
    public static ForgeConfigSpec.IntValue STONE_GENERATOR_CAPACITY;

    public static ForgeConfigSpec.IntValue IRON_GENERATOR_PERTICK;
    public static ForgeConfigSpec.IntValue IRON_GENERATOR_TRANSFER;
    public static ForgeConfigSpec.IntValue IRON_GENERATOR_CAPACITY;

    public static void init() {
        initServer();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG);
    }

    private static void initServer() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        // Stone Generator
        builder.comment("Stone Generator").push("stone_generator");
        STONE_GENERATOR_PERTICK = builder
                .comment("RF/t produced by the generator")
                .defineInRange("generatePerTick", 10, 1, Integer.MAX_VALUE);
        STONE_GENERATOR_TRANSFER = builder
                .comment("RF/t maximum output")
                .defineInRange("sendPerTick", 10, 1, Integer.MAX_VALUE);
        STONE_GENERATOR_CAPACITY = builder
                .comment("RF storage capacity")
                .defineInRange("capacity", 10000, 1, Integer.MAX_VALUE);
        builder.pop();
        //----------------

        // Iron Generator
        builder.comment("Iron Generator").push("iron_generator");
        IRON_GENERATOR_PERTICK = builder
                .comment("RF/t produced by the generator")
                .defineInRange("generatePerTick", 25, 1, Integer.MAX_VALUE);
        IRON_GENERATOR_TRANSFER = builder
                .comment("RF/t maximum output")
                .defineInRange("sendPerTick", 25, 1, Integer.MAX_VALUE);
        IRON_GENERATOR_CAPACITY = builder
                .comment("RF storage capacity")
                .defineInRange("capacity", 25000, 1, Integer.MAX_VALUE);
        builder.pop();
        //----------------

        SERVER_CONFIG = builder.build();
    }
}
