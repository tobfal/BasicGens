package de.tobfal.basicgens.block;

import de.tobfal.basicgens.block.entity.GeneratorBlockEntityBase;
import de.tobfal.basicgens.block.entity.GoldGeneratorBlockEntity;
import de.tobfal.basicgens.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class GoldGeneratorBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public GoldGeneratorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GoldGeneratorBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if(pLevel.getBlockEntity(pPos) instanceof GoldGeneratorBlockEntity entity) {
                NetworkHooks.openGui(((ServerPlayer)pPlayer), entity, pPos);
            } else {
                throw new IllegalStateException("Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
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
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.GOLD_GENERATOR.get(), GoldGeneratorBlockEntity::tick);
    }

}
