package aiard0.nephastus.machines;

import net.minecraft.world.item.ItemStack;

public abstract class BaseMachineRecipe {

    protected final ItemStack input;
    protected final ItemStack output;
    protected final int processingTime; // in ticks

    public BaseMachineRecipe(ItemStack input, ItemStack output, int processingTime) {
        this.input = input.copy();
        this.output = output.copy();
        this.processingTime = processingTime;
    }

    // Checks if the input matches the recipe
    public boolean matches(ItemStack inputStack) {
        return inputStack.is(input.getItem()) && inputStack.getCount() >= input.getCount();
    }

    // Getters
    public ItemStack getInput() {
        return input.copy();
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public abstract String getRecipeType();

    public boolean canProduce(ItemStack outputStack) {
        if (outputStack.isEmpty()) {
            return false;
        }

        return outputStack.is(output.getItem()) &&
                outputStack.getCount() + output.getCount() <= outputStack.getMaxStackSize();
    }

    public void execute(ItemStack inputStack, ItemStack outputStack) {
        // Consume the input
        inputStack.shrink(input.getCount());

        // Produce the output
        if (outputStack.isEmpty()) {
            outputStack = output.copy();
        } else {
            outputStack.grow(output.getCount());
        }
    }

}
