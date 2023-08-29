package de.tobfal.basicgens.block.entity;

import de.tobfal.basicgens.energy.ModEnergyStorage;
import de.tobfal.basicgens.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class GeneratorBlockEntityBase extends BlockEntity implements MenuProvider, ITickableBlockEntity {

    //Handlers
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if(slot == 0 && (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) <= 0 || stack.getItem() == Items.LAVA_BUCKET)) return stack;
            if(slot == 1 && stack.getItem() != ModItems.CONTROLLER_AUGMENT.get()) return stack;
            return super.insertItem(slot, stack, simulate);
        }
    };

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    public final ModEnergyStorage energyHandler;

    protected final ContainerData data;
    public int fuelTime = 0;
    public int maxFuelTime = 0;
    public double fuelEfficiency;

    public int energyPerTick;

    public GeneratorBlockEntityBase(BlockEntityType<?> type, BlockPos pWorldPosition, BlockState pBlockState, double fuelEfficiency, int capacity, int energyPerTick) {
        this(type, pWorldPosition, pBlockState, fuelEfficiency, capacity, energyPerTick, energyPerTick);
    }

    public GeneratorBlockEntityBase(BlockEntityType<?> type, BlockPos pWorldPosition, BlockState pBlockState, double fuelEfficiency, int capacity, int energyPerTick, int maxTransfer) {
        super(type, pWorldPosition, pBlockState);

        this.energyHandler = new ModEnergyStorage(capacity, maxTransfer){
            @Override
            public boolean canReceive() {
                return false;
            }

            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };

        this.energyPerTick = energyPerTick;
        this.fuelEfficiency = fuelEfficiency;

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> GeneratorBlockEntityBase.this.fuelTime;
                    case 1 -> GeneratorBlockEntityBase.this.maxFuelTime;
                    case 2 -> GeneratorBlockEntityBase.this.energyHandler.getEnergyStored();
                    case 3 -> GeneratorBlockEntityBase.this.energyHandler.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> GeneratorBlockEntityBase.this.fuelTime = pValue;
                    case 1 -> GeneratorBlockEntityBase.this.maxFuelTime = pValue;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
            return lazyItemHandler.cast();
        if (cap == ForgeCapabilities.ENERGY)
            return lazyEnergyHandler.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.put("energy", energyHandler.serializeNBT());
        tag.putInt("fuelTime", this.fuelTime);
        tag.putInt("maxFuelTime", this.fuelTime);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        energyHandler.deserializeNBT(nbt.get("energy"));
        this.fuelTime = nbt.getInt("fuelTime");
        this.maxFuelTime = nbt.getInt("maxFuelTime");
        super.load(nbt);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> energyHandler);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void tick() {
        this.outputEnergy(this);

        ItemStack input = this.itemHandler.getStackInSlot(0);
        boolean checkIfFull = false;

        for(int i = 1; i < this.itemHandler.getSlots(); i++){
            ItemStack augment = this.itemHandler.getStackInSlot(i);
            if(augment.getItem() == ModItems.CONTROLLER_AUGMENT.get()) checkIfFull = true;
        }

        if(input.isEmpty() && this.fuelTime <= 0) return;
        boolean canInsertEnergy = this.energyHandler.getMaxEnergyStored() - this.energyHandler.getEnergyStored() >= this.energyPerTick;

        if(canInsertEnergy || !checkIfFull) {
            if(this.fuelTime > 0) {
                this.fuelTime--;
                this.energyHandler.receiveEnergyIntern(this.energyPerTick, false);
            } else if(!input.isEmpty() && canInsertEnergy) {
                this.maxFuelTime = (int)(ForgeHooks.getBurnTime(this.itemHandler.extractItem(0, 1, false), RecipeType.SMELTING) * this.fuelEfficiency);
                this.fuelTime = this.maxFuelTime;
            }
        }
    }

    private void outputEnergy(GeneratorBlockEntityBase pBlockEntity) {
        if(pBlockEntity.energyHandler.canExtract()) {
            for(Direction direction : Direction.values()) {
                BlockEntity be = pBlockEntity.level.getBlockEntity(worldPosition.relative(direction));
                if(be == null) continue;
                boolean doContinue = be.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).map(handler -> {
                    if(!handler.canReceive()) return true;
                    int sendSimulate = pBlockEntity.energyHandler.extractEnergy(pBlockEntity.energyHandler.getEnergyStored(), true);
                    int energySent = handler.receiveEnergy(sendSimulate, false);
                    pBlockEntity.energyHandler.extractEnergy(energySent, false);
                    return pBlockEntity.energyHandler.getEnergyStored() > 0;
                }).orElse(true);

                if(!doContinue) return;
            }
        }
    }
}

