package de.tobfal.basicgens.block.menu;

import de.tobfal.basicgens.block.entity.BaseGeneratorBlockEntity;
import de.tobfal.basicgens.init.ModItems;
import de.tobfal.basicgens.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class GeneratorMenu extends AbstractContainerMenu {

    private final BaseGeneratorBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public GeneratorMenu(int windowId, Inventory inv, FriendlyByteBuf extraData) {
        this(windowId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
    }

    public GeneratorMenu(int windowId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.GENERATOR_MENU.get(), windowId);
        checkContainerSize(inv, 2);

        blockEntity = ((BaseGeneratorBlockEntity) entity);
        this.level = inv.player.level();
        this.data = data;

        // Make inventory and container slots
        int box = 18;
        // Player Inventory
        for (int height = 0; height < 3; ++height) {
            for (int width = 0; width < 9; ++width) {
                addSlot(new Slot(inv, 9 + height * 9 + width, 8 + box * width, 84 + box * height));
            }
        }
        for (int width = 0; width < 9; ++width) {
            addSlot(new Slot(inv, width, 8 + box * width, 142));
        }

        // Container
        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 80, 35));

                addSlot(new SlotItemHandler(h, 1, 26, 35));
            });
        }

        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex < 36) {
            if (sourceStack.getItem() == ModItems.CONTROLLER_AUGMENT.get()) {
                if (!moveItemStackTo(sourceStack, 37, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (ForgeHooks.getBurnTime(sourceStack, RecipeType.SMELTING) <= 0 || sourceStack.getItem() == Items.LAVA_BUCKET)
                    return ItemStack.EMPTY;
                if (!moveItemStackTo(sourceStack, 36, 37, false)) {
                    return ItemStack.EMPTY;
                }
            }
        } else if (pIndex < 38) {
            if (!moveItemStackTo(sourceStack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(pPlayer, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, blockEntity.getBlockState().getBlock());
    }

    public int getScaledEnergy() {
        int energy = this.data.get(2);
        int maxEnergy = this.data.get(3);
        int energyBarSize = 64;

        return maxEnergy == 0 ? 0 : Math.round((float) energyBarSize * (float) energy / (float) maxEnergy);
    }

    public int getScaledFuelProgress() {
        int fuelProgress = this.data.get(0);
        int maxFuelProgress = this.data.get(1);
        int fuelProgressSize = 14;

        return maxFuelProgress == 0 ? 0 : Math.round((float) fuelProgressSize * (float) fuelProgress / (float) maxFuelProgress);
    }

    public int getEnergy() {
        return this.data.get(2);
    }

    public int getMaxEnergy() {
        return this.data.get(3);
    }

    public int getFuelTime() {
        return this.data.get(0);
    }
}
