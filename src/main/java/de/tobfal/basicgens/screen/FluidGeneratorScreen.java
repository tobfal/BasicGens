package de.tobfal.basicgens.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import de.tobfal.basicgens.BasicGens;
import de.tobfal.basicgens.block.menu.FluidGeneratorMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class FluidGeneratorScreen extends AbstractContainerScreen<FluidGeneratorMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BasicGens.MOD_ID, "textures/gui/fluid_generator_gui.png");

    public FluidGeneratorScreen(FluidGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        int energyHeight = menu.getScaledEnergy();
        int fluidHeight = menu.getScaledFluid();

        //Render fluid bar background
        pGuiGraphics.blit(TEXTURE, x + 72, y + 16, 176, 64, 33, 59);

        //Render fluid bar
        FluidStack fluidStack = new FluidStack(Fluids.LAVA.getSource(), 1);
        ResourceLocation fluidResourceLocation = IClientFluidTypeExtensions.of(fluidStack.getFluid()).getStillTexture(fluidStack);
        TextureAtlasSprite fluidSprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidResourceLocation);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

        int spritesNeeded = fluidHeight / 16;

        for(int i = 0; i < spritesNeeded + 1; i++){
            for(int j = 0; j < 3; j++){
                pGuiGraphics.blit(x + 72 + 16 * j, y + 16 + 59 - fluidHeight + 16 * i, 0, 16, 16, fluidSprite);
            }
        }

        //Render Screen
        RenderSystem.setShaderTexture(0, TEXTURE);
        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        //Render energy bar
        pGuiGraphics.blit(TEXTURE, x + 156, y + 11 + 64 - energyHeight, 176, 64 - energyHeight, 8, energyHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        int x = pMouseX - (width - imageWidth) / 2;
        int y = pMouseY - (height - imageHeight) / 2;
        if(x > 155 && x < 164 && y > 10 && y < 75)
            pGuiGraphics.renderTooltip(this.font, Component.literal(String.format("%.1f kRF/%.0f kRF", menu.getEnergy()/1000f, menu.getMaxEnergy()/1000f)), pMouseX, pMouseY);
        if(x > 71 && x < 105 && y > 15 && y < 75)
            pGuiGraphics.renderTooltip(this.font, Component.literal(String.format("%d mB/%d mB", menu.getFluidAmmount(), menu.getFluidCapacity())), pMouseX, pMouseY);
    }
}
