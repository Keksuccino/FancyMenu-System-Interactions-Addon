package de.keksuccino.fmsia.customization.actions.file;

import de.keksuccino.fancymenu.customization.action.Action;
import de.keksuccino.fancymenu.util.LocalizationUtils;
import de.keksuccino.fancymenu.util.rendering.text.Components;
import de.keksuccino.fmsia.optin.OptIn;
import de.keksuccino.konkrete.file.FileUtils;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UnpackZipAction extends Action {

    public UnpackZipAction() {
        super("unpackzip");
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public void execute(@Nullable String value) {
        if (!OptIn.isAddonEnabledForClientUser()) return;
        if (value != null) {
            if (value.contains(";")) {
                try {
                    String zipPath = cleanPath(value.split(";", 2)[0]);
                    String outputDir = cleanPath(value.split(";", 2)[1]);
                    FileUtils.unpackZip(zipPath, outputDir);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Removes all spaces from the beginning of the path and replaces all backslash characters with normal slash characters.
     */
    private static String cleanPath(String path) {
        int i = 0;
        for (char c : path.toCharArray()) {
            if (c == ' ') {
                i++;
            } else {
                break;
            }
        }
        if (i <= path.length()) {
            return path.substring(i).replace("\\", "/");
        }
        return "";
    }

    @Override
    public @NotNull Component getActionDisplayName() {
        return Components.translatable("fmsia.actions.unpackzip");
    }

    @Override
    public @NotNull Component[] getActionDescription() {
        return LocalizationUtils.splitLocalizedLines("fmsia.actions.unpackzip.desc");
    }

    @Override
    public Component getValueDisplayName() {
        return Components.translatable("fmsia.actions.unpackzip.desc.value");
    }

    @Override
    public String getValueExample() {
        return "some/path/example.zip;export/to/example/";
    }

}
