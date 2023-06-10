package com.aof.mcinabox.gamecontroller.codes;

import com.aof.mcinabox.gamecontroller.definitions.map.KeyMap;

import java.util.HashMap;

import static com.aof.mcinabox.gamecontroller.codes.BoatMousecodes.BOAT_MOUSE_BUTTON_left;
import static com.aof.mcinabox.gamecontroller.codes.BoatMousecodes.BOAT_MOUSE_BUTTON_middle;
import static com.aof.mcinabox.gamecontroller.codes.BoatMousecodes.BOAT_MOUSE_BUTTON_right;
import static com.aof.mcinabox.gamecontroller.codes.BoatMousecodes.BOAT_MOUSE_WHEEL_down;
import static com.aof.mcinabox.gamecontroller.codes.BoatMousecodes.BOAT_MOUSE_WHEEL_up;
import static com.aof.mcinabox.gamecontroller.definitions.map.KeyMap.*;
import static com.aof.mcinabox.gamecontroller.definitions.map.MouseMap.MOUSEMAP_BUTTON_LEFT;
import static com.aof.mcinabox.gamecontroller.definitions.map.MouseMap.MOUSEMAP_BUTTON_MIDDLE;
import static com.aof.mcinabox.gamecontroller.definitions.map.MouseMap.MOUSEMAP_BUTTON_RIGHT;
import static com.aof.mcinabox.gamecontroller.definitions.map.MouseMap.MOUSEMAP_WHEEL_DOWN;
import static com.aof.mcinabox.gamecontroller.definitions.map.MouseMap.MOUSEMAP_WHEEL_UP;

public class XKeyMap implements KeyMap, CoKeyMap {

    private final HashMap<String, Short> xKeyMap;

    public XKeyMap() {
        xKeyMap = new HashMap<>();
        init();
    }

