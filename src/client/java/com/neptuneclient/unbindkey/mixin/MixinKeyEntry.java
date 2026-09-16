package com.neptuneclient.unbindkey.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(KeyBindsList.KeyEntry.class)
public abstract class MixinKeyEntry {

    @Shadow
    @Final
    private Button resetButton;
    @Shadow
    @Final
    private KeyMapping key;
    @Shadow
    @Final
    private KeyBindsList this$0;
    @Shadow
    @Final
    private Button changeButton;
    private SpriteIconButton clearButton;

    @Unique
    private void clearKeyBind(Button button) {
        key.setKey(InputConstants.UNKNOWN);
        this$0.resetMappingAndUpdateButtons();
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/options/controls/KeyBindsList$KeyEntry;refreshEntry()V"))
    public void initializeClearButton(KeyBindsList this$0, KeyMapping key, Component name, CallbackInfo ci) {
        clearButton = SpriteIconButton.builder(Component.literal("Reset"), this::clearKeyBind, true)
                .width(20)
                .sprite(Identifier.fromNamespaceAndPath("unbindkey", "icon/clear"), 15, 15)
                .build();
    }

    @ModifyVariable(method = "extractContent", at = @At("STORE"), name = "resetButtonX")
    public int modifyResetButtonX(int resetButtonX) {
        return resetButtonX - 25;
    }

    @Inject(method = "extractContent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V"))
    public void clearButtonExtractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a, CallbackInfo ci) {
        clearButton.setPosition(resetButton.getX() + 50 + 2, resetButton.getY());
        clearButton.extractRenderState(graphics, mouseX, mouseY, a);
    }

    @Inject(method = "refreshEntry", at = @At("HEAD"))
    public void updateClearButtonActiveState(CallbackInfo ci) {
        clearButton.active = !key.isUnbound();
    }

    /**
     * @author Neptune
     * @reason include the clear button
     */
    @Overwrite
    public List<? extends GuiEventListener> children() {
        return ImmutableList.of(changeButton, resetButton, clearButton);
    }

    /**
     * @author Neptune
     * @reason include the clear button
     */
    @Overwrite
    public List<? extends GuiEventListener> narratables() {
        return ImmutableList.of(changeButton, resetButton, clearButton);
    }

}
