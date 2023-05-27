package cosine.boat;

import android.view.KeyEvent;
import java.util.Map;
import java.util.HashMap;
import cosine.boat.BoatKeycodes;


public class AndroidKeyCodes {
	public static final Map<Integer, Integer> keyCodeMap = new HashMap<>();
	static {


        keyCodeMap.put(KeyEvent.KEYCODE_MINUS, BoatKeycodes.BOAT_KEYBOARD_minus);
        keyCodeMap.put(KeyEvent.KEYCODE_EQUALS, BoatKeycodes.BOAT_KEYBOARD_equal);
        keyCodeMap.put(KeyEvent.KEYCODE_LEFT_BRACKET, BoatKeycodes.BOAT_KEYBOARD_bracketleft);
        keyCodeMap.put(KeyEvent.KEYCODE_RIGHT_BRACKET, BoatKeycodes.BOAT_KEYBOARD_bracketright);
        keyCodeMap.put(KeyEvent.KEYCODE_SEMICOLON, BoatKeycodes.BOAT_KEYBOARD_semicolon);
        keyCodeMap.put(KeyEvent.KEYCODE_APOSTROPHE, BoatKeycodes.BOAT_KEYBOARD_apostrophe);
        keyCodeMap.put(KeyEvent.KEYCODE_GRAVE, BoatKeycodes.BOAT_KEYBOARD_grave);
        keyCodeMap.put(KeyEvent.KEYCODE_BACKSLASH, BoatKeycodes.BOAT_KEYBOARD_backslash);
        keyCodeMap.put(KeyEvent.KEYCODE_COMMA, BoatKeycodes.BOAT_KEYBOARD_comma);
        keyCodeMap.put(KeyEvent.KEYCODE_PERIOD, BoatKeycodes.BOAT_KEYBOARD_period);
        keyCodeMap.put(KeyEvent.KEYCODE_SLASH, BoatKeycodes.BOAT_KEYBOARD_slash);
        keyCodeMap.put(KeyEvent.KEYCODE_ESCAPE, BoatKeycodes.BOAT_KEYBOARD_Escape);
        keyCodeMap.put(KeyEvent.KEYCODE_TAB, BoatKeycodes.BOAT_KEYBOARD_Tab);
        keyCodeMap.put(KeyEvent.KEYCODE_BACK, BoatKeycodes.BOAT_KEYBOARD_BackSpace);
        keyCodeMap.put(KeyEvent.KEYCODE_SPACE, BoatKeycodes.BOAT_KEYBOARD_space);
        keyCodeMap.put(KeyEvent.KEYCODE_CAPS_LOCK, BoatKeycodes.BOAT_KEYBOARD_Caps_Lock);
        keyCodeMap.put(KeyEvent.KEYCODE_ENTER, BoatKeycodes.BOAT_KEYBOARD_KP_Enter);
        keyCodeMap.put(KeyEvent.KEYCODE_SHIFT_LEFT, BoatKeycodes.BOAT_KEYBOARD_Shift_L);
        keyCodeMap.put(KeyEvent.KEYCODE_CTRL_LEFT, BoatKeycodes.BOAT_KEYBOARD_Control_L);
        keyCodeMap.put(KeyEvent.KEYCODE_ALT_LEFT, BoatKeycodes.BOAT_KEYBOARD_Alt_L);
        keyCodeMap.put(KeyEvent.KEYCODE_SHIFT_RIGHT, BoatKeycodes.BOAT_KEYBOARD_Shift_R);
        keyCodeMap.put(KeyEvent.KEYCODE_CTRL_RIGHT, BoatKeycodes.BOAT_KEYBOARD_Control_R);
        keyCodeMap.put(KeyEvent.KEYCODE_ALT_RIGHT, BoatKeycodes.BOAT_KEYBOARD_Alt_R);
        keyCodeMap.put(KeyEvent.KEYCODE_DPAD_UP, BoatKeycodes.BOAT_KEYBOARD_Up);
        keyCodeMap.put(KeyEvent.KEYCODE_DPAD_DOWN, BoatKeycodes.BOAT_KEYBOARD_Down);
        keyCodeMap.put(KeyEvent.KEYCODE_DPAD_LEFT, BoatKeycodes.BOAT_KEYBOARD_Left);
        keyCodeMap.put(KeyEvent.KEYCODE_DPAD_RIGHT, BoatKeycodes.BOAT_KEYBOARD_Right);
        keyCodeMap.put(KeyEvent.KEYCODE_PAGE_UP, BoatKeycodes.BOAT_KEYBOARD_Page_Up);
        keyCodeMap.put(KeyEvent.KEYCODE_PAGE_DOWN, BoatKeycodes.BOAT_KEYBOARD_Page_Down);
        keyCodeMap.put(KeyEvent.KEYCODE_HOME, BoatKeycodes.BOAT_KEYBOARD_Home);
        keyCodeMap.put(KeyEvent.KEYCODE_MOVE_END, BoatKeycodes.BOAT_KEYBOARD_End);
        keyCodeMap.put(KeyEvent.KEYCODE_INSERT, BoatKeycodes.BOAT_KEYBOARD_Insert);
        keyCodeMap.put(KeyEvent.KEYCODE_DEL, BoatKeycodes.BOAT_KEYBOARD_Delete);
        keyCodeMap.put(KeyEvent.KEYCODE_BREAK, BoatKeycodes.BOAT_KEYBOARD_Pause);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_0, BoatKeycodes.BOAT_KEYBOARD_KP_0);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_1, BoatKeycodes.BOAT_KEYBOARD_KP_1);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_2, BoatKeycodes.BOAT_KEYBOARD_KP_2);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_3, BoatKeycodes.BOAT_KEYBOARD_KP_3);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_4, BoatKeycodes.BOAT_KEYBOARD_KP_4);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_5, BoatKeycodes.BOAT_KEYBOARD_KP_5);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_6, BoatKeycodes.BOAT_KEYBOARD_KP_6);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_7, BoatKeycodes.BOAT_KEYBOARD_KP_7);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_8, BoatKeycodes.BOAT_KEYBOARD_KP_8);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_9, BoatKeycodes.BOAT_KEYBOARD_KP_9);
        keyCodeMap.put(KeyEvent.KEYCODE_NUM_LOCK, BoatKeycodes.BOAT_KEYBOARD_Num_Lock);
        keyCodeMap.put(KeyEvent.KEYCODE_SCROLL_LOCK, BoatKeycodes.BOAT_KEYBOARD_Scroll_Lock);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_SUBTRACT, BoatKeycodes.BOAT_KEYBOARD_KP_Subtract);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_ADD, BoatKeycodes.BOAT_KEYBOARD_KP_Add);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_DOT, BoatKeycodes.BOAT_KEYBOARD_KP_Decimal);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_ENTER, BoatKeycodes.BOAT_KEYBOARD_KP_Enter);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_DIVIDE, BoatKeycodes.BOAT_KEYBOARD_KP_Divide);
        keyCodeMap.put(KeyEvent.KEYCODE_NUMPAD_MULTIPLY, BoatKeycodes.BOAT_KEYBOARD_KP_Multiply);
        keyCodeMap.put(KeyEvent.KEYCODE_SYSRQ, BoatKeycodes.BOAT_KEYBOARD_Print);
        /* missing RightK in BoatKeycodes.java */

        keyCodeMap.put(KeyEvent.KEYCODE_0,BoatKeycodes.BOAT_KEYBOARD_0);
		keyCodeMap.put(KeyEvent.KEYCODE_1,BoatKeycodes.BOAT_KEYBOARD_1);
		keyCodeMap.put(KeyEvent.KEYCODE_2,BoatKeycodes.BOAT_KEYBOARD_2);
		keyCodeMap.put(KeyEvent.KEYCODE_3,BoatKeycodes.BOAT_KEYBOARD_3);
		keyCodeMap.put(KeyEvent.KEYCODE_4,BoatKeycodes.BOAT_KEYBOARD_4);
		keyCodeMap.put(KeyEvent.KEYCODE_5,BoatKeycodes.BOAT_KEYBOARD_5);
		keyCodeMap.put(KeyEvent.KEYCODE_6,BoatKeycodes.BOAT_KEYBOARD_6);
		keyCodeMap.put(KeyEvent.KEYCODE_7,BoatKeycodes.BOAT_KEYBOARD_7);
		keyCodeMap.put(KeyEvent.KEYCODE_8,BoatKeycodes.BOAT_KEYBOARD_8);
		keyCodeMap.put(KeyEvent.KEYCODE_9,BoatKeycodes.BOAT_KEYBOARD_9);
		//q-p
		keyCodeMap.put(KeyEvent.KEYCODE_Q,BoatKeycodes.BOAT_KEYBOARD_Q);
		keyCodeMap.put(KeyEvent.KEYCODE_W,BoatKeycodes.BOAT_KEYBOARD_W);
		keyCodeMap.put(KeyEvent.KEYCODE_E,BoatKeycodes.BOAT_KEYBOARD_E);
		keyCodeMap.put(KeyEvent.KEYCODE_R,BoatKeycodes.BOAT_KEYBOARD_R);
		keyCodeMap.put(KeyEvent.KEYCODE_T,BoatKeycodes.BOAT_KEYBOARD_T);
		keyCodeMap.put(KeyEvent.KEYCODE_Y,BoatKeycodes.BOAT_KEYBOARD_Y);
		keyCodeMap.put(KeyEvent.KEYCODE_U,BoatKeycodes.BOAT_KEYBOARD_U);
		keyCodeMap.put(KeyEvent.KEYCODE_I,BoatKeycodes.BOAT_KEYBOARD_I);
		keyCodeMap.put(KeyEvent.KEYCODE_O,BoatKeycodes.BOAT_KEYBOARD_O);
		keyCodeMap.put(KeyEvent.KEYCODE_P,BoatKeycodes.BOAT_KEYBOARD_P);
		//a-l
		keyCodeMap.put(KeyEvent.KEYCODE_A,BoatKeycodes.BOAT_KEYBOARD_A);
		keyCodeMap.put(KeyEvent.KEYCODE_S,BoatKeycodes.BOAT_KEYBOARD_S);
		keyCodeMap.put(KeyEvent.KEYCODE_D,BoatKeycodes.BOAT_KEYBOARD_D);
		keyCodeMap.put(KeyEvent.KEYCODE_F,BoatKeycodes.BOAT_KEYBOARD_F);
		keyCodeMap.put(KeyEvent.KEYCODE_G,BoatKeycodes.BOAT_KEYBOARD_G);
		keyCodeMap.put(KeyEvent.KEYCODE_H,BoatKeycodes.BOAT_KEYBOARD_H);
		keyCodeMap.put(KeyEvent.KEYCODE_J,BoatKeycodes.BOAT_KEYBOARD_J);
		keyCodeMap.put(KeyEvent.KEYCODE_K,BoatKeycodes.BOAT_KEYBOARD_K);
		keyCodeMap.put(KeyEvent.KEYCODE_L,BoatKeycodes.BOAT_KEYBOARD_L);
		//z-m
		keyCodeMap.put(KeyEvent.KEYCODE_Z,BoatKeycodes.BOAT_KEYBOARD_Z);
		keyCodeMap.put(KeyEvent.KEYCODE_X,BoatKeycodes.BOAT_KEYBOARD_X);
		keyCodeMap.put(KeyEvent.KEYCODE_C,BoatKeycodes.BOAT_KEYBOARD_C);
		keyCodeMap.put(KeyEvent.KEYCODE_V,BoatKeycodes.BOAT_KEYBOARD_V);
		keyCodeMap.put(KeyEvent.KEYCODE_B,BoatKeycodes.BOAT_KEYBOARD_B);
		keyCodeMap.put(KeyEvent.KEYCODE_N,BoatKeycodes.BOAT_KEYBOARD_N);
		keyCodeMap.put(KeyEvent.KEYCODE_M,BoatKeycodes.BOAT_KEYBOARD_M);
		//f1-f12
		keyCodeMap.put(KeyEvent.KEYCODE_F1,BoatKeycodes.BOAT_KEYBOARD_F1);
		keyCodeMap.put(KeyEvent.KEYCODE_F2,BoatKeycodes.BOAT_KEYBOARD_F2);
		keyCodeMap.put(KeyEvent.KEYCODE_F3,BoatKeycodes.BOAT_KEYBOARD_F3);
		keyCodeMap.put(KeyEvent.KEYCODE_F4,BoatKeycodes.BOAT_KEYBOARD_F4);
		keyCodeMap.put(KeyEvent.KEYCODE_F5,BoatKeycodes.BOAT_KEYBOARD_F5);
		keyCodeMap.put(KeyEvent.KEYCODE_F6,BoatKeycodes.BOAT_KEYBOARD_F6);
		keyCodeMap.put(KeyEvent.KEYCODE_F7,BoatKeycodes.BOAT_KEYBOARD_F7);
		keyCodeMap.put(KeyEvent.KEYCODE_F8,BoatKeycodes.BOAT_KEYBOARD_F8);
		keyCodeMap.put(KeyEvent.KEYCODE_F9,BoatKeycodes.BOAT_KEYBOARD_F9);
		keyCodeMap.put(KeyEvent.KEYCODE_F10,BoatKeycodes.BOAT_KEYBOARD_F10);
		keyCodeMap.put(KeyEvent.KEYCODE_F11,BoatKeycodes.BOAT_KEYBOARD_F11);
		keyCodeMap.put(KeyEvent.KEYCODE_F12,BoatKeycodes.BOAT_KEYBOARD_F12);
		
		
		
		
		
	}
}
