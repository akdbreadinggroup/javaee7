#!/bin/bash

# Warten bis MSSQL-Server gestartet hat
sleep 10s

export STATUS=1
i=10

while [[ $STATUS -ne 0 ]] && [[ $i -lt 120 ]]; do
	i=$i+1
	/opt/mssql-tools/bin/sqlcmd -t 1 -U sa -P $SA_PASSWORD -Q "select 1" >> /dev/null
	STATUS=$?
	sleep 1s
done

if [ $STATUS -ne 0 ]; then 
	echo "Error: MSSQL Server took more than 120 seconds to start up."
	exit 1
fi
echo "======= MSSQL SERVER STARTED ========" | tee -a ./config.log


echo "======= START GENERAL DB CONFIGURATION ========" | tee -a ./config.log
# Starte Skript um DB-Schema zu erstellen
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -d master -i setup.sql

#Starte Konfiguration von db zu addressbook
/usr/config/addressbook/configure-db.sh

#Starte Konfiguration von db zu webshop
/usr/config/webshop/configure-db.sh

echo "======= START ENTITYMANAGER DB CONFIGUARTION =======" | tee -a ./config.log
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -d master -i entitymanager/setup.sql

echo "======= MSSQL CONFIG COMPLETE =======" | tee -a ./config.log
