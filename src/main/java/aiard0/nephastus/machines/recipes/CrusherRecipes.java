package aiard0.nephastus.machines.recipes;

import aiard0.nephastus.machines.BaseMachineRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class CrusherRecipes {

    private static final List<CrusherRecipe> RECIPES = new ArrayList<>();

    // Initialize the recipes
    static {
        registerRecipes();
    }

    // Register the recipes
    private static void registerRecipes() {
        // Stone -> Gravel (100 ticks = 5 seconds)
        addRecipe(new ItemStack(Items.STONE, 1), new ItemStack(Items.GRAVEL, 1), 100);

        // Cobblestone -> Gravel (80 ticks = 4 seconds)
        addRecipe(new ItemStack(Items.COBBLESTONE, 1), new ItemStack(Items.GRAVEL, 1), 80);

        // Gravel -> Sand (60 ticks = 3 seconds)
        addRecipe(new ItemStack(Items.GRAVEL, 1), new ItemStack(Items.SAND, 1), 60);
    }

    // Add a recipe
    public static void addRecipe(ItemStack input, ItemStack output, int processingTime) {
        RECIPES.add(new CrusherRecipe(input, output, processingTime));
    }

    // Find a recipe for the input
    public static CrusherRecipe findRecipe(ItemStack inputStack) {
        for (CrusherRecipe recipe : RECIPES) {
            if (recipe.matches(inputStack)) {
                return recipe;
            }
        }
        return null;
    }

    // Verify if the input has a recipe
    public static boolean hasRecipe(ItemStack inputStack) {
        return findRecipe(inputStack) != null;
    }

    // Get all recipes
    public static List<CrusherRecipe> getRecipes() {
        return new ArrayList<>(RECIPES);
    }

    public static class CrusherRecipe extends BaseMachineRecipe {

        public CrusherRecipe(ItemStack input, ItemStack output, int processingTime) {
            super(input, output, processingTime);
        }

        @Override
        public String getRecipeType() {
            return "crusher";
        }

        @Override
        public void execute(ItemStack inputStack, ItemStack outputStack) {
            // Consume the input
            inputStack.shrink(input.getCount());

            // Produce the output
            if (outputStack.isEmpty()) {
                // If the output stack is empty, place the output
                outputStack.setCount(0); // Reset the count
                ItemStack result = output.copy();
                outputStack = result;
            } else {
                // If have items in the output stack, add more items
                outputStack.grow(output.getCount());
            }
        }

        public ItemStack executeAndReturn(ItemStack inputStack) {
            // Consume the input
            inputStack.shrink(input.getCount());

            // Produce the output
            return output.copy();
        }

    }

}
