package aiard0.nephastus.machines.blocks;

import aiard0.nephastus.machines.BaseMachineBlock;
import aiard0.nephastus.machines.entities.CrusherBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CrusherBlock extends BaseMachineBlock {

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrusherBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null :
                (lvl, pos, st, entity) -> {
                    if (entity instanceof CrusherBlockEntity crusher) {
                        CrusherBlockEntity.tick(lvl, pos, st, crusher);
                    }
                };
    }

}
