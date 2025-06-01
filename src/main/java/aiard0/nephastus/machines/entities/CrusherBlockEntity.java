package aiard0.nephastus.machines.entities;

import aiard0.nephastus.machines.BaseMachineBlockEntity;
import aiard0.nephastus.registry.machines.MachineBlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CrusherBlockEntity extends BaseMachineBlockEntity {

    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        super(MachineBlockEntityRegistry.CRUSHER_BLOCK_ENTITY.get(), pos, state);
    }

}
