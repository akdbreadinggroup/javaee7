<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:datasources="urn:jboss:domain:datasources:5.0">

    <xsl:output method="xml" indent="yes"/>

    <!-- Datasource hinzufuegen -->
    <xsl:template match="//datasources:subsystem/datasources:datasources">
        <xsl:copy>
            <datasource jndi-name="java:jboss/datasources/MssqlDS" pool-name="MssqlDS"
                        enabled="true"
                        use-java-context="true"
                        statistics-enabled="${{wildfly.datasources.statistics-enabled:${{wildfly.statistics-enabled:false}}}}"
                        xmlns="urn:jboss:domain:datasources:5.0">
                <connection-url>jdbc:sqlserver://${env.MSSQL_HOST:localhost}:${env.MSSQL_PORT:1433};databaseName=${env.MSSQL_DATABASE:addressbook}</connection-url>
                <driver>mssql</driver>
                <security>
                    <user-name>${env.MSSQL_USER:sa}</user-name>
                    <password>${env.MSSQL_PASSWORD:password}</password>
                </security>
                <validation>
                    <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.mssql.MSSQLValidConnectionChecker"/>
                    <validate-on-match>true</validate-on-match>
                    <background-validation>false</background-validation>
                    <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.mssql.MSSQLExceptionSorter"/>
                    <!-- <check-valid-connection-sql>SELECT 1</check-valid-connection-sql> -->
                    <!-- <background-validation>true</background-validation> -->
                    <!-- <background-validation-millis>60000</background-validation-millis> -->
                </validation>
                <!-- <pool> -->
                <!-- <min-pool-size>5</min-pool-size> -->
                <!-- <max-pool-size>50</max-pool-size> -->
                <!-- <prefill>false</prefill> -->
                <!-- <use-strict-min>false</use-strict-min> -->
                <!-- <flush-strategy>FailingConnectionOnly</flush-strategy> -->
                <!-- <flush-strategy>IdleConnections</flush-strategy> -->
                <!-- </pool> -->
            </datasource>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>

    <!-- kopiere alles andere -->
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>
