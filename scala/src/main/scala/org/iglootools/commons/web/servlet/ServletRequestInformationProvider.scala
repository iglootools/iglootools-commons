package org.iglootools.commons.web.servlet
import org.iglootools.commons.web.servlet
import javax.servlet.http.HttpServletRequest

trait ServletRequestInformationProvider {
  private final val HTTP_DEFAULT_PORT: Int = 80
  private final val HTTPS_DEFAULT_PORT: Int = 443

  // See http://stackoverflow.com/questions/106179/regular-expression-to-match-hostname-or-ip-address
  private val ValidHostnameRegex = """^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\-]*[a-zA-Z0-9])\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\-]*[A-Za-z0-9])$""".r

  /**
   * The host that was requested
   */
  def host: String

  /**
   * Whether it is a hostname (vs IP)
   */
  def isHostname: Boolean = ValidHostnameRegex.pattern.matcher(host).matches

  /**
   * The Scheme (http, https, ..)
   */
  def protocolScheme: String

  /**
   * Whether the transport is secure (true for https)
   */
  def isSecure: Boolean

  /**
   * The server port (80, 443, 8080)
   */
  def serverPort: Int

  /**
   * The request URI (this includes the context path, but not the parameters)
   * e.g. /igloofinder/home
   */
  def requestURI: String

  /**
   * The parameters without the trailing '?' (e.g. test=ok)
   */
  def queryString: Option[String]

  /**
   * The context path (e.g. /igloofinder)
   */
  def contextPath: String

  /**
   * The complete request URL (http://www.mydomain.com/contextpath/home?test=ok
   */
  def requestURL: String = {
    requestURL(hostname=host)
  }

  /**
   * The base URL (e.g. http://www.mydomain.com/contextpath)
   */
  def baseUrl: String = baseUrl(appendContextPath=true)


  /**
   * Reconstructs the complete request URL with a different hostname
   * (can be used to generate redirect URLs with a different subdomaine)
   */
  protected[servlet] def requestURL(hostname: String): String = {
    val sb = new StringBuilder
    sb.append(baseUrl(hostname=hostname, appendContextPath=false))
    sb.append(requestURI)
    queryString.foreach { case s =>
      sb.append("?")
      sb.append(s)
    }
    sb.toString
  }

  private def appendServerPortIfNecessary(sb: StringBuilder): StringBuilder = {
    if (!isDefaultPort(serverPort)) {
      sb.append(":")
        .append(serverPort)
    }
    sb
  }

   /**
   * The base URL, possibly with a different hostname and with optional context path
   */
  private def baseUrl(hostname: String = host, appendContextPath: Boolean): String = {
    val sb = new StringBuilder
    sb.append(protocolScheme)
    sb.append("://")
    sb.append(hostname)
    appendServerPortIfNecessary(sb)
    if(appendContextPath)
      sb.append(contextPath)
    sb.toString
  }

  private def isDefaultPort(serverPort: Int): Boolean = {
    return "http".equals(protocolScheme) && serverPort == HTTP_DEFAULT_PORT || "https".equals(protocolScheme) && serverPort == HTTPS_DEFAULT_PORT
  }
}