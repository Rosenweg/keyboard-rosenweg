# Rosenweg Tastatur

Bildschirmtastatur für die Smartphones der Siedlung Rosenweg in Kaiseraugst, auf gute Lesbarkeit und Treffbarkeit eingestellt. Funktional ist es die Tastatur von Fossify; angepasst sind Name, Paketname, Symbol und die Vorgaben beim ersten Start.

## Im Rosenweg

- **Vorgaben:** Deutsch mit eigenen Tasten für ä, ö und ü, Schwarz auf Gelb (hoher Kontrast), Tastenränder, Vorschau, Vibration und Zahlenreihe; Höhe etwa 3/8 der Bildschirmhöhe. Danach ist alles frei einstellbar.
- **Einstellungen pro Person:** Auf geteilten Telefonen gehören die Einstellungen der angemeldeten Person. Sie liegen (über Rosenweg Gerät) auf der Telefonanlage und sind nach dem Anmelden an jedem geteilten Telefon wieder da. Ohne Rosenweg Gerät bleiben sie wie gewohnt auf dem Handy.
- **Rosenweg Updater:** Auf Handys ohne Rosenweg Gerät prüft die App den App-Katalog der Telefonanlage und bietet Aktualisierungen für alle installierten Rosenweg-Apps an. Jede APK wird vorher geprüft (Prüfsumme, Paketname, Version, Rosenweg-Signatur); Android lässt jede Installation bestätigen. Auf geteilten Telefonen übernimmt das Rosenweg Gerät.

## Installation

- **Geteilte Rosenweg-Telefone:** Rosenweg Gerät installiert die App automatisch aus dem App-Katalog der Telefonanlage und hält sie aktuell.
- **Eigenes Handy:** APK von [www.rosenweg4303.ch/apps](https://www.rosenweg4303.ch/apps) herunterladen und installieren. Danach hält der Rosenweg Updater die Rosenweg-Apps aktuell.

Die App gibt es nicht im Play Store und nicht auf F-Droid. Sie ist mit dem Rosenweg-Schlüssel signiert; Fassungen anderer Herkunft lassen sich nicht darüber installieren.

## Bauen

Die App baut gegen den Rosenweg-Fork der Fossify Commons und die Rosenweg-Module `cloud`/`updater` (`ch.rosenweg4303:*`). Diese kommen aus dem lokalen Maven-Repository (`mavenLocal()`), also zuerst [commons-rosenweg](https://github.com/Rosenweg/commons-rosenweg) veröffentlichen:

```bash
# in commons-rosenweg, Zweig rosenweg
./gradlew -PVERSION=7.1.3-rosenweg.1 publishToMavenLocal

# in diesem Repo, Zweig rosenweg
./gradlew assembleFossRelease
```

Die APK liegt danach unter `app/build/outputs/apk/foss/release/`. Signiert wird mit `keystore.properties` (Vorlage: `keystore.properties_sample`); die Datei und der Schlüssel liegen nicht im Repo. Paketname: `ch.rosenweg4303.keyboard`.

## Herkunft und Lizenz

Fork von [Fossify Keyboard](https://github.com/FossifyOrg/Keyboard). Der Zweig `main` folgt dem Original, die Rosenweg-Anpassungen liegen im Zweig `rosenweg`. Herzlichen Dank an das Fossify-Team und alle Mitwirkenden für die solide Grundlage.

Lizenz: GNU General Public License v3.0, siehe [LICENSE](LICENSE).
