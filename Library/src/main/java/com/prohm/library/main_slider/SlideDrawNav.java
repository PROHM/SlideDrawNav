package com.prohm.library.main_slider;

/**
 *  *****************************************************
 Created by Mblack47 on 04.07.2023 a 10:01 PM
 *****************************************************
 */
public interface SlideDrawNav {

    boolean isMenuClosed();

    boolean isMenuOpened();

    boolean isMenuLocked();

    void closeMenu();

    void closeMenu(boolean animated);

    void openMenu();

    void openMenu(boolean animated);

    void setMenuLocked(boolean locked);

    /* SlidingRootNavLayout getLayout(); */
}
