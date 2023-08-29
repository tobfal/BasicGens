package de.tobfal.basicgens.block.entity;

import de.tobfal.basicgens.energy.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class FluidGeneratorBlockEntityBase extends BlockEntity implements MenuProvider, ITickableBlockEntity {

    private LazyOptional<EnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private final ModEnergyStorage energyHandler;

    private LazyOptional<FluidTank> lazyFluidHandler = LazyOptional.empty();
    private final FluidTank fluidHandler = new FluidTank(5000){
        @Override
        protected void onContentsChanged() {
            setChanged();
        }
    };

    protected final ContainerData data;
    public double fuelEfficiency;

    public int energyPerTick;

    public FluidGeneratorBlockEntityBase(BlockEntityType<?> type, BlockPos pWorldPosition, BlockState pBlockState, Fluid validFluid, double fuelEfficiency, int capacity, int energyPerTick) {
        this(type, pWorldPosition, pBlockState, validFluid, fuelEfficiency, capacity, energyPerTick, energyPerTick);
    }

    public FluidGeneratorBlockEntityBase(BlockEntityType<?> type, BlockPos pWorldPosition, BlockState pBlockState, Fluid validFluid, double fuelEfficiency, int capacity, int energyPerTick, int maxTransfer) {
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

        this.fluidHandler.setValidator(fluidStack -> fluidStack.getFluid() == validFluid);

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
        if (cap == ForgeCapabilities.ENERGY)
            return lazyEnergyHandler.cast();
        if (cap == ForgeCapabilities.FLUID_HANDLER)
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
        energyHandler.deserializeNBT(nbt.get("energy"));
        fluidHandler.readFromNBT(nbt);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> energyHandler);
        lazyFluidHandler = LazyOptional.of(() -> fluidHandler);
    }

    public void tick() {
        this.outputEnergy(this);
        if(this.fluidHandler.isEmpty()) return;
        boolean canInsertEnergy = this.energyHandler.getMaxEnergyStored() - this.energyHandler.getEnergyStored() >= this.energyPerTick;
        if(!canInsertEnergy) return;

        this.fluidHandler.drain(1, IFluidHandler.FluidAction.EXECUTE);
        this.energyHandler.receiveEnergyIntern(this.energyPerTick, false);
    }

    private void outputEnergy(FluidGeneratorBlockEntityBase pBlockEntity) {
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

    public void onBucketInteraction(Player pPlayer, InteractionHand pHand, FluidGeneratorBlockEntityBase pBlockEntity, Fluid pFluid){
        if(pBlockEntity.fluidHandler.getCapacity() - pBlockEntity.fluidHandler.getFluidAmount() < 1000) return;
        FluidStack bucketFluidStack = new FluidStack(pFluid, 1000);
        if(!pBlockEntity.fluidHandler.isFluidValid(bucketFluidStack)) return;
        pBlockEntity.fluidHandler.fill(bucketFluidStack, IFluidHandler.FluidAction.EXECUTE);
        if(pFluid == Fluids.LAVA) {
            pPlayer.playNotifySound(SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS,1.0f, 1.0f);
        } else {
            pPlayer.playNotifySound(SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS,1.0f, 1.0f);
        }
        pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
    }
}

