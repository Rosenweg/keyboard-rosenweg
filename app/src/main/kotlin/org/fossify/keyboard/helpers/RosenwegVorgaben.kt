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
            // Stand 5 (25.11.2026, Stefan: "muss auf 3/8 der Bildschirmflaeche zurueck")
            if (c.rosenwegVorgabenStand < 5) {
                c.keyboardHeightPercentage = hoeheFuerDreiAchtel(context)
                c.rosenwegVorgabenStand = 5
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
        c.showNumbersRow = true
        c.keyboardHeightPercentage = hoeheFuerDreiAchtel(context)
        c.showKeyBorders = true
        c.showPopupOnKeypress = true
        c.vibrateOnKeypress = true
        c.showNumbersRow = true
        c.rosenwegVorgabenGesetzt = true
        c.rosenwegVorgabenStand = 5
    }

    /**
     * Hoehen-Einstellung, mit der die ganze Tastatur (Symbolleiste + Tastenreihen) etwa 3/8 der
     * Bildschirmhoehe belegt; gerundet auf die Stufen der Einstellung (70 ... 160 %).
     * Galaxy A15 (832 dp hoch): 312 dp -> 90 %.
     */
    private fun hoeheFuerDreiAchtel(context: Context): Int {
        val r = context.resources
        val dm = r.displayMetrics
        val hoch = maxOf(dm.heightPixels, dm.widthPixels).toFloat()
        val leiste = r.getDimension(org.fossify.keyboard.R.dimen.toolbar_height)
        val taste = r.getDimension(org.fossify.keyboard.R.dimen.key_height)
        val reihen = if (context.config.showNumbersRow) 5 else 4
        val prozent = ((hoch * 3f / 8f - leiste) / (reihen * taste) * 100f)
        val stufen = listOf(
            KEYBOARD_HEIGHT_70_PERCENT, KEYBOARD_HEIGHT_80_PERCENT, KEYBOARD_HEIGHT_90_PERCENT,
            KEYBOARD_HEIGHT_100_PERCENT, KEYBOARD_HEIGHT_120_PERCENT, KEYBOARD_HEIGHT_140_PERCENT,
            KEYBOARD_HEIGHT_160_PERCENT,
        )
        return stufen.minByOrNull { kotlin.math.abs(it - prozent) } ?: KEYBOARD_HEIGHT_100_PERCENT
    }
}
