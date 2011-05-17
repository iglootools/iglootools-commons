package org.iglootools.commons.web.servlet;

trait ClientInformationProvider {
  /**
   * Returns the client address. Always returns the end-user address, even when behind a proxy
   */
  def clientAddress: String

  /**
   * Returns the client hostname. Always returns the client hostname, even when behind a proxy
   */
  def clientHost: String
}