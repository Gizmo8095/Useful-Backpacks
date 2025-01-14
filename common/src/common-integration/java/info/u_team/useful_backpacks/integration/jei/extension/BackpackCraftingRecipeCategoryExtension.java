package info.u_team.useful_backpacks.integration.jei.extension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import info.u_team.u_team_core.api.dye.DyeableItem;
import info.u_team.u_team_core.util.ColorUtil;
import info.u_team.u_team_core.util.RGB;
import info.u_team.useful_backpacks.recipe.BackpackCraftingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class BackpackCraftingRecipeCategoryExtension implements ICraftingCategoryExtension {
	
	private final BackpackCraftingRecipe recipe;
	
	public BackpackCraftingRecipeCategoryExtension(BackpackCraftingRecipe recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public ResourceLocation getRegistryName() {
		return recipe.getId();
	}
	
	@Override
	public int getWidth() {
		return recipe.getWidth();
	}
	
	@Override
	public int getHeight() {
		return recipe.getHeight();
	}
	
	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
		final List<List<ItemStack>> inputs = recipe.getIngredients().stream().map(ingredient -> Lists.newArrayList(ingredient.getItems())).collect(Collectors.toCollection(ArrayList::new));
		final List<ItemStack> outputs = Lists.newArrayList(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
		
		final AtomicBoolean changed = new AtomicBoolean(false);
		
		focuses.getItemStackFocuses(RecipeIngredientRole.OUTPUT) //
				.map(focus -> focus.getTypedValue().getIngredient()) //
				.findFirst().ifPresent(outputStack -> {
					if (outputStack.getItem() instanceof DyeableItem dyeable) {
						final int dyeableColor = dyeable.getColor(outputStack);
						final DyeColor color = ColorUtil.findClosestDyeColor(new RGB(dyeableColor));
						final Block wool = ColorUtil.getWoolFromColor(color);
						for (int index = 0; index < inputs.size(); index++) {
							final List<ItemStack> list = inputs.get(index);
							if (list.stream().allMatch(stack -> stack.is(ItemTags.WOOL))) {
								if (dyeableColor != dyeable.getDefaultColor()) {
									inputs.set(index, List.of(new ItemStack(wool)));
								} else {
									inputs.set(index, List.of(new ItemStack(Blocks.WHITE_WOOL)));
								}
							}
						}
						outputs.set(0, DyeableItem.colorStack(outputs.get(0), List.of(color)));
						changed.set(true);
					}
				});
		
		focuses.getItemStackFocuses(RecipeIngredientRole.INPUT) //
				.map(focus -> focus.getTypedValue().getIngredient()) //
				.findFirst().ifPresent(inputStack -> {
					if (inputStack.is(ItemTags.WOOL)) {
						final DyeColor color = ColorUtil.getColorFromWool(inputStack.getItem());
						if (color != null && color != DyeColor.WHITE) {
							outputs.set(0, DyeableItem.colorStack(outputs.get(0), List.of(color)));
						}
						changed.set(true);
					}
				});
		
		if (!changed.get()) {
			Stream.of(DyeColor.values()).map(color -> {
				if (color == DyeColor.WHITE) {
					return outputs.get(0);
				}
				return DyeableItem.colorStack(outputs.get(0), List.of(color));
			}).forEach(outputs::add);
			outputs.remove(0);
		}
		
		craftingGridHelper.createAndSetInputs(builder, VanillaTypes.ITEM_STACK, inputs, getWidth(), getHeight());
		craftingGridHelper.createAndSetOutputs(builder, VanillaTypes.ITEM_STACK, outputs);
	}
	
}
