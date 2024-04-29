package de.keksuccino.fmsia.mixin.mixins.common.client;

import de.keksuccino.fmsia.optin.OptIn;
import de.keksuccino.fmsia.optin.OptInScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {

    @Unique private static boolean optInScreenShown_FMSIA = false;

    /**
     * @reason This is to show the Opt-In screen of FMSIA. I open it in render() to cover most modded scenarios where other custom screens get opened on game launch.
     */
    @Inject(method = "render", at = @At("HEAD"))
    private void before_render_FMSIA(GuiGraphics $$0, int $$1, int $$2, float $$3, CallbackInfo info) {
        if (!optInScreenShown_FMSIA) {
            optInScreenShown_FMSIA = true;
            if (!OptIn.hasClientUserFinishedOptInProcess()) {
                Minecraft.getInstance().setScreen(new OptInScreen(new TitleScreen()));
            }
        }
    }

}
