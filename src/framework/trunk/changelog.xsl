<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="text"/>

  <xsl:template match="/log/logentry">
    <xsl:value-of select="@revision"/><xsl:text> </xsl:text><xsl:value-of select="msg"/><xsl:text> </xsl:text>[<xsl:value-of select="author"/>]
  </xsl:template>

</xsl:stylesheet>
