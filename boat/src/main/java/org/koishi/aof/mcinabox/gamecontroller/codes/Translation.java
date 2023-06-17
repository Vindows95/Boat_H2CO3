package org.koishi.aof.mcinabox.gamecontroller.codes;

import org.koishi.aof.mcinabox.gamecontroller.definitions.id.key.KeyEvent;

public class Translation {

    private final CoKeyMap lwjglKeyTrans;
    private final CoKeyMap glfwKeyTrans;
    private final CoKeyMap xKeyMap;
    private final CoKeyMap aKeyMap;
    private int mode;

    public Translation(int mode) {
        lwjglKeyTrans = new LwjglKeyMap();
        glfwKeyTrans = new GlfwKeyMap();
        xKeyMap = new XKeyMap();
        aKeyMap = new AndroidKeyMap();
        this.mode = mode;
    }

    public short trans(String s) {
        switch (mode) {
//            case KEYMAP_TO_LWJGL:
//                return (int) lwjglKeyTrans.translate(s);
//            case KEYMAP_TO_GLFW:
//                return (int) glfwKeyTrans.translate(s);
            case KeyEvent.KEYMAP_TO_X:
                return (short) xKeyMap.translate(s);
            default:
                return -1;
        }
    }

    public String trans(int i) {
        switch (mode) {
            case KeyEvent.ANDROID_TO_KEYMAP:
                return (String) aKeyMap.translate(i);
            default:
                return null;
        }
    }

    public Translation setMode(int mode) {
        this.mode = mode;
        return this;
    }
}
