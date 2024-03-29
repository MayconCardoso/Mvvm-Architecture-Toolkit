package io.github.mayconcardoso.mvvm.core

/**
 * A command is something that happen just once like for example:
 *  - The ViewModel send 'a command' to the view to make it navigate to another screen.
 *
 *  It's like the 'State' of the view, but used to send, again, 'a command' to the screen.
 *  It's everything the doesn't change the 'visual state' of the view.
 */
interface ViewCommand