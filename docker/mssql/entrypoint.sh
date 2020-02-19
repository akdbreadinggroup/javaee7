#!/bin/bash

# Starte SQL-Server
/opt/mssql/bin/sqlservr &

# Starte Skript zum konfigurieren der Datenbank
/usr/config/configure-db.sh

eval $1
