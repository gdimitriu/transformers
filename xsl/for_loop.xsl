<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>
<xsl:variable name="newline">
	<xsl:text></xsl:text>
</xsl:variable>
<xsl:template name="for-loop">
	<xsl:param name="i" select="1"/>
	<xsl:param name="increment" select="1"/>
	<xsl:param name="operator" select="="/>
	<xsl:param name="testValue" select="1"/>
	<xsl:param name="iteration" select="1"/>
	<xsl:variable name="testPassed">
	<xsl:choose>
		<xsl:when test="starts-with($operator, '!=')">
			<xsl:if test="$i != $testValue">
				<xsl:text>true</xsl:text>
			</xsl:if>
		</xsl:when>
		<xsl:when test="starts-with($operator, '<=')">
			<xsl:if test="$i <= $testValue">
				<xsl:text>true</xsl:text>
			</xsl:if>
		</xsl:when>
		<xsl:when test="starts-with($operator, '>=')">
			<xsl:if test="$i >= $testValue">
				<xsl:text>true</xsl:text>
			</xsl:if>
		</xsl:when>
		<xsl:when test="starts-with($operator, '=')">
			<xsl:if test="$i = $testValue">
				<xsl:text>true</xsl:text>
			</xsl:if>
		</xsl:when>
		<xsl:when test="starts-with($operator, '<')">
			<xsl:if test="$i < $testValue">
				<xsl:text>true</xsl:text>
			</xsl:if>
		</xsl:when>
		<xsl:when test="starts-with($operator, '>')">
			<xsl:if test="$i > $testValue">
				<xsl:text>true</xsl:text>
			</xsl:if>
		</xsl:when>
		<xsl:otherwise>
			<xsl:message terminate="yes">
				<xsl:text>Sorry, the for-loop emulator only </xsl:text>
				<xsl:text>handles six operators </xsl:text>
				<xsl:value-of select="$newline"/>
				<xsl:text>(< | > | = | <= | >= | !=). </xsl:text>
				<xsl:text>The value </xsl:text>
				<xsl:value-of select="$operator"/>
				<xsl:text> is not allowed.</xsl:text>
				<xsl:value-of select="$newline"/>
			</xsl:message>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>
	<xsl:if test="$testPassed='true'">
	<!-- Put your logic here, whatever it might be. For the purpose -->
	<!-- of our example, we'll just write some text to the output stream. -->
		<xsl:text>Iteration </xsl:text><xsl:value-of select="$iteration"/>
		<xsl:text>: i=</xsl:text>
		<xsl:value-of select="$i"/><xsl:value-of select="$newline"/>
		<!-- Your logic should end here; don't change the rest of this -->
		<!-- template! -->
		<!-- Now for the important part: we increment the index variable and -->
		<!-- loop. Notice that we're passing the incremented value, not -->
		<!-- changing the variable itself. -->
		<xsl:call-template name="for-loop">
			<xsl:with-param name="i" select="$i + $increment"/>
			<xsl:with-param name="increment" select="$increment"/>
			<xsl:with-param name="operator" select="$operator"/>
			<xsl:with-param name="testValue" select="$testValue"/>
			<xsl:with-param name="iteration" select="$iteration + 1"/>
		</xsl:call-template>
	</xsl:if>
</xsl:template>
<xsl:template match="/">
	<xsl:call-template name="for-loop">
		<xsl:with-param name="i" select="'10'"/>
		<xsl:with-param name="increment" select="'-2'"/>
		<xsl:with-param name="operator" select="'>'"/>
		<xsl:with-param name="testValue" select="'0'"/>
	</xsl:call-template>
</xsl:template>
</xsl:stylesheet>
