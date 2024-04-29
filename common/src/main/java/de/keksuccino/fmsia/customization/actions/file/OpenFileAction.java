package de.keksuccino.fmsia.customization.actions.file;

import de.keksuccino.fancymenu.customization.action.Action;
import de.keksuccino.fancymenu.util.LocalizationUtils;
import de.keksuccino.fancymenu.util.file.FileUtils;
import de.keksuccino.fancymenu.util.rendering.text.Components;
import de.keksuccino.fmsia.optin.OptIn;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class OpenFileAction extends Action {

    public OpenFileAction() {
        super("openfile");
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public void execute(@Nullable String value) {
        if (!OptIn.isAddonEnabledForClientUser()) return;
        if (value != null) {
            File f = new File(value.replace("\\", "/"));
            if (f.exists()) {
                FileUtils.openFile(f);
            }
        }
    }

    @Override
    public @NotNull Component getActionDisplayName() {
        return Components.translatable("fmsia.actions.openfile");
    }

    @Override
    public @NotNull Component[] getActionDescription() {
        return LocalizationUtils.splitLocalizedLines("fmsia.actions.openfile.desc");
    }

    @Override
    public Component getValueDisplayName() {
        return Components.translatable("fmsia.actions.openfile.desc.value");
    }

    @Override
    public String getValueExample() {
        return "config/fancymenu/example.txt";
    }

}
