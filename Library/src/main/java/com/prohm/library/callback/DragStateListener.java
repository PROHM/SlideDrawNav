package com.prohm.library.callback;

/**
 *  *****************************************************
 Created by Mblack47 on 04.07.2023 a 09:23 PM
 *****************************************************
 */
public interface DragStateListener {
    void onDragStart();

    void onDragEnd(boolean isMenuOpened);
}
