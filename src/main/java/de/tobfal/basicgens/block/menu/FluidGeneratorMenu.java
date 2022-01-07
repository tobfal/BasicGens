package de.tobfal.basicgens.block.menu;

import de.tobfal.basicgens.block.entity.FluidGeneratorBlockEntityBase;
import de.tobfal.basicgens.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FluidGeneratorMenu extends AbstractContainerMenu {

    private final FluidGeneratorBlockEntityBase blockEntity;
    private final Level level;
    private final ContainerData data;

    public FluidGeneratorMenu(int windowId, Inventory inv, FriendlyByteBuf extraData) {
        this(windowId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
    }

    public FluidGeneratorMenu(int windowId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.FLUID_GENERATOR_MENU.get(), windowId);

        blockEntity = ((FluidGeneratorBlockEntityBase) entity);
        this.level = inv.player.level;
        this.data = data;

        // Make inventory and container slots
        int box = 18;
        // Player Inventory
        for(int height = 0; height < 3; ++height){
            for(int width = 0; width < 9; ++width){
                addSlot(new Slot(inv, 9 + height * 9 + width, 8 + box * width, 84 + box * height));
            }
        }
        for(int width = 0; width < 9; ++width){
            addSlot(new Slot(inv, width, 8 + box * width, 142));
        }

        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, blockEntity.getBlockState().getBlock());
    }

    public int getScaledEnergy(){
        int energy = this.data.get(2);
        int maxEnergy = this.data.get(3);
        int energyBarSize = 64;

        return maxEnergy == 0 ? 0 : Math.round((float)energyBarSize * (float)energy / (float)maxEnergy);
    }

    public int getScaledFluid() {
        int fluidAmmount = this.data.get(0);
        int fluidCapacity = this.data.get(1);
        int fluidBarSize = 59;

        return fluidCapacity == 0 ? 0 : Math.round((float)fluidBarSize * (float)fluidAmmount / (float)fluidCapacity);
    }

    public int getEnergy(){
        return this.data.get(2);
    }

    public int getMaxEnergy(){
        return this.data.get(3);
    }

    public int getFluidAmmount() {
        return this.data.get(0);
    }

    public int getFluidCapacity() {
        return this.data.get(1);
    }
}