    private void init() {
        xKeyMap.put(KEYMAP_KEY_0, GLFW_KEY_0);
        xKeyMap.put(KEYMAP_KEY_1, GLFW_KEY_1);
        xKeyMap.put(KEYMAP_KEY_2, GLFW_KEY_2);
        xKeyMap.put(KEYMAP_KEY_3, GLFW_KEY_3);
        xKeyMap.put(KEYMAP_KEY_4, GLFW_KEY_4);
        xKeyMap.put(KEYMAP_KEY_5, GLFW_KEY_5);
        xKeyMap.put(KEYMAP_KEY_6, GLFW_KEY_6);
        xKeyMap.put(KEYMAP_KEY_7, GLFW_KEY_7);
        xKeyMap.put(KEYMAP_KEY_8, GLFW_KEY_8);
        xKeyMap.put(KEYMAP_KEY_9, GLFW_KEY_9);
        xKeyMap.put(KEYMAP_KEY_A, GLFW_KEY_A);
        xKeyMap.put(KEYMAP_KEY_B, GLFW_KEY_B);
        xKeyMap.put(KEYMAP_KEY_C, GLFW_KEY_C);
        xKeyMap.put(KEYMAP_KEY_D, GLFW_KEY_D);
        xKeyMap.put(KEYMAP_KEY_E, GLFW_KEY_E);
        xKeyMap.put(KEYMAP_KEY_F, GLFW_KEY_F);
        xKeyMap.put(KEYMAP_KEY_G, GLFW_KEY_G);
        xKeyMap.put(KEYMAP_KEY_H, GLFW_KEY_H);
        xKeyMap.put(KEYMAP_KEY_I, GLFW_KEY_I);
        xKeyMap.put(KEYMAP_KEY_J, GLFW_KEY_J);
        xKeyMap.put(KEYMAP_KEY_K, GLFW_KEY_K);
        xKeyMap.put(KEYMAP_KEY_L, GLFW_KEY_L);
        xKeyMap.put(KEYMAP_KEY_M, GLFW_KEY_M);
        xKeyMap.put(KEYMAP_KEY_N, GLFW_KEY_N);
        xKeyMap.put(KEYMAP_KEY_O, GLFW_KEY_O);
        xKeyMap.put(KEYMAP_KEY_P, GLFW_KEY_P);
        xKeyMap.put(KEYMAP_KEY_Q, GLFW_KEY_Q);
        xKeyMap.put(KEYMAP_KEY_R, GLFW_KEY_R);
        xKeyMap.put(KEYMAP_KEY_S, GLFW_KEY_S);
        xKeyMap.put(KEYMAP_KEY_T, GLFW_KEY_T);
        xKeyMap.put(KEYMAP_KEY_U, GLFW_KEY_U);
        xKeyMap.put(KEYMAP_KEY_V, GLFW_KEY_V);
        xKeyMap.put(KEYMAP_KEY_W, GLFW_KEY_W);
        xKeyMap.put(KEYMAP_KEY_X, GLFW_KEY_X);
        xKeyMap.put(KEYMAP_KEY_Y, GLFW_KEY_Y);
        xKeyMap.put(KEYMAP_KEY_Z, GLFW_KEY_Z);
        xKeyMap.put(KEYMAP_KEY_MINUS, GLFW_KEY_MINUS);
        xKeyMap.put(KEYMAP_KEY_EQUALS, GLFW_KEY_EQUAL);
        xKeyMap.put(KEYMAP_KEY_LBRACKET, GLFW_KEY_LEFT_BRACKET);
        xKeyMap.put(KEYMAP_KEY_RBRACKET, GLFW_KEY_RIGHT_BRACKET);
        xKeyMap.put(KEYMAP_KEY_SEMICOLON, GLFW_KEY_SEMICOLON);
        xKeyMap.put(KEYMAP_KEY_APOSTROPHE, GLFW_KEY_APOSTROPHE);
        xKeyMap.put(KEYMAP_KEY_GRAVE, GLFW_KEY_GRAVE_ACCENT);
        xKeyMap.put(KEYMAP_KEY_BACKSLASH, GLFW_KEY_BACKSLASH);
        xKeyMap.put(KEYMAP_KEY_COMMA, GLFW_KEY_COMMA);
        xKeyMap.put(KEYMAP_KEY_PERIOD, GLFW_KEY_PERIOD);
        xKeyMap.put(KEYMAP_KEY_SLASH, GLFW_KEY_SLASH);
        xKeyMap.put(KEYMAP_KEY_ESC, GLFW_KEY_ESCAPE);
        xKeyMap.put(KEYMAP_KEY_F1, GLFW_KEY_F1);
        xKeyMap.put(KEYMAP_KEY_F2, GLFW_KEY_F2);
        xKeyMap.put(KEYMAP_KEY_F3, GLFW_KEY_F3);
        xKeyMap.put(KEYMAP_KEY_F4, GLFW_KEY_F4);
        xKeyMap.put(KEYMAP_KEY_F5, GLFW_KEY_F5);
        xKeyMap.put(KEYMAP_KEY_F6, GLFW_KEY_F6);
        xKeyMap.put(KEYMAP_KEY_F7, GLFW_KEY_F7);
        xKeyMap.put(KEYMAP_KEY_F8, GLFW_KEY_F8);
        xKeyMap.put(KEYMAP_KEY_F9, GLFW_KEY_F9);
        xKeyMap.put(KEYMAP_KEY_F10, GLFW_KEY_F10);
        xKeyMap.put(KEYMAP_KEY_F11, GLFW_KEY_F11);
        xKeyMap.put(KEYMAP_KEY_F12, GLFW_KEY_F12);
        xKeyMap.put(KEYMAP_KEY_TAB, GLFW_KEY_TAB);
        xKeyMap.put(KEYMAP_KEY_BACKSPACE, GLFW_KEY_BACKSPACE);
        xKeyMap.put(KEYMAP_KEY_SPACE, GLFW_KEY_SPACE);
        xKeyMap.put(KEYMAP_KEY_CAPITAL, GLFW_KEY_CAPS_LOCK);
        xKeyMap.put(KEYMAP_KEY_ENTER, GLFW_KEY_ENTER);
        xKeyMap.put(KEYMAP_KEY_LSHIFT, GLFW_KEY_LEFT_SHIFT);
        xKeyMap.put(KEYMAP_KEY_LCTRL, GLFW_KEY_LEFT_CONTROL);
        xKeyMap.put(KEYMAP_KEY_LALT, GLFW_KEY_LEFT_ALT);
        xKeyMap.put(KEYMAP_KEY_RSHIFT, GLFW_KEY_RIGHT_SHIFT);
        xKeyMap.put(KEYMAP_KEY_RCTRL, GLFW_KEY_RIGHT_CONTROL);
        xKeyMap.put(KEYMAP_KEY_RALT, GLFW_KEY_RIGHT_ALT);
        xKeyMap.put(KEYMAP_KEY_UP, GLFW_KEY_UP);
        xKeyMap.put(KEYMAP_KEY_DOWN, GLFW_KEY_DOWN);
        xKeyMap.put(KEYMAP_KEY_LEFT, GLFW_KEY_LEFT);
        xKeyMap.put(KEYMAP_KEY_RIGHT, GLFW_KEY_RIGHT);
        xKeyMap.put(KEYMAP_KEY_PAGEUP, GLFW_KEY_PAGE_UP);
        xKeyMap.put(KEYMAP_KEY_PAGEDOWN, GLFW_KEY_PAGE_DOWN);
        xKeyMap.put(KEYMAP_KEY_HOME, GLFW_KEY_HOME);
        xKeyMap.put(KEYMAP_KEY_END, GLFW_KEY_END);
        xKeyMap.put(KEYMAP_KEY_INSERT, GLFW_KEY_INSERT);
        xKeyMap.put(KEYMAP_KEY_DELETE, GLFW_KEY_DELETE);
        xKeyMap.put(KEYMAP_KEY_PAUSE, GLFW_KEY_PAUSE);
        xKeyMap.put(KEYMAP_KEY_NUMPAD0, GLFW_KEY_KP_0);
        xKeyMap.put(KEYMAP_KEY_NUMPAD1, GLFW_KEY_KP_1);
        xKeyMap.put(KEYMAP_KEY_NUMPAD2, GLFW_KEY_KP_2);
        xKeyMap.put(KEYMAP_KEY_NUMPAD3, GLFW_KEY_KP_3);
        xKeyMap.put(KEYMAP_KEY_NUMPAD4, GLFW_KEY_KP_4);
        xKeyMap.put(KEYMAP_KEY_NUMPAD5, GLFW_KEY_KP_5);
        xKeyMap.put(KEYMAP_KEY_NUMPAD6, GLFW_KEY_KP_6);
        xKeyMap.put(KEYMAP_KEY_NUMPAD7, GLFW_KEY_KP_7);
        xKeyMap.put(KEYMAP_KEY_NUMPAD8, GLFW_KEY_KP_8);
        xKeyMap.put(KEYMAP_KEY_NUMPAD9, GLFW_KEY_KP_9);
        xKeyMap.put(KEYMAP_KEY_NUMLOCK, GLFW_KEY_NUM_LOCK);
        xKeyMap.put(KEYMAP_KEY_SCROLL, GLFW_KEY_SCROLL_LOCK);
        xKeyMap.put(KEYMAP_KEY_SUBTRACT, GLFW_KEY_KP_SUBTRACT);
        xKeyMap.put(KEYMAP_KEY_ADD, GLFW_KEY_KP_ADD);
        xKeyMap.put(KEYMAP_KEY_DECIMAL, GLFW_KEY_KP_DECIMAL);
        xKeyMap.put(KEYMAP_KEY_NUMPADENTER, GLFW_KEY_KP_ENTER);
        xKeyMap.put(KEYMAP_KEY_DIVIDE, GLFW_KEY_KP_DIVIDE);
        xKeyMap.put(KEYMAP_KEY_MULTIPLY, GLFW_KEY_KP_MULTIPLY);
        xKeyMap.put(KEYMAP_KEY_PRINT, GLFW_KEY_PRINT_SCREEN);
        xKeyMap.put(KEYMAP_KEY_LWIN, GLFW_KEY_LEFT_SUPER);
        xKeyMap.put(KEYMAP_KEY_RWIN, GLFW_KEY_RIGHT_SUPER);

        /* Mouse buttons codes */
        xKeyMap.put(MOUSEMAP_BUTTON_LEFT, GLFW_MOUSE_BUTTON_LEFT);
        xKeyMap.put(MOUSEMAP_BUTTON_RIGHT, GLFW_MOUSE_BUTTON_RIGHT);
        xKeyMap.put(MOUSEMAP_BUTTON_MIDDLE, GLFW_MOUSE_BUTTON_MIDDLE);
        xKeyMap.put(MOUSEMAP_WHEEL_UP, GLFW_MOUSE_BUTTON_LAST);
        xKeyMap.put(MOUSEMAP_WHEEL_DOWN, GLFW_MOUSE_BUTTON_LAST);
    }

