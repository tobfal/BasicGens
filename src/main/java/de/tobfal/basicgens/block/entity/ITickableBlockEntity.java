package de.tobfal.basicgens.block.entity;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public interface ITickableBlockEntity {

    static <T extends BlockEntity> BlockEntityTicker<T> getTickerHelper(Level pLevel) {
        return pLevel.isClientSide() ? null : (level0, pos0, state0, blockEntity) -> ((ITickableBlockEntity) blockEntity).tick();
    }

    void tick();
}
