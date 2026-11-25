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

    private fun deutsch(c: Config) {
        c.keyboardLanguage = LANGUAGE_GERMAN_QWERTZ
        c.selectedLanguages = mutableSetOf(LANGUAGE_GERMAN_QWERTZ)
    }

    fun anwenden(context: Context) {
        val c = context.config
        if (c.rosenwegVorgabenGesetzt) {
            // Stand 2 (24.11.2026): 140 % war auf dem Galaxy A15 zu gross -> 120 %.
            if (c.rosenwegVorgabenStand < 2) {
                if (c.keyboardHeightPercentage == KEYBOARD_HEIGHT_140_PERCENT) {
                    c.keyboardHeightPercentage = KEYBOARD_HEIGHT_120_PERCENT
                }
                c.rosenwegVorgabenStand = 2
            }
            // Stand 3 (25.11.2026, Stefan: "deutsch qwertz fuer alle"): einmal auf Deutsch (QWERTZ)
            if (c.rosenwegVorgabenStand < 3) {
                deutsch(c)
                c.rosenwegVorgabenStand = 3
            }
            return
        }
        deutsch(c)
        // Ohne das uebernimmt Android 12+ die Systemfarben (Material You) und
        // ignoriert die eigenen Farben.
        c.isSystemThemeEnabled = false
        c.backgroundColor = GELB
        c.textColor = Color.BLACK
        c.primaryColor = Color.BLACK
        c.accentColor = Color.BLACK
        // Treffbarkeit: hoehere Tasten, sichtbare Tastengrenzen, Rueckmeldung.
        c.keyboardHeightPercentage = KEYBOARD_HEIGHT_120_PERCENT
        c.showKeyBorders = true
        c.showPopupOnKeypress = true
        c.vibrateOnKeypress = true
        c.showNumbersRow = true
        c.rosenwegVorgabenGesetzt = true
        c.rosenwegVorgabenStand = 3
    }
}
