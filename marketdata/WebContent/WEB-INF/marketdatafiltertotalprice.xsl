<xsl:stylesheet
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
     version="1.0">
<xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />

<xsl:param name="type"/>

<xsl:template match="/">
<xsl:variable name="pass1Result">
	<prices>
	<xsl:apply-templates/>
	</prices>
</xsl:variable>
 <xsl:apply-templates mode="mPass2"
      select="($pass1Result)/*"/>
</xsl:template>

<xsl:template match="/MarketData/Data[type=$type]">
<xsl:choose>
	<xsl:when test="number(price) = number(price)">
		<price>
		<xsl:value-of select="price"/>
		</price>
	</xsl:when>
	<xsl:when test="number(substring(price,4)) = number(substring(price,4))">
		<price>
		<xsl:value-of select="substring(price,4)"/>
		</price>
	</xsl:when>
	<xsl:otherwise>
	</xsl:otherwise>
	
</xsl:choose>

<!--  <xsl:value-of select="sum(MarketData/Data/price[../type=$type])"/> -->
</xsl:template>

 <xsl:template match="/prices" mode="mPass2">
  
   <xsl:value-of select="sum(price)"/>
 </xsl:template>


</xsl:stylesheet>


