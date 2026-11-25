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
        // "Deutsch" ist das volle QWERTZ mit eigenen Tasten fuer ue/oe/ae; "Deutsch (QWERTZ)"
        // ist eine kompakte Variante ohne sie (Stefan 25.11.2026: "hat nicht oeuea")
        c.keyboardLanguage = LANGUAGE_GERMAN
        c.selectedLanguages = mutableSetOf(LANGUAGE_GERMAN)
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
            // Stand 3/4 (25.11.2026, Stefan: "deutsch qwertz fuer alle", mit ue/oe/ae): einmal auf Deutsch
            if (c.rosenwegVorgabenStand < 4) {
                deutsch(c)
                c.rosenwegVorgabenStand = 4
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
        c.rosenwegVorgabenStand = 4
    }
}
