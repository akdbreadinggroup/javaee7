#!/bin/bash

# Warten bis MSSQL-Server gestartet hat
export STATUS=1
i=0

while [[ $STATUS -ne 0 ]] && [[ $i -lt 30 ]]; do
	i=$i+1
	/opt/mssql-tools/bin/sqlcmd -t 1 -U sa -P $SA_PASSWORD -Q "select 1" >> /dev/null
	STATUS=$?
done

if [ $STATUS -ne 0 ]; then 
	echo "Error: MSSQL Server took more than 30 seconds to start up."
	exit 1
fi
echo "======= MSSQL SERVER STARTED ========" | tee -a ./config.log


echo "======= START GENERAL DB CONFIGURATION ========" | tee -a ./config.log
# Starte Skript um DB-Schema zu erstellen
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -d master -i setup.sql


echo "======= START ADDRESSBOOK DB CONFIGURATION ========" | tee -a ./config.log
# Starte Skript um DB-Schema zu erstellen
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -d master -i addressbook/setup.sql

echo "======= START ADDRESSBOOK CSV IMPORT =======" | tee -a ./config.log
# Importiere Daten aus der CSV-Datei
/opt/mssql-tools/bin/bcp addressbook.dbo.Contact in "/usr/config/addressbook/Contacts.csv" -c -t',' -S localhost -U sa -P $SA_PASSWORD


echo "======= MSSQL CONFIG COMPLETE =======" | tee -a ./config.log
