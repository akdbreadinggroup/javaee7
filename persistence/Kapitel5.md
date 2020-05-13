# Reading Group javaee7

## Pro JPA in Java EE 8 Kapitel 5 Collection Mapping

Wichtig: immer gegen Interface (Collection, Set, List etc.) programmieren, weil die 
Implementierung vom Persistence Provider ersetzt werden kann.

### Sets or Collections

Employee mit Adressen und Rollen:

* Basic Types (Rollen)
* Embeddable Types (Adressen)
 
### Listen

* sortiert: @OrderBy 
  * OrderBy wird beim Einlesen angewandt
  * Liste in Memory sollte auch sortiert sein
  
* mit (Einfüge-)Reihenfolge: @OrderColumn

### Maps

* Key: Basic Type / Embedded Type / Entity
* Value: Basic Type / Embedded Type / Entity

Keying by
 * Basic Type (-> evtl. enum) / Entity Attribute @MapKey, @MapKeyEnumerated
 * Embeddable Type: 
    * eher unüblich, eher ein Anti-Pattern? 
    * Aber: Als logischer Key in der Map OK? 
 * Entity: funktioniert gut, da Entities unique id haben
 
 ### Duplicates
 
 * JPA Spezifikation sagt nicht, ob Duplikate erlaubt sind oder nicht
 * in Listen mit OrderColumn möglich, da Duplikate durch Order-Eintrag physisch unterscheidbar
 
 ### Null Values
 * JPA Spezifikation sagt nicht, ob Nulls in Collections erlaubt sind oder nicht
 * Nur Collections gemapped mit ElementCollections / Join Tables können überhaupt Null-Werte enthalten