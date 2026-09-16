package com.neptuneclient.unbindkey.mixin;

import net.minecraft.client.gui.screens.options.controls.KeyBindsList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(KeyBindsList.class)
public class MixinKeyBindsList {

    /**
     * @author Neptune
     * @reason Make space for the clear button
     */
    @Overwrite
    public int getRowWidth() {
        return 340 + 20 + 2;
    }

}
