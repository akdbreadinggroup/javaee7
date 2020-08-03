<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:datasources="urn:jboss:domain:datasources:6.0">

    <xsl:output method="xml" indent="yes"/>

    <!-- MSSQL-Driver hinzufuegen -->
    <xsl:template match="//datasources:subsystem/datasources:datasources/datasources:drivers">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
            <driver name="mssql" module="com.microsoft.sqlserver.jdbc" xmlns="urn:jboss:domain:datasources:6.0">
                <driver-class>com.microsoft.sqlserver.jdbc.SQLServerDriver</driver-class>
            </driver>
        </xsl:copy>
    </xsl:template>

    <!-- kopiere alles andere -->
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>