    @Override
    public Object translate(Object keyCode) {
        if (xKeyMap.containsKey(keyCode)) {
            return xKeyMap.get(keyCode);
        } else {
            return -1;
        }
    }


    /** The unknown key. */
    public static final short GLFW_KEY_UNKNOWN = 0; // should be -1

    /** Printable keys. */
    public static final short
            GLFW_KEY_SPACE         = 32,
            GLFW_KEY_APOSTROPHE    = 39,
            GLFW_KEY_COMMA         = 44,
            GLFW_KEY_MINUS         = 45,
            GLFW_KEY_PERIOD        = 46,
            GLFW_KEY_SLASH         = 47,
            GLFW_KEY_0             = 48,
            GLFW_KEY_1             = 49,
            GLFW_KEY_2             = 50,
            GLFW_KEY_3             = 51,
            GLFW_KEY_4             = 52,
            GLFW_KEY_5             = 53,
            GLFW_KEY_6             = 54,
            GLFW_KEY_7             = 55,
            GLFW_KEY_8             = 56,
            GLFW_KEY_9             = 57,
            GLFW_KEY_SEMICOLON     = 59,
            GLFW_KEY_EQUAL         = 61,
            GLFW_KEY_A             = 65,
            GLFW_KEY_B             = 66,
            GLFW_KEY_C             = 67,
            GLFW_KEY_D             = 68,
            GLFW_KEY_E             = 69,
            GLFW_KEY_F             = 70,
            GLFW_KEY_G             = 71,
            GLFW_KEY_H             = 72,
            GLFW_KEY_I             = 73,
            GLFW_KEY_J             = 74,
            GLFW_KEY_K             = 75,
            GLFW_KEY_L             = 76,
            GLFW_KEY_M             = 77,
            GLFW_KEY_N             = 78,
            GLFW_KEY_O             = 79,
            GLFW_KEY_P             = 80,
            GLFW_KEY_Q             = 81,
            GLFW_KEY_R             = 82,
            GLFW_KEY_S             = 83,
            GLFW_KEY_T             = 84,
            GLFW_KEY_U             = 85,
            GLFW_KEY_V             = 86,
            GLFW_KEY_W             = 87,
            GLFW_KEY_X             = 88,
            GLFW_KEY_Y             = 89,
            GLFW_KEY_Z             = 90,
            GLFW_KEY_LEFT_BRACKET  = 91,
            GLFW_KEY_BACKSLASH     = 92,
            GLFW_KEY_RIGHT_BRACKET = 93,
            GLFW_KEY_GRAVE_ACCENT  = 96,
            GLFW_KEY_WORLD_1       = 161,
            GLFW_KEY_WORLD_2       = 162;

