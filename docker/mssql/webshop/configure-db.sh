#!/bin/bash

echo "======= START WEBSHOP DB CONFIGURATION ========" | tee -a ./config.log
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -d master -i webshop/setup.sql

echo "======= START WEBSHOP CSV IMPORT =======" | tee -a ./config.log
# Importiere Daten aus der CSV-Datei
/opt/mssql-tools/bin/bcp rgdatabase.dbo.Product in "/usr/config/webshop/Products.csv" -c -t',' -S localhost -U sa -P $SA_PASSWORD