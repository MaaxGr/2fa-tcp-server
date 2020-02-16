package de.maaxgr.twofatcpserver.util

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent


fun Robot.typeString(content: String) = content.toCharArray().forEach { typeChar(it) }

fun Robot.typeChar(character: Char) {
    val keyCode = KeyEvent.getExtendedKeyCodeForChar(character.toInt())

    if (KeyEvent.CHAR_UNDEFINED.toInt() == keyCode) {
        throw RuntimeException("Key code not found for character '$character'")
    }

    keyPress(keyCode)
    delay(10)
    keyRelease(keyCode)
    delay(10)
}

fun Robot.pasteText(content: String) {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val oldSelection = StringSelection(clipboard.getData(DataFlavor.stringFlavor) as String)
    val newSelection = StringSelection(content)

    clipboard.setContents(newSelection, newSelection)

    val robot = Robot()
    robot.keyPress(KeyEvent.VK_CONTROL)
    robot.keyPress(KeyEvent.VK_V)
    robot.keyRelease(KeyEvent.VK_V)
    robot.keyRelease(KeyEvent.VK_CONTROL)

    GlobalScope.launch {
        delay(250)
        clipboard.setContents(oldSelection, oldSelection)
    }
}