    /** Function keys. */
    public static final short
            GLFW_KEY_ESCAPE        = 256,
            GLFW_KEY_ENTER         = 257,
            GLFW_KEY_TAB           = 258,
            GLFW_KEY_BACKSPACE     = 259,
            GLFW_KEY_INSERT        = 260,
            GLFW_KEY_DELETE        = 261,
            GLFW_KEY_RIGHT         = 262,
            GLFW_KEY_LEFT          = 263,
            GLFW_KEY_DOWN          = 264,
            GLFW_KEY_UP            = 265,
            GLFW_KEY_PAGE_UP       = 266,
            GLFW_KEY_PAGE_DOWN     = 267,
            GLFW_KEY_HOME          = 268,
            GLFW_KEY_END           = 269,
            GLFW_KEY_CAPS_LOCK     = 280,
            GLFW_KEY_SCROLL_LOCK   = 281,
            GLFW_KEY_NUM_LOCK      = 282,
            GLFW_KEY_PRINT_SCREEN  = 283,
            GLFW_KEY_PAUSE         = 284,
            GLFW_KEY_F1            = 290,
            GLFW_KEY_F2            = 291,
            GLFW_KEY_F3            = 292,
            GLFW_KEY_F4            = 293,
            GLFW_KEY_F5            = 294,
            GLFW_KEY_F6            = 295,
            GLFW_KEY_F7            = 296,
            GLFW_KEY_F8            = 297,
            GLFW_KEY_F9            = 298,
            GLFW_KEY_F10           = 299,
            GLFW_KEY_F11           = 300,
            GLFW_KEY_F12           = 301,
            GLFW_KEY_F13           = 302,
            GLFW_KEY_F14           = 303,
            GLFW_KEY_F15           = 304,
            GLFW_KEY_F16           = 305,
            GLFW_KEY_F17           = 306,
            GLFW_KEY_F18           = 307,
            GLFW_KEY_F19           = 308,
            GLFW_KEY_F20           = 309,
            GLFW_KEY_F21           = 310,
            GLFW_KEY_F22           = 311,
            GLFW_KEY_F23           = 312,
            GLFW_KEY_F24           = 313,
            GLFW_KEY_F25           = 314,
            GLFW_KEY_KP_0          = 320,
            GLFW_KEY_KP_1          = 321,
            GLFW_KEY_KP_2          = 322,
            GLFW_KEY_KP_3          = 323,
            GLFW_KEY_KP_4          = 324,
            GLFW_KEY_KP_5          = 325,
            GLFW_KEY_KP_6          = 326,
            GLFW_KEY_KP_7          = 327,
            GLFW_KEY_KP_8          = 328,
            GLFW_KEY_KP_9          = 329,
            GLFW_KEY_KP_DECIMAL    = 330,
            GLFW_KEY_KP_DIVIDE     = 331,
            GLFW_KEY_KP_MULTIPLY   = 332,
            GLFW_KEY_KP_SUBTRACT   = 333,
            GLFW_KEY_KP_ADD        = 334,
            GLFW_KEY_KP_ENTER      = 335,
            GLFW_KEY_KP_EQUAL      = 336,
            GLFW_KEY_LEFT_SHIFT    = 340,
            GLFW_KEY_LEFT_CONTROL  = 341,
            GLFW_KEY_LEFT_ALT      = 342,
            GLFW_KEY_LEFT_SUPER    = 343,
            GLFW_KEY_RIGHT_SHIFT   = 344,
            GLFW_KEY_RIGHT_CONTROL = 345,
            GLFW_KEY_RIGHT_ALT     = 346,
            GLFW_KEY_RIGHT_SUPER   = 347,
            GLFW_KEY_MENU          = 348,
            GLFW_KEY_LAST          = GLFW_KEY_MENU;

