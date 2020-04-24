package com.mctech.architecture.mvvm.x.core

/**
 * A command is something that happen just once like for example:
 *  - The ViewModel send 'a command' to the view to make it navigate to another screen.
 *
 *  It's like the 'State' of the view, but used to send, again, 'a command' to the screen.
 *  It's everything the does't change the 'visual state' of the view.
 */
interface ViewCommand