package aiard0.nephastus.machines.entities;

import aiard0.nephastus.machines.BaseMachineBlockEntity;
import aiard0.nephastus.machines.recipes.CrusherRecipes;
import aiard0.nephastus.registry.machines.MachineBlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CrusherBlockEntity extends BaseMachineBlockEntity {

    // Variables for processing
    private int progress = 0;
    private int maxProgress = 100; // Based on recipe
    private boolean isProcessing = false;
    private CrusherRecipes.CrusherRecipe currentRecipe = null;

    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        super(MachineBlockEntityRegistry.CRUSHER_BLOCK_ENTITY.get(), pos, state, 1, 1);
    }

    // Method tick
    public static void tick(Level level, BlockPos blockPos, BlockState blockState, CrusherBlockEntity entity) {
        if (level.isClientSide) {
            return;
        }

        boolean wasProcessing = entity.isProcessing;

        if (entity.canProcess()) {
            if (!entity.isProcessing) {
                entity.isProcessing = true;
                entity.progress = 0;
                entity.maxProgress = entity.currentRecipe.getProcessingTime();
            }

            entity.progress++;

            if (entity.progress >= entity.maxProgress) {
                entity.processItem();
                entity.progress = 0;
                entity.isProcessing = false;
                entity.currentRecipe = null;
            }

        } else {
            entity.isProcessing = false;
            entity.progress = 0;
            entity.currentRecipe = null;
        }

        if (wasProcessing != entity.isProcessing) {
            entity.setChanged();
        }

    }

    @Override
    protected boolean canProcess() {
        ItemStack inputStack = this.getItem(0); // Input slot
        ItemStack outputStack = this.getItem(1); // Output slot

        // Check if the input has a item
        if (inputStack.isEmpty()) {
            return false;
        }

        // Encounters a recipe for the item
        CrusherRecipes.CrusherRecipe recipe = CrusherRecipes.findRecipe(inputStack);
        if (recipe == null) {
            return false;
        }

        // Verify if is possible to produce the output
        if (!recipe.canProduce(outputStack)) {
            return false;
        }

        // Update the recipe
        this.currentRecipe = recipe;
        return true;
    }

    private void processItem() {
        if (!canProcess() || this.currentRecipe == null) {
            return;
        }

        ItemStack inputStack = this.getItem(0);
        ItemStack outputStack = this.getItem(1);

        // Execute the recipe
        ItemStack result = currentRecipe.executeAndReturn(inputStack);

        // Place the result in the output slot
        if (!outputStack.isEmpty()) {
            setItem(1, outputStack);
        } else {
            outputStack.grow(result.getCount());
        }

        setChanged();
    }

    @Override
    public void clearContent() {
        this.items.clear();
        this.currentRecipe = null;
    }

    // NBT Serialization
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        this.progress = tag.getInt("Progress");
        this.maxProgress = tag.getInt("MaxProgress");
        this.isProcessing = tag.getBoolean("IsProcessing");
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putInt("Progress", this.progress);
        tag.putInt("MaxProgress", this.maxProgress);
        tag.putBoolean("IsProcessing", this.isProcessing);
    }

    // Getters for GUI
    public int getProgress() {
        return this.progress;
    }

    public int getMaxProgress() {
        return this.maxProgress;
    }

    public boolean isProcessing() {
        return this.isProcessing;
    }

    public float getProgressPercent() {
        return maxProgress > 0 ? (float) progress / (float) maxProgress : 0.0f;
    }
}
