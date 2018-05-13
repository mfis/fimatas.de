---
title: "Backup"
date: 2018-05-01T10:27:05+02:00
draft: true
showDate: true
tags: ["backup"]
---

Die Lebensdauer von Speichermedien in Rechnern ist auf einem guten Stand. Dennoch ist es leider nie die Frage, ob ein Speichermedium irgendwann einen Defekt erleidet, sondern lediglich wann.

Es gibt viele Empfehlungen zum Thema Backup der persönlichen Daten auf einem Rechner, z.B:

* Ein Backup sollte nicht auf dem Gerät liegen, auf dem auch die zu sichernden Daten liegen. 
Dies würde bei versehentlich gelöschten Dateien helfen, nicht jedoch bei einem Defekt des Gerätes.
* Eine 1-zu-1 Spiegelung der zu sichernden Daten (z.B: über rsync) auf einem Backup-Meduim hilft bei einem Defekt des Gerätes, nicht aber bei versehentlich gelöschten Daten. Für geänderte/gelöschte Daten sollten daher Generations/Versions-Stände angelegt werden.
* Es sollten idealerweise mindestens zwei Backup-Ziele (neben dem zu sichernden Gerät selbst) existieren, um die Wahrscheinlicheit zu verringern, dass das Backup-Ziel zeitgleich mit dem zu sichernden Gerät einen Defekt erleidet.
* Ebenfalls im Idealfall sollten diese Backups auf unterschiedlichen Software-Lösungen basieren, um für den Fall eines Bugs in der Backup-Software beim Backup oder Restore vorbereitet zu sein.
* Mindestens ein Backup sollte an einem anderen Standort als das zu sichernden Gerät aufbewahrt werden, um dem gleichzeitigen Ausfall beider Geräte z.B. bei Überschwemmung, Blitzschlag, Diebstahl entgegen zu wirken.
* Nach der Erstanlage eines Backups sowie nach Updates der Backup-Software oder des Betriebssystems des zu sichernden Gerätes sollte der Restore der gesicherten Daten getestet werden.

Über diesen technischen Empfehlungen steht aber immer:  
Die beste Backup-Strategie nützt nichts, wenn man das Backup nicht oder zu selten durchführt.
Regelmäßig ein Script oder Programm zu starten oder gar Kommandozeilenbefehle runter zu tippen, wird schnell lästig, man verschiebt und/oder vergisst es. So ging es zumindest mir.

Meiner Meinung nach sollte das Backup daher automatisch laufen. Das Programm sollte nach dem Hochfahren automatisch gestartet werden, im Hintergrund laufen und in einem voreingestellten Intervall ohne Nutzerinteraktion die Backups ausführen. Meldungen an den Nutzer sollte es ausschließlich im Fehlerfall geben.
Desweiteren war es mir wichtig, dass das Programm die Backup-Dateien vor dem Schreiben auf das Ziel verschlüsselt. 

Die Wahl des Programms ist bei mir nach einem Wechsel von CrashPlan aufgrund der Flexibilität bei den Speicher-Zielen auf Arq gefallen.  
Arq unterstützt diverse Cloud-Dienste (auch gleichzeitig) und unterschiedliche Backup-Konfigurationen je Ziel.

Ein Restore-Test für einzelne gelöschte Dateien ist vergleichsweise einfach: Test-Datei anlegen, Backup laufen lassen, Datei löschen und über Arq die Datei zurückholen.  
Aufwändiger ist hier der Test eines Restores noch vollständiger Neuinstallation. Hierzu habe ich eine macOS Installation in einer VirtualBox VM aufgesetzt und darin den Restore getestet. Dies ist sicherlich aufgrund des Aufwands kein Test, den man nach den Update jeder Minor-Version von Arq durchführen möchte, zumindest aber nach großen Versionssprüngen halte ich die Prozedur für durchaus sinnvoll.

Täglich, besser stündlich eine externe (USB-)Festplatte für das Backup anzuschließen, habe ich keine drei Tage lang durchgezogen. Wenn man dann noch der Empfehlung mit dem zweiten Standort nachgehen möchte, sind der praktischen Durchführung regelmäßigen Backups sehr schnell Grenzen gesetzt. 

