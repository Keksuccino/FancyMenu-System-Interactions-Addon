package de.keksuccino.fmsia.customization.actions.file;

import de.keksuccino.fancymenu.customization.action.Action;
import de.keksuccino.fancymenu.util.LocalizationUtils;
import de.keksuccino.fmsia.optin.OptIn;
import net.minecraft.network.chat.Component;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class DeleteFileAction extends Action {

    public DeleteFileAction() {
        super("deletefile");
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public void execute(@Nullable String value) {
        if (!OptIn.isAddonEnabledForClientUser()) return;
        if (value != null) {
            try {
                File f = new File(value.replace("\\", "/"));
                if (f.isFile()) {
                    FileUtils.delete(f);
                } else if (f.isDirectory()) {
                    FileUtils.deleteDirectory(f);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public @NotNull Component getActionDisplayName() {
        return Component.translatable("fmsia.actions.deletefile");
    }

    @Override
    public @NotNull Component[] getActionDescription() {
        return LocalizationUtils.splitLocalizedLines("fmsia.actions.deletefile.desc");
    }

    @Override
    public Component getValueDisplayName() {
        return Component.translatable("fmsia.actions.deletefile.desc.value");
    }

    @Override
    public String getValueExample() {
        return "some/path/example.png";
    }

}
