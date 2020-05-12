# Reading Group javaee7

## Pro JPA in Java EE 8 Kapitel 5 Collection Mapping

Wichtig: immer gegen Interface (Collection, Set, List etc.) programmieren, weil die 
Implementierung vom Persistence Provider ersetzt werden kann.

### Sets or Collections

* Basic Types
* Embeddable Types
 
### Listen

* sortiert: @OrderBy 
  * OrderBy wird beim Einlesen angewandt
  * Liste in Memory sollte auch sortiert sein
  
* mit (Einfüge-)Reihenfolge: @ OrderColumn