package de.keksuccino.fmsia.optin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.keksuccino.fancymenu.util.file.FileUtils;
import de.keksuccino.fmsia.FMSIA;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.*;

public class OptIn {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final File OPTIN_USERS_FILE = new File(FMSIA.MOD_DIR + "/users.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<String, OptInUser> USERS = new HashMap<>();

    private static boolean initialized = false;

    private static void readFile() {

        try {

            USERS.clear();

            if (!OPTIN_USERS_FILE.isFile()) {
                OPTIN_USERS_FILE.createNewFile();
            }

            List<String> lines = FileUtils.readTextLinesFrom(OPTIN_USERS_FILE);
            if (!lines.isEmpty()) {
                StringBuilder linesString = new StringBuilder();
                for (String line : lines) {
                    if (!linesString.isEmpty()) {
                        linesString.append("\n");
                    }
                    linesString.append(line);
                }
                List<OptInUser> usersList = GSON.fromJson(linesString.toString(), new TypeToken<List<OptInUser>>(){});
                usersList.forEach(optInUser -> {
                    if (optInUser.getUUID() != null) USERS.put(optInUser.getUUID().toString(), optInUser);
                });
            }

        } catch (Exception ex) {
            LOGGER.error(FMSIA.MSG_PREFIX + " Failed to read opt-in user file! Disabling addon..", ex);
            USERS.clear();
        }

    }

    private static void writeFile() {

        try {

            if (!OPTIN_USERS_FILE.isFile()) {
                OPTIN_USERS_FILE.createNewFile();
            }

            String jsonString = GSON.toJson(new ArrayList<>(USERS.values()));
            FileUtils.writeTextToFile(OPTIN_USERS_FILE, false, jsonString);

        } catch (Exception ex) {
            LOGGER.error(FMSIA.MSG_PREFIX + " Failed to write opt-in user file! Disabling addon..", ex);
            USERS.clear();
        }

    }

    protected static void putUser(@NotNull UUID uuid, boolean enableAddon) {
        USERS.put(uuid.toString(), new OptInUser(uuid, enableAddon));
        writeFile();
    }

    public static boolean hasUserFinishedOptInProcess(@NotNull UUID uuid) {
        if (!initialized) {
            initialized = true;
            readFile();
        }
        return (USERS.get(uuid.toString()) != null);
    }

    public static boolean hasClientUserFinishedOptInProcess() {
        try {
            return hasUserFinishedOptInProcess(Minecraft.getInstance().getUser().getProfileId());
        } catch (Exception ex) {
            LOGGER.error(FMSIA.MSG_PREFIX + " Failed to get client user opt-in status!", ex);
        }
        return false;
    }

    public static boolean isAddonEnabledForUser(@NotNull UUID uuid) {
        if (!initialized) {
            initialized = true;
            readFile();
        }
        OptInUser user = USERS.get(uuid.toString());
        if (user != null) return user.isAddonEnabled();
        return false;
    }

    public static boolean isAddonEnabledForClientUser() {
        try {
            return isAddonEnabledForUser(Minecraft.getInstance().getUser().getProfileId());
        } catch (Exception ex) {
            LOGGER.error(FMSIA.MSG_PREFIX + " Failed to get client user opt-in status!", ex);
        }
        return false;
    }

}