    /** If this bit is set one or more Shift keys were held down. */
    public static final int GLFW_MOD_SHIFT = 0x1;

    /** If this bit is set one or more Control keys were held down. */
    public static final int GLFW_MOD_CONTROL = 0x2;

    /** If this bit is set one or more Alt keys were held down. */
    public static final int GLFW_MOD_ALT = 0x4;

    /** If this bit is set one or more Super keys were held down. */
    public static final int GLFW_MOD_SUPER = 0x8;

    /** If this bit is set the Caps Lock key is enabled and the  input mode is set. */
    public static final int GLFW_MOD_CAPS_LOCK = 0x10;

    /** If this bit is set the Num Lock key is enabled and the  input mode is set. */
    public static final int GLFW_MOD_NUM_LOCK = 0x20;


    /** Mouse buttons. See <a target="_blank" href="http://www.glfw.org/docs/latest/input.html#input_mouse_button">mouse button input</a> for how these are used. */
    public static final short
            GLFW_MOUSE_BUTTON_1      = 0,
            GLFW_MOUSE_BUTTON_2      = 1,
            GLFW_MOUSE_BUTTON_3      = 2,
            GLFW_MOUSE_BUTTON_4      = 3,
            GLFW_MOUSE_BUTTON_5      = 4,
            GLFW_MOUSE_BUTTON_6      = 5,
            GLFW_MOUSE_BUTTON_7      = 6,
            GLFW_MOUSE_BUTTON_8      = 7,
            GLFW_MOUSE_BUTTON_LAST   = GLFW_MOUSE_BUTTON_8,
            GLFW_MOUSE_BUTTON_LEFT   = GLFW_MOUSE_BUTTON_1,
            GLFW_MOUSE_BUTTON_RIGHT  = GLFW_MOUSE_BUTTON_2,
            GLFW_MOUSE_BUTTON_MIDDLE = GLFW_MOUSE_BUTTON_3;
}
