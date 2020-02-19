FROM jboss/wildfly

# Kopiere XSL-Dateien mit Daten zu MSSQL-Treiber und Datasource
ADD addDriver.xsl addDatasource.xsl /opt/jboss/wildfly/

# MSSQL-JDBC-Treiber zu standalone.xml hinzufuegen
RUN java -jar /usr/share/java/saxon.jar -s:/opt/jboss/wildfly/standalone/configuration/standalone.xml -xsl:/opt/jboss/wildfly/addDriver.xsl -o:/opt/jboss/wildfly/standalone/configuration/standalone.xml

# Datasource zu standalone.xml hinzufuegen
RUN java -jar /usr/share/java/saxon.jar -s:/opt/jboss/wildfly/standalone/configuration/standalone.xml -xsl:/opt/jboss/wildfly/addDatasource.xsl -o:/opt/jboss/wildfly/standalone/configuration/standalone.xml

# XSL-Dateien loeschen
RUN rm /opt/jboss/wildfly/addDriver.xsl
RUN rm /opt/jboss/wildfly/addDatasource.xsl

# Verzeichnis fuer MSSQL-JDBC-Treiber anlegen
RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/com/microsoft/sqlserver/jdbc/main

# module.xml zum Verzeichnis hinzufuegen
ADD module.xml /opt/jboss/wildfly/modules/system/layers/base/com/microsoft/sqlserver/jdbc/main/

# Aktives Verzeichnis anpassen
WORKDIR /opt/jboss/wildfly/modules/system/layers/base/com/microsoft/sqlserver/jdbc/main

# Aktuellen MSSQL-JDBC-Treiber v8.2.0 laden
RUN curl -L https://github.com/microsoft/mssql-jdbc/releases/download/v8.2.0/mssql-jdbc-8.2.0.jre8.jar > mssql-jdbc.jar

# admin fuer Remote-Verwaltung hinzufuegen
RUN /opt/jboss/wildfly/bin/add-user.sh admin 1234 --silent

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-c", "standalone.xml", "--debug", "*:8787"]