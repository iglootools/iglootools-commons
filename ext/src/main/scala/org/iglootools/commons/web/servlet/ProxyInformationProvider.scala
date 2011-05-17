package org.iglootools.commons.web.servlet;
trait ProxyInformationProvider {
  /**
   * Returns the proxy address if the server is behind a proxy (None otherwise)
   */
  def proxyAddress: Option[String]

  /**
   * Returns the proxy hostname if the server is behind a proxy (None otherwise)
   */
  def proxyHost: Option[String]
}