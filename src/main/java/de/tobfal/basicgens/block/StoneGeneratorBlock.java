package de.tobfal.basicgens.block;

import de.tobfal.basicgens.block.entity.GeneratorBlockEntityBase;
import de.tobfal.basicgens.block.entity.ITickableBlockEntity;
import de.tobfal.basicgens.block.entity.StoneGeneratorBlockEntity;
import de.tobfal.basicgens.init.Config;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.stream.Stream;

public class StoneGeneratorBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(14, 1, 0, 15, 2, 1),
            Block.box(2, 9, 2, 14, 12, 14),
            Block.box(3, 3, 2, 13, 9, 14),
            Block.box(14, 10, 0, 15, 11, 16),
            Block.box(1, 0, 0, 15, 1, 1),
            Block.box(14, 1, 15, 15, 2, 16),
            Block.box(1, 1, 15, 2, 2, 16),
            Block.box(15, 1, 15, 16, 11, 16),
            Block.box(0, 1, 15, 1, 11, 16),
            Block.box(1, 11, 15, 15, 12, 16),
            Block.box(1, 0, 15, 15, 1, 16),
            Block.box(1, 11, 0, 15, 12, 1),
            Block.box(0, 1, 0, 1, 11, 1),
            Block.box(15, 1, 0, 16, 11, 1),
            Block.box(1, 1, 0, 2, 2, 1),
            Block.box(1, 10, 0, 2, 11, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public StoneGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter blockGetter, List<Component> components, TooltipFlag tooltipFlag) {
        if(Screen.hasShiftDown()) {
            components.add(Component.translatable("tooltip.basicgens.generator.description"));
            components.add(Component.translatable("tooltip.basicgens.generator.capacity").append(String.format(" %.0f kRF", Config.STONE_GENERATOR_CAPACITY.get()/1000f)));
            components.add(Component.translatable("tooltip.basicgens.generator.pertick").append(" " + Config.STONE_GENERATOR_PERTICK.get() + " RF/t"));
            components.add(Component.translatable("tooltip.basicgens.generator.transfer").append(" " + Config.STONE_GENERATOR_TRANSFER.get() + " RF/t"));
        } else {
            components.add(Component.translatable("tooltip.basicgens.generator"));
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new StoneGeneratorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof GeneratorBlockEntityBase) {
                ((GeneratorBlockEntityBase) blockEntity).drops();
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if(pLevel.getBlockEntity(pPos) instanceof StoneGeneratorBlockEntity entity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), entity, pPos);
            } else {
                throw new IllegalStateException("Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return ITickableBlockEntity.getTickerHelper(pLevel);
    }
}
