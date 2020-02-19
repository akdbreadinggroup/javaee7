# Reading Group javaee7

## Installation
1. Java JDK 8 installieren
2. Maven installieren
3. Docker installieren

## Images laden und Container starten
Ins Root-Verzeichnis des Projekt wechseln  
 
 Starten der Container (Falls noch nicht vorhanden baut werden sie auch gebaut)  
`docker-compose up`

Alternativ mit `-d` um detached Log-Ausgaben in der Bash zu vermeiden  
`docker-compose up -d`

Stoppen der Container  
`docker-compose stop`

Stoppen und Container entfernen  
`docker-compose down`

Bash in Container ausführen  
`docker exec -it rg-mssql bash`

## Build & Deployment
Mit `mvn install` werden automatisch alle .war-Dateien gebaut.  
Läuft der JBoss werden sie ebenfalls auch gleich deployed

### GIT autocrlf deaktivieren

In einer Bash prüfen ob autocrlf in eine Form aktiviert ist  
`git config core.autocrlf`  
`git config --global core.autocrlf`

Ergebnis sollte false sein, falls nicht folgendes eingeben  
`git config core.autocrlf false`  
`git config --global core.autocrlf false` 

und erneut prüfen.

## Datenbank

### Allgemein
Verwendetes DBMS ist MSSQL-2019

### Login

URL für **addressbook** Datenbank  
<jdbc:sqlserver://localhost:1433;databaseName=addressbook>

Benutzer | Passwort
---------| -------------
sa       | ReadingGroupDB2020  
Benutzer | Passwort123

(*Benutzer* soweit möglich verwenden)  
Siehe auch *docker-compose.yml*

## JBoss

### JBoss Web Management

#### Adresse
<http://localhost:9990>

#### Login
Benutzer | Passwort
---------| -------------
 admin   | 1234

## Informationen

### Project Structure
(Ctrl+Alt+Shift+S)  
Project SDK: 1.8  
Project language level: 8

## Demo
<http://localhost:8080/demo/foobar/bar/hans>

## Soap
### WSDL
<http://localhost:8080/soap-service/Hello?WSDL>

### Web Client
<http://localhost:8080/soap-client-web/HelloServlet>

## Rest
<http://localhost:8080/rest/helloworld>