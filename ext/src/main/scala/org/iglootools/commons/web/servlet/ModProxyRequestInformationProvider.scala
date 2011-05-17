package org.iglootools.commons.web.servlet

import javax.servlet.http.HttpServletRequest
import java.net.URL

/**
 * @see http://wiki.eclipse.org/Jetty/Tutorial/Apache#Configuring_mod_proxy_http
 */
class ModProxyRequestInformationProvider(private[this] val servletRequest: HttpServletRequest)
  extends RequestInformationProvider {

  def clientAddress: String = assumeNotNull(servletRequest.getHeader("X-Forwarded-For"))
  def clientHost: String = clientAddress
  def protocolScheme: String = Option(servletRequest.getHeader("X-Forwarded-Proto")).getOrElse(servletRequest.getScheme)
  def isSecure: Boolean = protocolScheme == "https"
  def serverPort: Int = 80 // we assume it is always 80
  def requestURI: String = servletRequest.getRequestURI
  def contextPath: String = servletRequest.getContextPath
  def queryString: Option[String] = Option(servletRequest.getQueryString)

  def host: String = assumeNotNull(servletRequest.getHeader("X-Forwarded-Host"))

  def proxyHost: Option[String] = Some(servletRequest.getRemoteHost) // X-Forwarded-Server ?
  def proxyAddress: Option[String] = Some(servletRequest.getRemoteAddr)

  private def assumeNotNull(s: String) = {
    assume(s != null, "value cannot be null")
    s
  }
}