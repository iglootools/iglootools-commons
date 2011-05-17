package org.iglootools.commons.web.servlet

import javax.servlet.http.HttpServletRequest
import java.net.URL

class HttpServletRequestInformationProvider(private[this] val servletRequest: HttpServletRequest) extends RequestInformationProvider {

  def clientHost: String = servletRequest.getRemoteHost
  def clientAddress: String = servletRequest.getRemoteAddr
  def protocolScheme: String = servletRequest.getScheme
  def isSecure: Boolean = servletRequest.isSecure
  def serverPort: Int = servletRequest.getServerPort
  def requestURI: String = servletRequest.getRequestURI
  def contextPath: String = servletRequest.getContextPath
  def queryString: Option[String] = Option(servletRequest.getQueryString)

  def host: String = new URL(servletRequest.getRequestURL.toString).getHost

  def proxyHost: Option[String] = None

  def proxyAddress: Option[String] = None


}