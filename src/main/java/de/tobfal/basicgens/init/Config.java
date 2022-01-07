package de.tobfal.basicgens.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {

    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.IntValue STONE_GENERATOR_PERTICK;
    public static ForgeConfigSpec.IntValue STONE_GENERATOR_TRANSFER;
    public static ForgeConfigSpec.IntValue STONE_GENERATOR_CAPACITY;
    public static ForgeConfigSpec.DoubleValue STONE_GENERATOR_EFFICIENCY;

    public static ForgeConfigSpec.IntValue IRON_GENERATOR_PERTICK;
    public static ForgeConfigSpec.IntValue IRON_GENERATOR_TRANSFER;
    public static ForgeConfigSpec.IntValue IRON_GENERATOR_CAPACITY;
    public static ForgeConfigSpec.DoubleValue IRON_GENERATOR_EFFICIENCY;

    public static ForgeConfigSpec.IntValue GOLD_GENERATOR_PERTICK;
    public static ForgeConfigSpec.IntValue GOLD_GENERATOR_TRANSFER;
    public static ForgeConfigSpec.IntValue GOLD_GENERATOR_CAPACITY;
    public static ForgeConfigSpec.DoubleValue GOLD_GENERATOR_EFFICIENCY;

    public static ForgeConfigSpec.IntValue NETHER_GENERATOR_PERTICK;
    public static ForgeConfigSpec.IntValue NETHER_GENERATOR_TRANSFER;
    public static ForgeConfigSpec.IntValue NETHER_GENERATOR_CAPACITY;
    public static ForgeConfigSpec.IntValue NETHER_GENERATOR_EFFICIENCY;

    public static ForgeConfigSpec.IntValue GLOWSTONE_GENERATOR_PERTICK;
    public static ForgeConfigSpec.IntValue GLOWSTONE_GENERATOR_TRANSFER;
    public static ForgeConfigSpec.IntValue GLOWSTONE_GENERATOR_CAPACITY;
    public static ForgeConfigSpec.IntValue GLOWSTONE_GENERATOR_EFFICIENCY;

    public static void init() {
        initCommon();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
    }

    private static void initCommon() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        // Stone Generator
        builder.comment("Stone Generator").push("stone_generator");
        STONE_GENERATOR_PERTICK = builder
                .comment("RF/t produced by the generator")
                .defineInRange("generatePerTick", 15, 1, Integer.MAX_VALUE);
        STONE_GENERATOR_TRANSFER = builder
                .comment("RF/t maximum output")
                .defineInRange("sendPerTick", 15, 1, Integer.MAX_VALUE);
        STONE_GENERATOR_CAPACITY = builder
                .comment("RF storage capacity")
                .defineInRange("capacity", 10000, 1, Integer.MAX_VALUE);
        STONE_GENERATOR_EFFICIENCY = builder
                .comment("Fuel efficiency relative to minecraft default burn time (generatorBurnTime = minecraftBurnTime * fuelEfficiency)")
                .defineInRange("fuelEfficiency", 0.75, 0, Double.MAX_VALUE);
        builder.pop();
        //----------------

        // Iron Generator
        builder.comment("Iron Generator").push("iron_generator");
        IRON_GENERATOR_PERTICK = builder
                .comment("RF/t produced by the generator")
                .defineInRange("generatePerTick", 30, 1, Integer.MAX_VALUE);
        IRON_GENERATOR_TRANSFER = builder
                .comment("RF/t maximum output")
                .defineInRange("sendPerTick", 30, 1, Integer.MAX_VALUE);
        IRON_GENERATOR_CAPACITY = builder
                .comment("RF storage capacity")
                .defineInRange("capacity", 25000, 1, Integer.MAX_VALUE);
        IRON_GENERATOR_EFFICIENCY = builder
                .comment("Fuel efficiency relative to minecraft default burn time (generatorBurnTime = minecraftBurnTime * fuelEfficiency)")
                .defineInRange("fuelEfficiency", 1, 0, Double.MAX_VALUE);
        builder.pop();
        //----------------

        // Gold Generator
        builder.comment("Gold Generator").push("gold_generator");
        GOLD_GENERATOR_PERTICK = builder
                .comment("RF/t produced by the generator")
                .defineInRange("generatePerTick", 50, 1, Integer.MAX_VALUE);
        GOLD_GENERATOR_TRANSFER = builder
                .comment("RF/t maximum output")
                .defineInRange("sendPerTick", 50, 1, Integer.MAX_VALUE);
        GOLD_GENERATOR_CAPACITY = builder
                .comment("RF storage capacity")
                .defineInRange("capacity", 70000, 1, Integer.MAX_VALUE);
        GOLD_GENERATOR_EFFICIENCY = builder
                .comment("Fuel efficiency relative to minecraft default burn time (generatorBurnTime = minecraftBurnTime * fuelEfficiency)")
                .defineInRange("fuelEfficiency", 1, 0, Double.MAX_VALUE);
        builder.pop();
        //----------------

        // Nether Generator
        builder.comment("Nether Generator").push("nether_generator");
        NETHER_GENERATOR_PERTICK = builder
                .comment("RF/t produced by the generator")
                .defineInRange("generatePerTick", 40, 1, Integer.MAX_VALUE);
        NETHER_GENERATOR_TRANSFER = builder
                .comment("RF/t maximum output")
                .defineInRange("sendPerTick", 40, 1, Integer.MAX_VALUE);
        NETHER_GENERATOR_CAPACITY = builder
                .comment("RF storage capacity")
                .defineInRange("capacity", 50000, 1, Integer.MAX_VALUE);
        NETHER_GENERATOR_EFFICIENCY = builder
                .comment("mB of fluid used per tick")
                .defineInRange("fuelEfficiency", 1, 1, Integer.MAX_VALUE);
        builder.pop();
        //----------------

        // Glowstone Generator
        builder.comment("Glowstone Generator").push("glowstone_generator");
        GLOWSTONE_GENERATOR_PERTICK = builder
                .comment("RF/t produced by the generator")
                .defineInRange("generatePerTick", 60, 1, Integer.MAX_VALUE);
        GLOWSTONE_GENERATOR_TRANSFER = builder
                .comment("RF/t maximum output")
                .defineInRange("sendPerTick", 60, 1, Integer.MAX_VALUE);
        GLOWSTONE_GENERATOR_CAPACITY = builder
                .comment("RF storage capacity")
                .defineInRange("capacity", 80000, 1, Integer.MAX_VALUE);
        GLOWSTONE_GENERATOR_EFFICIENCY = builder
                .comment("mB of fluid used per tick")
                .defineInRange("fuelEfficiency", 1, 1, Integer.MAX_VALUE);
        builder.pop();
        //----------------

        COMMON_CONFIG = builder.build();
    }
}
