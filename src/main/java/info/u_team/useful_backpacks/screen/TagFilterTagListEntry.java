package info.u_team.useful_backpacks.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.gui.elements.ScrollableListEntry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TagFilterTagListEntry extends ScrollableListEntry<TagFilterTagListEntry> {
	
	private final ResourceLocation tag;
	
	public TagFilterTagListEntry(ResourceLocation tag) {
		this.tag = tag;
	}
	
	@Override
	public void render(PoseStack poseStack, int slotIndex, int entryY, int entryX, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float partialTick) {
		poseStack.pushPose();
		poseStack.scale(0.75F, 0.75F, 0.75F);
		minecraft.font.draw(poseStack, tag.toString(), entryX * 1 / 0.75f, entryY * 1 / 0.75f, 0xFF0083FF);
		poseStack.popPose();
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		getList().setSelected(this);
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	public ResourceLocation getTag() {
		return tag;
	}
	
	@Override
	public Component getNarration() {
		return CommonComponents.EMPTY;
	}
	
}
