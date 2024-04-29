package de.keksuccino.fmsia;

import java.io.File;
import de.keksuccino.fancymenu.customization.ScreenCustomization;
import de.keksuccino.fmsia.customization.actions.Actions;
import de.keksuccino.fmsia.platform.Services;
import de.keksuccino.fancymenu.util.file.FileUtils;
import de.keksuccino.fancymenu.util.file.GameDirectoryUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class FMSIA {

	private static final Logger LOGGER = LogManager.getLogger();

	public static final String VERSION = "1.0.0";
	public static final String MOD_LOADER = Services.PLATFORM.getPlatformName();
	public static final String MOD_ID = "fmsia";
	public static final String MSG_PREFIX = "[FANCYMENU SYSTEM INTERACTIONS ADDON]";

	public static final File MOD_DIR = createDirectory(new File(GameDirectoryUtils.getGameDirectory(), "/config/fmsia/"));

	private static boolean initialized = false;

	public static void earlyInit() {

		if (initialized) return;
		initialized = true;

		if (Services.PLATFORM.isOnClient()) {
			LOGGER.info(MSG_PREFIX + " Loading v" + VERSION + " in client-side mode on " + MOD_LOADER.toUpperCase() + "!");
		} else {
			LOGGER.info(MSG_PREFIX + " Loading v" + VERSION + " in server-side mode on " + MOD_LOADER.toUpperCase() + "!");
		}

		if (Services.PLATFORM.isOnClient()) {

			//Disable customization for all FMSIA screens
			ScreenCustomization.addScreenBlacklistRule(s -> s.startsWith("de.keksuccino.fmsia."));

			Actions.registerAll();

		}

	}

	private static File createDirectory(@NotNull File directory) {
		return FileUtils.createDirectory(directory);
	}

}
