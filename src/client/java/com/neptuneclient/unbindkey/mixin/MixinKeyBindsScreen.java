package com.neptuneclient.unbindkey.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(KeyBindsScreen.class)
public class MixinKeyBindsScreen {

    @Shadow
    @Nullable
    public KeyMapping selectedKey;

    @Redirect(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;setKey(Lcom/mojang/blaze3d/platform/InputConstants$Key;)V", ordinal = 0))
    public void handleEscapeKeyPress(KeyMapping instance, InputConstants.Key key) {
        selectedKey = null;
    }

}