Da kabelgebundene Speichermedien damit rausfallen (und auch aufgrund der Empfehlung mit dem zweiten Standort), habe ich auf eine Kombination aus Cloud- und lokalem Netzwerkspeicher gewechselt.

Auf dem Cloudspeicher werden alle persönlichen, nicht rekonstruierbaren Dateien, wie zum Beispiel Bilder, Videos, Dokumente gesichert. Bei mir sind das aktuell etwa 120 GB.
Auf dem lokalen Netzwerkspeicher werden zusätzlich noch rekonstruierbare Dateien gesichert, wie zum Beispiel gekaufte Musik und VM-Images. Zusammen sind das bei mir etwa 250 GB.
Bei dem Cloudspeicher habe ich auf diese Daten aufgrund der geringen Upload-Bandbreite von 2MBit/s bei mir zu Hause verzichtet.
Betriebssystem-Dateien sowie Apps werden gar nicht gesichert, da diese einfacher wieder neu heruntergeladen und installiert werden können.

Für den lokalen Netzwerkspeicher nutze ich eine 2 TB 3,5 Zoll USB-Festplatte an einem Raspberry Pi. Der RaspPi läuft mit dem Standard Raspbian auf Basis von Debian Stretch. Der Zugriff vom Backup-Programm läuft über SFTP mit einem eigenen Linux-User für die Backup-Dateien. Die Festplatte ist mittels `hdparm` auf eine Spindown-Zeit von 5 Minuten konfiguriert, was sich bei stündlichen Backups, welche jeweils meist nur 1-2 Minuten laufen, als praktisch erwiesen hat. Damit dreht die Platte, solange der Rechner läuft pro Stunde nur 5 Minuten und bei ausgeschaltetem Rechner garnicht, was der Lebensdauer der Platte sowie dem Stromverbrauch zu Gute kommt.

Zur Umsetzung der Empfehlungen hinsichtlich des zweiten Backup-Zieles sowie des zweiten Standorts bin ich nach einigen Wechseln bei Backblaze B2 gelandet. Der passende Anbieter hängt hier stark von der Datenmenge ab. Viele Anbieter von Cloud-Speicher bieten Festpreise in Staffelungen fester Speichergrößen an. Oftmals existieren hier zwischen 100 GB und 1-2 TB keine Zwischenstufen und meist ist das Paket mit 1-2 TB preislich schon recht happig. Wenn man dann von den 1-2 TB nur 120GB nutzt, ist das zusätzlich ärgerlich. Backblaze B2 rechnet nach der exakten Speichermenge plus dem Datentransfer ab. Das macht den monatlichen Preis zwar etwas schwieriger kalkulierbar, unterm Strich aber günstiger als viele Anbieter mit Festpreisen.

Damit bin ich bei zwei Backup-Lokationen außerhalb des zu sichernden Geräts, auf die mittels Arq automatisiert versionierte Backups gespeichert werden. Was fehlt, ist der vierte Punkt der o.g. Aufzählung: Die zweite Backup-Software. Diesen Punkt setze ich nur mit einer Einschränkung um. 
Persönliche Dokumente werden bei mir von MacOS automatisch auf iCloud synchronisiert. Damit bleibe ich deutlich unter 50 GB und komme daher mit dem kleinsten Speicheraket aus.
Persönliche Bilder und Videos werden manuell auf eine Online Bilder-Gallerie synchronisiert. Ich nutze hier die von mir  selbstgeschriebene ("Photos")[https://github.com/mfis/Photos] Anwendung mit dazugeörigem (Client)[https://github.com/mfis/PhotosClient].
Alternativ wären hier auch Dienste wie Google Photos, Own-Cloud etc. möglich. Dabei sollte aber besonderes Augenmerk auf das Tehma Datensicherheit/Verschlüsselung gelegt werden.
Beide zuletzt genannten Lösungen (iCloud, Photos) nutzen keine Versionierung. Zudem erfolgt die Photos-Synchronisation manuell.
Als alleinige Backup-Lösung wäre TODO