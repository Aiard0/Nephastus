package aiard0.nephastus.creativetabs;

import aiard0.nephastus.Nephastus;
import aiard0.nephastus.registry.machines.MachineBlockRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MachineCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Nephastus.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MACHINES_TAB = CREATIVE_MODE_TABS.register("machines_tab", () -> CreativeModeTab.builder()
            // Title
            .title(Component.translatable("creativetab.nephastus.machines_tab"))
            // Icon
            .icon(() -> new ItemStack(MachineBlockRegistry.MACHINE_BLOCK))
            // Items
            .displayItems((parameters, output) -> {
                // Add Machine Block to the tab
                output.accept(MachineBlockRegistry.MACHINE_BLOCK_ITEM);
                // Add Crusher Block to the tab
                output.accept(MachineBlockRegistry.CRUSHER_BLOCK_ITEM);
            })
            // Finishing touches
            .build());

}
