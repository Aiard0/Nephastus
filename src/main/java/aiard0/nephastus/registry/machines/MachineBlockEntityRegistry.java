package aiard0.nephastus.registry.machines;

import aiard0.nephastus.Nephastus;
import aiard0.nephastus.machines.entities.CrusherBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MachineBlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Nephastus.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CrusherBlockEntity>> CRUSHER_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("crusher", () ->
            BlockEntityType.Builder.of(CrusherBlockEntity::new, MachineBlockRegistry.CRUSHER_BLOCK.get())
                .build(null));
}
