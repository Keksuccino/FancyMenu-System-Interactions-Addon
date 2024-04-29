package de.keksuccino.fmsia.customization.actions;

import de.keksuccino.fancymenu.customization.action.ActionRegistry;
import de.keksuccino.fmsia.customization.actions.file.*;
import de.keksuccino.fmsia.customization.actions.other.ExecuteTerminalCommandAction;

public class Actions {

    public static final CopyFileAction COPY_FILE = new CopyFileAction();
    public static final DeleteFileAction DELETE_FILE = new DeleteFileAction();
    public static final DownloadFileAction DOWNLOAD_FILE = new DownloadFileAction();
    public static final MoveFileAction MOVE_FILE = new MoveFileAction();
    public static final OpenFileAction OPEN_FILE = new OpenFileAction();
    public static final RenameFileAction RENAME_FILE = new RenameFileAction();
    public static final UnpackZipAction UNPACK_ZIP = new UnpackZipAction();
    public static final ExecuteTerminalCommandAction EXECUTE_TERMINAL_COMMAND = new ExecuteTerminalCommandAction();

    public static void registerAll() {

        //FILE
        ActionRegistry.register(COPY_FILE);
        ActionRegistry.register(DELETE_FILE);
        ActionRegistry.register(DOWNLOAD_FILE);
        ActionRegistry.register(MOVE_FILE);
        ActionRegistry.register(OPEN_FILE);
        ActionRegistry.register(RENAME_FILE);
        ActionRegistry.register(UNPACK_ZIP);

        //OTHER
        ActionRegistry.register(EXECUTE_TERMINAL_COMMAND);

    }

}
