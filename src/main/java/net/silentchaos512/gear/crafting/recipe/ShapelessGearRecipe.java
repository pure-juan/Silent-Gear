package net.silentchaos512.gear.crafting.recipe;

import com.google.gson.JsonParseException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.util.GearData;
import net.silentchaos512.gear.util.GearHelper;
import net.silentchaos512.lib.crafting.recipe.ExtendedShapelessRecipe;

public final class ShapelessGearRecipe extends ExtendedShapelessRecipe implements IGearRecipe {
    public static final ResourceLocation NAME = SilentGear.getId("gear_crafting");
    public static final Serializer<ShapelessGearRecipe> SERIALIZER = Serializer.basic(ShapelessGearRecipe::new);

    private final ICoreItem item;

    private ShapelessGearRecipe(ShapelessRecipe recipe) {
        super(recipe);

        ItemStack output = recipe.getRecipeOutput();
        if (!(output.getItem() instanceof ICoreItem)) {
            throw new JsonParseException("result is not a gear item: " + output);
        }
        this.item = (ICoreItem) output.getItem();
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        if (!this.getBaseRecipe().matches(inv, worldIn)) return false;

        GearType gearType = item.getGearType();
        return getParts(inv).stream().allMatch(part -> part.isCraftingAllowed(gearType, inv));
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return item.construct(getParts(inv));
    }

    @Override
    public ICoreItem getOutputItem() {
        return item;
    }

    @Override
    public ItemStack getRecipeOutput() {
        // Create an example item, so we're not just showing a broken item
        ItemStack result = item.construct(GearHelper.getExamplePartsFromRecipe(this.item.getGearType(), getIngredients()));
        GearData.setExampleTag(result, true);
        return result;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }
}
