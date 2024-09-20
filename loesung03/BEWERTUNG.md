Implementierung:
- in jedem sort-Aufruf wird die gesamte Liste durchlaufen (getMiddle()). Besser wäre es am Anfang einmalig die Liste zu durchlaufen und die Länge als Parameter an weitere sort-Aufrufe zu übergeben.
- in der sort-Methode ist die Variable 'temp' redundant zu Argument 'head', was ein wenig verwirrt. Intellij gibt auch eine Warnung (((nicht so wichtig: generell besser einen treffenderen Namen wählen als temp, das macht den Code leserlicher zb. head, firstNode, ersterKnoten, result, mid, pointer ... ))) (-1p) 
94/100

Test: 
- Test für die Stabilität fehlt
- Variablennamen wie var6, var5 machen den Code unleserlich, lieber was anderes verwenden (siehe oben). Warum zum Beispiel var4, woher kommt die '4'? Und was ist mit var1,var2,var3,var7 passiert? Redundante Variable (-1p)
91/100
