package it.nicolalopatriello.thesis.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThesisConstant {
    public static final String AUTHORIZATION = "Authorization";
    public static final String SERVER_DISCONNECTED_EXCEPTION = "ServerDisconnectedException";


    public static final String REVERTABLE_CREATE = "create";
    public static final String REVERTABLE_UPDATE = "update";
    public static final String REVERTABLE_SOFT_DELETE = "softDelete";
    public static final String REVERTABLE_RESTORE = "restoreById";
    public static final String REVERTABLE_RENAME = "rename";
    public static final String REVERTABLE_MOVE = "move";


    public static final String ROOT_FOLDER_ID = "dMzTa1614247284940";
    public static final String ROOT_FOLDER_NAME = "/";
}
