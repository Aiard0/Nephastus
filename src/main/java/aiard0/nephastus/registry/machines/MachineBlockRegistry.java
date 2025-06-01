package aiard0.nephastus.registry.machines;

import aiard0.nephastus.machines.MachineBlock;
import aiard0.nephastus.machines.blocks.CrusherBlock;

import aiard0.nephastus.Nephastus;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MachineBlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Nephastus.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Nephastus.MODID);

    public static final DeferredBlock<Block> MACHINE_BLOCK =
            BLOCKS.register("machine_block", MachineBlock::new);
    public static final DeferredItem<BlockItem> MACHINE_BLOCK_ITEM =
            ITEMS.register("machine_block", () -> new BlockItem(MACHINE_BLOCK.get(), new Item.Properties()));

    public static final DeferredBlock<Block> CRUSHER_BLOCK =
            BLOCKS.register("crusher_block", CrusherBlock::new);
    public static final DeferredItem<BlockItem> CRUSHER_BLOCK_ITEM =
            ITEMS.register("crusher_block", () -> new BlockItem(CRUSHER_BLOCK.get(), new Item.Properties()));
}
