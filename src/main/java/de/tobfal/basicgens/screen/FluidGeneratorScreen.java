package de.tobfal.basicgens.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.tobfal.basicgens.BasicGens;
import de.tobfal.basicgens.block.menu.FluidGeneratorMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

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
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        int energy = menu.getScaledEnergy();
        int fluid = menu.getScaledFluid();

        this.blit(pPoseStack, x + 156, y + 11 + 64 - energy, 176, 64 - energy, 8, energy);

        //TODO: Proper FLUID render
        this.blit(pPoseStack, x + 72, y + 16 + 59 - fluid, 176, 64 + 59 - fluid, 33, fluid);
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);

        int x = mouseX - (width - imageWidth) / 2;
        int y = mouseY - (height - imageHeight) / 2;
        if(x > 155 && x < 164 && y > 10 && y < 75)
            renderTooltip(pPoseStack, Component.literal(String.format("%.1f kRF/%.0f kRF", menu.getEnergy()/1000f, menu.getMaxEnergy()/1000f)), mouseX, mouseY);
        if(x > 71 && x < 105 && y > 15 && y < 75)
            renderTooltip(pPoseStack, Component.literal(String.format("%d mB/%d mB", menu.getFluidAmmount(), menu.getFluidCapacity())), mouseX, mouseY);
    }
}
