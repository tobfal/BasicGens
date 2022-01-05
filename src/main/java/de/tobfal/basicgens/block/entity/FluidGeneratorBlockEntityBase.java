package de.tobfal.basicgens.block.entity;

import de.tobfal.basicgens.energy.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class FluidGeneratorBlockEntityBase extends BlockEntity implements MenuProvider {

    private LazyOptional<EnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private final ModEnergyStorage energyHandler;

    private LazyOptional<FluidTank> lazyFluidHandler = LazyOptional.empty();
    private final FluidTank fluidHandler = new FluidTank(5000);

    protected final ContainerData data;
    public double fuelEfficiency;

    public int energyPerTick;

    public FluidGeneratorBlockEntityBase(BlockEntityType<?> type, BlockPos pWorldPosition, BlockState pBlockState, double fuelEfficiency, int capacity, int energyPerTick) {
        this(type, pWorldPosition, pBlockState, fuelEfficiency, capacity, energyPerTick, energyPerTick);
    }

    public FluidGeneratorBlockEntityBase(BlockEntityType<?> type, BlockPos pWorldPosition, BlockState pBlockState, double fuelEfficiency, int capacity, int energyPerTick, int maxTransfer) {
        super(type, pWorldPosition, pBlockState);

        this.energyHandler = new ModEnergyStorage(capacity, maxTransfer){
            @Override
            public boolean canReceive() {
                return false;
            }
        };

        this.energyPerTick = energyPerTick;
        this.fuelEfficiency = fuelEfficiency;

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FluidGeneratorBlockEntityBase.this.fluidHandler.getFluidAmount();
                    case 1 -> FluidGeneratorBlockEntityBase.this.fluidHandler.getCapacity();
                    case 2 -> FluidGeneratorBlockEntityBase.this.energyHandler.getEnergyStored();
                    case 3 -> FluidGeneratorBlockEntityBase.this.energyHandler.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
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
        if (cap == CapabilityEnergy.ENERGY)
            return lazyEnergyHandler.cast();
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return lazyFluidHandler.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("energy", energyHandler.serializeNBT());
        fluidHandler.writeToNBT(tag);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        energyHandler.deserializeNBT(nbt.getCompound("energy"));
        fluidHandler.readFromNBT(nbt);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> energyHandler);
        lazyFluidHandler = LazyOptional.of(() -> fluidHandler);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, FluidGeneratorBlockEntityBase pBlockEntity) {

        pBlockEntity.outputEnergy(pBlockEntity);
        if(pBlockEntity.fluidHandler.isEmpty()) return;

        pBlockEntity.fluidHandler.drain(1, IFluidHandler.FluidAction.EXECUTE);
        pBlockEntity.energyHandler.receiveEnergyIntern(pBlockEntity.energyPerTick, false);

    }

    private void outputEnergy(FluidGeneratorBlockEntityBase pBlockEntity) {
        if(pBlockEntity.energyHandler.canExtract()) {
            for(Direction direction : Direction.values()) {
                BlockEntity be = pBlockEntity.level.getBlockEntity(worldPosition.relative(direction));
                if(be == null) continue;
                boolean doContinue = be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(handler -> {
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

