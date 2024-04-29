package de.keksuccino.fmsia.customization.actions.file;

import de.keksuccino.fancymenu.customization.action.Action;
import de.keksuccino.fancymenu.util.LocalizationUtils;
import de.keksuccino.fancymenu.util.WebUtils;
import de.keksuccino.fancymenu.util.rendering.text.Components;
import de.keksuccino.fmsia.optin.OptIn;
import de.keksuccino.konkrete.input.StringUtils;
import net.minecraft.network.chat.Component;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class DownloadFileAction extends Action {

    public DownloadFileAction() {
        super("downloadfile");
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
                    String url = StringUtils.convertFormatCodes(cleanPath(value.split(";", 2)[0]), "§", "&");
                    if (!WebUtils.isValidUrl(url)) return;
                    String path = cleanPath(value.split(";", 2)[1]);
                    File downloadTo = new File(path);
                    InputStream in = new URL(url).openStream();
                    FileUtils.copyInputStreamToFile(in, downloadTo);
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
        return Components.translatable("fmsia.actions.downloadfile");
    }

    @Override
    public @NotNull Component[] getActionDescription() {
        return LocalizationUtils.splitLocalizedLines("fmsia.actions.downloadfile.desc");
    }

    @Override
    public Component getValueDisplayName() {
        return Components.translatable("fmsia.actions.downloadfile.desc.value");
    }

    @Override
    public String getValueExample() {
        return "some/path/example.png;new_name.png";
    }

}
