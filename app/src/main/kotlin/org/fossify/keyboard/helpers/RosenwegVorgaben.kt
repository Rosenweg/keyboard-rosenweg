package org.fossify.keyboard.helpers

import android.content.Context
import android.graphics.Color
import org.fossify.keyboard.extensions.config

/**
 * Rosenweg-Vorgaben fuer die Tastatur: hoher Kontrast (Schwarz auf Gelb) und
 * gute Treffbarkeit. Werden einmalig beim ersten Start gesetzt; danach kann
 * jede Person in den Tastatur-Einstellungen nachjustieren.
 *
 * Kontrast Schwarz auf #FFD600 ~ 14:1 (WCAG AAA verlangt 7:1).
 */
object RosenwegVorgaben {
    private const val GELB = 0xFFFFD600.toInt()

    fun anwenden(context: Context) {
        val c = context.config
        if (c.rosenwegVorgabenGesetzt) return
        // Ohne das uebernimmt Android 12+ die Systemfarben (Material You) und
        // ignoriert die eigenen Farben.
        c.isSystemThemeEnabled = false
        c.backgroundColor = GELB
        c.textColor = Color.BLACK
        c.primaryColor = Color.BLACK
        c.accentColor = Color.BLACK
        // Treffbarkeit: hoehere Tasten, sichtbare Tastengrenzen, Rueckmeldung.
        c.keyboardHeightPercentage = KEYBOARD_HEIGHT_140_PERCENT
        c.showKeyBorders = true
        c.showPopupOnKeypress = true
        c.vibrateOnKeypress = true
        c.showNumbersRow = true
        c.rosenwegVorgabenGesetzt = true
    }
}
