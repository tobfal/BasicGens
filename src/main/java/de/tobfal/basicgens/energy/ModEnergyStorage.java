package de.tobfal.basicgens.energy;

import net.minecraftforge.energy.EnergyStorage;

public class ModEnergyStorage extends EnergyStorage {

    //<editor-fold desc="Constructor">
    public ModEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }
    //</editor-fold>

    //<editor-fold desc="Methods">
    protected void onEnergyChanged() {
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        onEnergyChanged();
        return super.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        onEnergyChanged();
        return super.receiveEnergy(maxReceive, simulate);
    }

    /**
     * Adds energy to the storage. Returns quantity of energy that was accepted. Ignores canReceive().
     * @param maxReceive Maximum amount of energy to be inserted.
     * @param simulate   If TRUE, the insertion will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
     */
    public int forceReceiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(capacity - energy, maxReceive);
        if (!simulate) {
            energy += energyReceived;
        }
        onEnergyChanged();
        return energyReceived;
    }

    public int forceExtractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        onEnergyChanged();
        return energyExtracted;
    }
    //</editor-fold>
}
