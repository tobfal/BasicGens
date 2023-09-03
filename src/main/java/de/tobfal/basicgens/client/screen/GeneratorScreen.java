package de.tobfal.basicgens.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import de.tobfal.basicgens.BasicGens;
import de.tobfal.basicgens.block.menu.GeneratorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GeneratorScreen extends AbstractContainerScreen<GeneratorMenu> {

    //<editor-fold desc="Constants">
    private static final ResourceLocation TEXTURE = new ResourceLocation(BasicGens.MOD_ID, "textures/gui/generator_gui.png");
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public GeneratorScreen(GeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    //</editor-fold>

    //<editor-fold desc="Methods">
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

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        int energy = menu.getScaledEnergy();
        int fuelProgress = menu.getScaledFuelProgress();

        pGuiGraphics.blit(TEXTURE, x + 156, y + 11 + 64 - energy, 176, 64 - energy, 8, energy);
        pGuiGraphics.blit(TEXTURE, x + 81, y + 54 + 14 - fuelProgress, 176, 64 + 14 - fuelProgress, 14, fuelProgress);
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        int x = pMouseX - (width - imageWidth) / 2;
        int y = pMouseY - (height - imageHeight) / 2;
        if (x > 155 && x < 164 && y > 10 && y < 75)
            pGuiGraphics.renderTooltip(this.font, Component.literal(String.format("%.1f kRF/%.0f kRF", menu.getEnergy() / 1000f, menu.getMaxEnergy() / 1000f)), pMouseX, pMouseY);
    }
    //</editor-fold>
}
