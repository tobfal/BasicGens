package de.tobfal.basicgens.energy;

import net.minecraftforge.energy.EnergyStorage;

public class ModEnergyStorage extends EnergyStorage {

    public ModEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

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
     * Ignores this.maxRecieve and canRecieve()
     */
    public int receiveEnergyIntern(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(capacity - energy, maxReceive);
        if (!simulate)
            energy += energyReceived;
        onEnergyChanged();
        return energyReceived;
    }

    public int extractEnergyIntern(int maxExtract, boolean simulate)
    {
        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        onEnergyChanged();
        return energyExtracted;
    }

}
