<p align="center"><img src="assets/logo/rosenweg-tastatur-512.png" width="96" alt="Logo"></p>
<h1 align="center">Rosenweg Tastatur</h1>
<p align="center">Bildschirmtastatur für die Rosenweg-Smartphones – gut lesbar und gut zu treffen.</p>
<p align="center"><a href="https://www.rosenweg4303.ch/apps">Herunterladen</a> · <a href="docs/anleitung.md">Anleitung</a></p>

## Inhalt

- [Überblick](#überblick)
- [Plattformen](#plattformen)
- [Rechte und Gruppen](#rechte-und-gruppen)
- [Bedienung](#bedienung)
- [Geteilte Rosenweg-Telefone](#geteilte-rosenweg-telefone)
- [Installation und Updates](#installation-und-updates)
- [Aufbau](#aufbau)
- [Bauen](#bauen)
- [CI und Release](#ci-und-release)
- [Konfiguration](#konfiguration)
- [Zweige](#zweige)
- [Herkunft und Lizenz](#herkunft-und-lizenz)

## Überblick

Bildschirmtastatur für die Smartphones der Siedlung Rosenweg in Kaiseraugst, auf gute Lesbarkeit und Treffbarkeit eingestellt. Funktional ist es die Tastatur von Fossify; angepasst sind Name, Paketname, Symbol und die Vorgaben beim ersten Start.

Was die App kann:

- **Deutsch:** volles QWERTZ mit eigenen Tasten für ä, ö und ü.
- **Hoher Kontrast:** Schwarz auf Gelb (etwa 14:1, mehr als WCAG AAA verlangt).
- **Gut zu treffen:** Tastenränder, Vorschau, Vibration, Zahlenreihe; Höhe etwa 3/8 des Bildschirms.
- **Frei einstellbar:** die Vorgaben gelten nur beim ersten Start.
- **Einstellungen pro Person:** auf geteilten Telefonen gehören sie der angemeldeten Person.
- **Rosenweg Updater:** hält auf dem eigenen Handy alle Rosenweg-Apps aktuell.

**Vorgaben beim ersten Start** (danach frei einstellbar):

| Einstellung | Vorgabe |
|---|---|
| Sprache | Deutsch (volles QWERTZ mit ä, ö, ü) |
| Farben | Schwarz auf Gelb `#FFD600`, Systemfarben (Material You) aus |
| Höhe | etwa 3/8 der Bildschirmhöhe (Galaxy A15: 90 %) |
| Tastenränder, Vorschau, Vibration, Zahlenreihe | ein |

## Plattformen

| Plattform | Stand |
|---|---|
| Android | ja, APK auf [rosenweg4303.ch/apps](https://www.rosenweg4303.ch/apps) |
| Geteilte Rosenweg-Telefone | ja, über den App-Katalog auf /smartphones |
| iOS, Desktop | nein (Android-App aus dem Fossify-Projekt) |

Nicht im Play Store und nicht auf F-Droid.

## Rechte und Gruppen

Die App hat keine eigenen Rollen. Wer sie auf den geteilten Telefonen bekommt, legt der App-Katalog der Telefonanlage fest.

| Wer | darf |
|---|---|
| Technik | legt die App im Katalog auf /smartphones an, wählt die Gruppen, schaltet sie aktiv; kann Apps auf den geteilten Telefonen ausblenden |
| Angemeldete Person in einer der Gruppen des Katalog-Eintrags (leer = alle) | sieht die App auf dem geteilten Telefon; Einstellungen gehören ihr |
| Alle anderen | auf dem eigenen Handy: APK von /apps, ohne Anmeldung |

## Bedienung

| Was | So geht’s |
|---|---|
| Einschalten (eigenes Handy) | App öffnen → Tastatur in den Android-Einstellungen aktivieren und als Standard wählen |
| Weitere Sprachen | Einstellungen → „Tastatursprachen verwalten“; mit „Sprachumschalttaste anzeigen“ gibt es eine Taste zum Wechseln |
| Farben, Höhe, Vibration | App öffnen → Einstellungen |
| Emoji, Zwischenablage | Symbolleiste über den Tasten |

Ausführlich: [docs/anleitung.md](docs/anleitung.md).

## Geteilte Rosenweg-Telefone

Auf den geteilten Telefonen (mit Rosenweg Gerät, Anmeldung per Karte am Sperrbildschirm) arbeitet die App für die Person, die gerade angemeldet ist. Der Weg zur Anlage führt über Rosenweg Gerät; die App selbst braucht dafür kein Netz und sieht kein Token.

| Thema | Verhalten |
|---|---|
| Einstellungen pro Person | Nach dem Anmelden holt die App die Einstellungen der Person von der Telefonanlage; ist die Fassung dort neuer, gilt sie, sonst geht die hiesige hoch. Jede Änderung geht kurz danach hoch. Gerätebezogenes (Pfade, Zähler, Hinweise, PIN) bleibt auf dem Gerät. |
| Abmelden | die Tastatur löscht die Einstellungen und den Zwischenablage-Verlauf der Person (`ch.rosenweg4303.ABGEMELDET`) |
| Updates | Rosenweg Gerät installiert die App aus dem App-Katalog und hält sie aktuell. |
| Ohne Rosenweg Gerät | alles bleibt wie gewohnt auf dem Handy. |

Voraussetzung: die Rosenweg-Signatur. Rosenweg Gerät beantwortet Anfragen nur von Apps mit seiner Signatur (`ch.rosenweg4303.permission.ANLAGE`).

## Installation und Updates

| Wo | Wie |
|---|---|
| Geteilte Rosenweg-Telefone | Rosenweg Gerät installiert die App automatisch aus dem App-Katalog der Telefonanlage und hält sie aktuell. |
| Eigenes Handy | APK von [rosenweg4303.ch/apps](https://www.rosenweg4303.ch/apps) herunterladen und installieren. Danach hält der Rosenweg Updater die Rosenweg-Apps aktuell. |

**Rosenweg Updater:** Auf Handys ohne Rosenweg Gerät prüft die App den App-Katalog der Telefonanlage und bietet Aktualisierungen für alle installierten Rosenweg-Apps an. Jede APK wird vorher geprüft (Prüfsumme, Paketname, Version, Rosenweg-Signatur); Android lässt jede Installation bestätigen.

Die App ist mit dem Rosenweg-Schlüssel signiert; Fassungen anderer Herkunft lassen sich nicht darüber installieren.

## Aufbau

Fossify-Code mit wenigen, markierten Rosenweg-Stellen (Kommentar „Rosenweg“). Der Kotlin-Namensraum bleibt `org.fossify.…`, nur die applicationId ist eigen: `ch.rosenweg4303.keyboard`. Kotlin-Dateien unten relativ zu `app/src/main/kotlin/org/fossify/keyboard/`.

| Teil | Inhalt |
|---|---|
| `helpers/AnlageEinstellungen.kt` | Einstellungen pro Person über Rosenweg Gerät (`content://ch.rosenweg4303.telefon.anlage`, Server `/api/telefon/einstellungen`) |
| `activities/MainActivity.kt` | ruft beim Start den Abgleich auf |
| `app/src/main/AndroidManifest.xml` | Berechtigung `ch.rosenweg4303.permission.ANLAGE`, Empfänger für An-/Abmelden (nur Rosenweg-Signatur) |
| `helpers/RosenwegVorgaben.kt` | Vorgaben beim ersten Start und gestufte Nachträge („Stand“ 2–5: Höhe, Deutsch, 3/8 des Bildschirms) |
| `helpers/Config.kt`, `App.kt` | merken, ob und welcher Stand der Vorgaben gesetzt ist; Deutsch als Standardsprache |
| `ch.rosenweg4303:updater` | Rosenweg Updater (aus commons-rosenweg) |
| Commons `7.1.3-rosenweg.1` | Rosenweg-Fork der Fossify Commons, aus `mavenLocal()` (`gradle/libs.versions.toml`, `settings.gradle.kts`) |
| `assets/logo/` | Logo aus dem Baukasten von rosenweg-basis: Turm, Weg und Rose mit dem Zeichen der App; dazu monochromes Designsymbol (Android 13+). Neu erzeugen: `sh assets/logo/erzeugen.sh` |
| `app/src/main/res/`: `values/rosenweg_icon.xml`, `mipmap-anydpi-v26/ic_launcher.xml` | ein einziges Rosenweg-Symbol statt der wählbaren Fossify-Symbolfarben |
| `fastlane/` | Store-Texte und Bilder des Originals (nicht verwendet) |

## Bauen

Die App baut gegen den Rosenweg-Fork der Fossify Commons und die Rosenweg-Module `cloud`/`updater` (`ch.rosenweg4303:*`). Diese kommen aus dem lokalen Maven-Repository, also zuerst [commons-rosenweg](https://github.com/Rosenweg/commons-rosenweg) veröffentlichen:

```bash
# in commons-rosenweg (Zweig main)
./gradlew -PVERSION=7.1.3-rosenweg.1 publishToMavenLocal

# in diesem Repo (Zweig main)
./gradlew assembleFossRelease
```

Die APK liegt danach unter `app/build/outputs/apk/foss/release/`. Signiert wird mit `keystore.properties` (siehe [Konfiguration](#konfiguration)).

Logo neu erzeugen (rosenweg-basis als Nachbarordner): `sh assets/logo/erzeugen.sh`. Welches Zeichen die App trägt, steht in rosenweg-basis `werkzeug/logo/android-apps.txt`.

Mehr: [docs/entwickeln.md](docs/entwickeln.md).

## CI und Release

Gebaut und signiert wird lokal; die CI verteilt nur.

| Schritt | Was passiert |
|---|---|
| APK bauen und signieren | lokal, wie unter [Bauen](#bauen) |
| `gh release create v<versionName> <datei.apk> --notes "Was ist neu"` | Tag = versionName der APK, eine APK je Release; ohne `--target` landet der Tag auf `main` |
| Workflow `.github/workflows/apps.yml` | ruft `apps-release.yml` aus Rosenweg/Website auf und schickt die APK an **[rosenweg4303.ch/apps](https://www.rosenweg4303.ch/apps)** und in den App-Katalog der Telefonanlage – damit bekommen sie die geteilten Telefone und der Rosenweg Updater |
| Prüfung auf der Anlage | Anmeldung per GitHub-OIDC (kein gespeichertes Geheimnis); geprüft werden Repo, Tag, Prüfsumme, Rosenweg-Signatur und Paketname. Die App muss auf /smartphones schon angelegt sein. |
| Erneut senden | `gh workflow run apps.yml --ref <tag>` |

Die übrigen Workflows (`pr.yml`, `testing-build.yml`, `release.yml`, `update-commons.yml` …) stammen aus dem Original und rufen die Fossify-Vorlagen auf; der Play-Store-Release (`release.yml`) läuft nur bei einer Änderung an `.fossify/release-marker.txt`.

Weg auf dem Server: Rosenweg/Website, `pbx/README.md`, Abschnitt „Seite /apps und App-Releases“.

## Konfiguration

| Wo | Was (nur Namen) |
|---|---|
| `gradle.properties` | `APP_ID` (`ch.rosenweg4303.keyboard`), `VERSION_NAME`, `VERSION_CODE` |
| `keystore.properties` (nicht im Repo, Vorlage `keystore.properties_sample`) | Pfad und Zugang zum Rosenweg-Signierschlüssel |
| GitHub-Secrets | keine für den Weg nach /apps (OIDC) |
| Server (Rosenweg/Website, PBX-API) | Katalog-Eintrag `ch.rosenweg4303.keyboard` auf /smartphones (Gruppen, aktiv); Einstellungen je Person und Paket über `/api/telefon/einstellungen` |

## Zweige

`main` ist der Hauptzweig mit allen Rosenweg-Anpassungen; der frühere Arbeitszweig `rosenweg` ist darin gemergt. Neuerungen aus dem Original werden von [Fossify Keyboard](https://github.com/FossifyOrg/Keyboard) nach `main` übernommen.

## Herkunft und Lizenz

Basiert auf [Fossify Keyboard](https://github.com/FossifyOrg/Keyboard). Herzlichen Dank an das Fossify-Team und alle Mitwirkenden für die solide Grundlage.

Lizenz: GNU General Public License v3.0 (GPL-3.0), siehe [LICENSE](LICENSE).
