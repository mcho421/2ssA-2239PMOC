<xsl:stylesheet
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
     version="1.0">

<xsl:param name="type"/>

<xsl:template match="/">
<MarketDatas>
    <xsl:copy-of select="/MarketData/Data[type/text()=$type]"/>
</MarketDatas>
</xsl:template>

</xsl:stylesheet>


