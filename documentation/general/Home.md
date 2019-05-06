### Willkommen beim Wiki für ch.bfh.bti7081.s2019.yellow

## Authoren:
* Azarny: Nadine Siegfried
* stormrider959: Lucien Heuzeveldt
* yaronwalter7: Yaron Walter
* Miishale: Michelle Lüscher
* oppli: Simon Oppliger

## Code
Befindet sich unter src/...

## Wiki update im Projekt 
Änderungen von Remote aktualisieren, die Files sollten dennoch nur online bearbeitet werden.
Anleitung, wie das submodul `docs` aktualisiert werden kann:
1. Im lokalen Repository die Git-Konsole öffnen
2. `git pull` und `git merge`ausführen, das aktualisiert das Haupt-Repository
3. `git submodule update --recursive --remote`ausführen. Dies aktualisiert alle Submodule, welche unter `.gitmodules`aufgelistet sind. 
4. Abschliessend noch ein `git push`, damit die aktualisierte Version auch im Remote-Repository ist.