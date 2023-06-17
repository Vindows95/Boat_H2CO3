package org.koishi.aof.mcinabox.gamecontroller.codes;

import static cosine.boat.BoatKeycodes.BOAT_KEYBOARD_0;
import static cosine.boat.BoatKeycodes.BOAT_KEYBOARD_1;
import static cosine.boat.BoatKeycodes.BOAT_KEYBOARD_2;

import org.koishi.aof.mcinabox.gamecontroller.definitions.map.KeyMap;

import java.util.HashMap;

import static cosine.boat.BoatKeycodes.*;

public class LwjglKeyMap implements KeyMap, CoKeyMap {

    private final HashMap<String, Integer> lwjglMap;

    public LwjglKeyMap() {
        lwjglMap = new HashMap<>();
        init();
    }

    private void init() {
        lwjglMap.put(KEYMAP_KEY_0, BOAT_KEYBOARD_0);
        lwjglMap.put(KEYMAP_KEY_1, BOAT_KEYBOARD_1);
        lwjglMap.put(KEYMAP_KEY_2, BOAT_KEYBOARD_2);
        lwjglMap.put(KEYMAP_KEY_3, BOAT_KEYBOARD_3);
        lwjglMap.put(KEYMAP_KEY_4, BOAT_KEYBOARD_4);
        lwjglMap.put(KEYMAP_KEY_5, BOAT_KEYBOARD_5);
        lwjglMap.put(KEYMAP_KEY_6, BOAT_KEYBOARD_6);
        lwjglMap.put(KEYMAP_KEY_7, BOAT_KEYBOARD_7);
        lwjglMap.put(KEYMAP_KEY_8, BOAT_KEYBOARD_8);
        lwjglMap.put(KEYMAP_KEY_9, BOAT_KEYBOARD_9);
        lwjglMap.put(KEYMAP_KEY_A, BOAT_KEYBOARD_A);
        lwjglMap.put(KEYMAP_KEY_B, BOAT_KEYBOARD_B);
        lwjglMap.put(KEYMAP_KEY_C, BOAT_KEYBOARD_C);
        lwjglMap.put(KEYMAP_KEY_D, BOAT_KEYBOARD_D);
        lwjglMap.put(KEYMAP_KEY_E, BOAT_KEYBOARD_E);
        lwjglMap.put(KEYMAP_KEY_F, BOAT_KEYBOARD_F);
        lwjglMap.put(KEYMAP_KEY_G, BOAT_KEYBOARD_G);
        lwjglMap.put(KEYMAP_KEY_H, BOAT_KEYBOARD_H);
        lwjglMap.put(KEYMAP_KEY_I, BOAT_KEYBOARD_I);
        lwjglMap.put(KEYMAP_KEY_J, BOAT_KEYBOARD_J);
        lwjglMap.put(KEYMAP_KEY_K, BOAT_KEYBOARD_K);
        lwjglMap.put(KEYMAP_KEY_L, BOAT_KEYBOARD_L);
        lwjglMap.put(KEYMAP_KEY_M, BOAT_KEYBOARD_M);
        lwjglMap.put(KEYMAP_KEY_N, BOAT_KEYBOARD_N);
        lwjglMap.put(KEYMAP_KEY_O, BOAT_KEYBOARD_O);
        lwjglMap.put(KEYMAP_KEY_P, BOAT_KEYBOARD_P);
        lwjglMap.put(KEYMAP_KEY_Q, BOAT_KEYBOARD_Q);
        lwjglMap.put(KEYMAP_KEY_R, BOAT_KEYBOARD_R);
        lwjglMap.put(KEYMAP_KEY_S, BOAT_KEYBOARD_S);
        lwjglMap.put(KEYMAP_KEY_T, BOAT_KEYBOARD_T);
        lwjglMap.put(KEYMAP_KEY_U, BOAT_KEYBOARD_U);
        lwjglMap.put(KEYMAP_KEY_V, BOAT_KEYBOARD_V);
        lwjglMap.put(KEYMAP_KEY_W, BOAT_KEYBOARD_W);
        lwjglMap.put(KEYMAP_KEY_X, BOAT_KEYBOARD_X);
        lwjglMap.put(KEYMAP_KEY_Y, BOAT_KEYBOARD_Y);
        lwjglMap.put(KEYMAP_KEY_Z, BOAT_KEYBOARD_Z);
        lwjglMap.put(KEYMAP_KEY_MINUS, BOAT_KEYBOARD_minus);
        lwjglMap.put(KEYMAP_KEY_EQUALS, BOAT_KEYBOARD_equal);
        lwjglMap.put(KEYMAP_KEY_LBRACKET, KEY_LBRACKET);
        lwjglMap.put(KEYMAP_KEY_RBRACKET, KEY_RBRACKET);
        lwjglMap.put(KEYMAP_KEY_SEMICOLON, BOAT_KEYBOARD_semicolon);
        lwjglMap.put(KEYMAP_KEY_APOSTROPHE, BOAT_KEYBOARD_apostrophe);
        lwjglMap.put(KEYMAP_KEY_GRAVE, BOAT_KEYBOARD_grave);
        lwjglMap.put(KEYMAP_KEY_BACKSLASH, BOAT_KEYBOARD_backslash);
        lwjglMap.put(KEYMAP_KEY_COMMA, BOAT_KEYBOARD_comma);
        lwjglMap.put(KEYMAP_KEY_PERIOD, BOAT_KEYBOARD_period);
        lwjglMap.put(KEYMAP_KEY_SLASH, BOAT_KEYBOARD_slash);
        lwjglMap.put(KEYMAP_KEY_ESC, BOAT_KEYBOARD_ESC);
        lwjglMap.put(KEYMAP_KEY_F1, BOAT_KEYBOARD_F1);
        lwjglMap.put(KEYMAP_KEY_F2, BOAT_KEYBOARD_F2);
        lwjglMap.put(KEYMAP_KEY_F3, BOAT_KEYBOARD_F3);
        lwjglMap.put(KEYMAP_KEY_F4, BOAT_KEYBOARD_F4);
        lwjglMap.put(KEYMAP_KEY_F5, BOAT_KEYBOARD_F5);
        lwjglMap.put(KEYMAP_KEY_F6, BOAT_KEYBOARD_F6);
        lwjglMap.put(KEYMAP_KEY_F7, BOAT_KEYBOARD_F7);
        lwjglMap.put(KEYMAP_KEY_F8, BOAT_KEYBOARD_F8);
        lwjglMap.put(KEYMAP_KEY_F9, BOAT_KEYBOARD_F9);
        lwjglMap.put(KEYMAP_KEY_F10, BOAT_KEYBOARD_F10);
        lwjglMap.put(KEYMAP_KEY_F11, BOAT_KEYBOARD_F11);
        lwjglMap.put(KEYMAP_KEY_F12, BOAT_KEYBOARD_F12);
        lwjglMap.put(KEYMAP_KEY_TAB, BOAT_KEYBOARD_Tab);
        lwjglMap.put(KEYMAP_KEY_BACKSPACE, BOAT_KEYBOARD_BackSpace);
        lwjglMap.put(KEYMAP_KEY_SPACE, BOAT_KEYBOARD_space);
        lwjglMap.put(KEYMAP_KEY_CAPITAL, BOAT_KEYBOARD_dead_tilde);
        lwjglMap.put(KEYMAP_KEY_ENTER, BOAT_KEYBOARD_Return);
        lwjglMap.put(KEYMAP_KEY_LSHIFT, BOAT_KEYBOARD_Shift_L);
        lwjglMap.put(KEYMAP_KEY_LCTRL, KEY_LCONTROL);
        lwjglMap.put(KEYMAP_KEY_LALT, BOAT_KEYBOARD_Menu);
        lwjglMap.put(KEYMAP_KEY_RSHIFT, BOAT_KEYBOARD_Shift_R);
        lwjglMap.put(KEYMAP_KEY_RCTRL, BOAT_KEYBOARD_Control_L);
        lwjglMap.put(KEYMAP_KEY_RALT, BOAT_KEYBOARD_Menu);
        lwjglMap.put(KEYMAP_KEY_UP, BOAT_KEYBOARD_Up);
        lwjglMap.put(KEYMAP_KEY_DOWN, BOAT_KEYBOARD_Down);
        lwjglMap.put(KEYMAP_KEY_LEFT, BOAT_KEYBOARD_Left);
        lwjglMap.put(KEYMAP_KEY_RIGHT, BOAT_KEYBOARD_Right);
        lwjglMap.put(KEYMAP_KEY_PAGEUP, BOAT_KEYBOARD_Prior);
        lwjglMap.put(KEYMAP_KEY_PAGEDOWN, BOAT_KEYBOARD_Next);
        lwjglMap.put(KEYMAP_KEY_HOME, BOAT_KEYBOARD_Home);
        lwjglMap.put(KEYMAP_KEY_END, BOAT_KEYBOARD_End);
        lwjglMap.put(KEYMAP_KEY_INSERT, BOAT_KEYBOARD_Insert);
        lwjglMap.put(KEYMAP_KEY_DELETE, BOAT_KEYBOARD_Delete);
        lwjglMap.put(KEYMAP_KEY_PAUSE, BOAT_KEYBOARD_Pause);
        lwjglMap.put(KEYMAP_KEY_NUMPAD0, BOAT_KEYBOARD_0);
        lwjglMap.put(KEYMAP_KEY_NUMPAD1, BOAT_KEYBOARD_1);
        lwjglMap.put(KEYMAP_KEY_NUMPAD2, BOAT_KEYBOARD_2);
        lwjglMap.put(KEYMAP_KEY_NUMPAD3, BOAT_KEYBOARD_3);
        lwjglMap.put(KEYMAP_KEY_NUMPAD4, BOAT_KEYBOARD_4);
        lwjglMap.put(KEYMAP_KEY_NUMPAD5, BOAT_KEYBOARD_5);
        lwjglMap.put(KEYMAP_KEY_NUMPAD6, BOAT_KEYBOARD_6);
        lwjglMap.put(KEYMAP_KEY_NUMPAD7, BOAT_KEYBOARD_7);
        lwjglMap.put(KEYMAP_KEY_NUMPAD8, BOAT_KEYBOARD_8);
        lwjglMap.put(KEYMAP_KEY_NUMPAD9, BOAT_KEYBOARD_9);
        lwjglMap.put(KEYMAP_KEY_NUMLOCK, BOAT_KEYBOARD_Num_Lock);
        lwjglMap.put(KEYMAP_KEY_SCROLL, BOAT_KEYBOARD_Scroll_Lock);
        lwjglMap.put(KEYMAP_KEY_SUBTRACT, BOAT_KEYBOARD_KP_Subtract);
        lwjglMap.put(KEYMAP_KEY_ADD, BOAT_KEYBOARD_KP_Add);
        lwjglMap.put(KEYMAP_KEY_DECIMAL, BOAT_KEYBOARD_KP_Decimal);
        lwjglMap.put(KEYMAP_KEY_NUMPADENTER, BOAT_KEYBOARD_KP_Enter);
        lwjglMap.put(KEYMAP_KEY_DIVIDE, BOAT_KEYBOARD_KP_Divide);
        lwjglMap.put(KEYMAP_KEY_MULTIPLY, KEY_MULTIPLY);
        lwjglMap.put(KEYMAP_KEY_PRINT, BOAT_KEYBOARD_Sys_Req);
        lwjglMap.put(KEYMAP_KEY_LWIN, BOAT_KEYBOARD_Meta_L);
        lwjglMap.put(KEYMAP_KEY_RWIN, BOAT_KEYBOARD_Meta_R);
        /* missing RightK in Keyboard.java */
    }

    @Override
    public Object translate(Object keyCode) {
        if (lwjglMap.containsKey(keyCode)) {
            return lwjglMap.get(keyCode);
        } else {
            return -1;
        }
    }
}
