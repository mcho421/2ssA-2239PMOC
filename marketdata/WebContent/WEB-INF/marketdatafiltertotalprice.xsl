<xsl:stylesheet
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
     version="1.0">
<xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

<xsl:param name="type"/>

<xsl:template match="/">
<xsl:value-of select="sum(MarketData/Data/price[../type=$type])"/>
</xsl:template>

</xsl:stylesheet>


