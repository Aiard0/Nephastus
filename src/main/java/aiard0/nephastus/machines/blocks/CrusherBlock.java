package aiard0.nephastus.machines.blocks;

import aiard0.nephastus.machines.BaseMachineBlock;
import aiard0.nephastus.machines.entities.CrusherBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CrusherBlock extends BaseMachineBlock {

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrusherBlockEntity(pos, state);
    }

}
