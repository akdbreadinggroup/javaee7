#!/bin/bash

echo "======= START ADDRESSBOOK DB CONFIGURATION ========" | tee -a ./config.log
# Starte Skript um addressbook Tabelle zu erstellen
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -d master -i addressbook/setup.sql

echo "======= START ADDRESSBOOK CSV IMPORT =======" | tee -a ./config.log
# Importiere Daten aus der CSV-Datei
/opt/mssql-tools/bin/bcp rgdatabase.dbo.Contact in "/usr/config/addressbook/Contacts.csv" -c -t',' -S localhost -U sa -P $SA_PASSWORD