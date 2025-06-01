package aiard0.nephastus.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseMachineBlockEntity extends BlockEntity implements WorldlyContainer {

    protected NonNullList<ItemStack> items;
    protected int inputSlots;
    protected int outputSlots;
    protected Map<Direction, int[]> slotAccess = new HashMap<>();

    public BaseMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int InputSlots, int OutputSlots) {
        super(type, pos, state);
        this.inputSlots = InputSlots;
        this.outputSlots = OutputSlots;
        this.items = NonNullList.withSize(inputSlots + outputSlots, ItemStack.EMPTY);
        setupDefaultSlotAccess();
    }

    protected void setupDefaultSlotAccess() {
        // Per default, all slots are accessible
        int[] inputSlotArray = new int[inputSlots];
        for (int i = 0; i < inputSlots; i++) {
            inputSlotArray[i] = i;
        }

        // Bottom slot access only output slots
        int[] outputSlotArray = new int[outputSlots];
        for (int i = 0; i < outputSlots; i++) {
            outputSlotArray[i] = i + inputSlots;
        }

        // Top slot access all slots
        int[] allSlots = new int[inputSlots + outputSlots];
        for (int i = 0; i < allSlots.length; i++) {
            allSlots[i] = i;
        }

        // Setup slot access per direction
        slotAccess.put(Direction.UP, allSlots);
        slotAccess.put(Direction.DOWN, outputSlotArray);

        // Sides
        for (Direction direction : Direction.values()) {
            if (direction != Direction.UP && direction != Direction.DOWN) {
                slotAccess.put(direction, inputSlotArray);
            }
        }
    }

    // Default method for containers

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : items) {
            if (!itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack result = ContainerHelper.removeItem(items, slot, amount);
        if (!result.isEmpty()) {
            setChanged();
        }
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > stack.getMaxStackSize()) {
            stack.setCount(stack.getMaxStackSize());
        }
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        assert level != null;
        if (level.getBlockEntity(worldPosition) != this) {
            return false;
        }
        return player.distanceToSqr(
                worldPosition.getX() + 0.5D,
                worldPosition.getY() + 0.5D,
                worldPosition.getZ() + 0.5D
        ) <= 64.0D;
    }

    // Methods for WorldlyContainer
    @Override
    public int[] getSlotsForFace(Direction side) {
        return slotAccess.getOrDefault(side, new int[0]);
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
        return slot < inputSlots;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        return slot >= inputSlots;
    }

    // NBT Serialization
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registries);

        if (tag.contains("InputSlots")) {
            this.inputSlots = tag.getInt("InputSlots");
        }
        if (tag.contains("OutputSlots")) {
            this.outputSlots = tag.getInt("OutputSlots");
        }

        // Rebuild slotAccess
        setupDefaultSlotAccess();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        ContainerHelper.saveAllItems(tag, this.items, registries);

        tag.putInt("InputSlots", this.inputSlots);
        tag.putInt("OutputSlots", this.outputSlots);
    }

    // Util methods
    protected boolean hasSpaceInOutput() {
        for (int i = inputSlots; i < items.size(); i++) {
            if (items.get(i).isEmpty() || items.get(i).getCount() < items.get(i).getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }

    protected abstract boolean canProcess();

    // Getters for Subclasses

}
