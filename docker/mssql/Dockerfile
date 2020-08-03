FROM mcr.microsoft.com/mssql/server:2019-latest

USER mssql

# config Verzeichnis erstellen
RUN mkdir -p /usr/config

# Aktuelles Verzeichnis auf /usr/config setzen
WORKDIR /usr/config

# Alle Dateien in Container nach /usr/config kopieren
COPY . /usr/config

# Rechte zum ausfuehren der Skripte setzen (nur mit root moeglich)
USER root
RUN chmod +x /usr/config/entrypoint.sh
RUN chmod +x /usr/config/configure-db.sh
USER mssql

# Entryoint des Containers beim starten auf die neue entrypoint.sh setzen
ENTRYPOINT ["./entrypoint.sh"]

# Log-Datei mitverfolgen
CMD ["tail -f /dev/null"]

# Healthcheck alle 15 Sekunden ob DB bereit ist
HEALTHCHECK --interval=15s CMD /opt/mssql-tools/bin/sqlcmd -U sa -P $SA_PASSWORD -Q "select 1" && grep -q "MSSQL CONFIG COMPLETE" ./config.log
