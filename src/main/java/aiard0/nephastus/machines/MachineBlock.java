package aiard0.nephastus.machines;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class MachineBlock extends Block {
    public MachineBlock() {
        super(BlockBehaviour.Properties.of()
                        .mapColor(MapColor.METAL)
                        .strength(3.0F, 6.0F)
                        .requiresCorrectToolForDrops()
                );
    }
}
