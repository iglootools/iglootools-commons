package org.iglootools.commons.web.servlet

import java.util.Locale

/**
 * Provides locale information from the URL.
 * This trait is meant to work with hostnames of the forms :
 * <ul>
 *   <li>www.mydomain.com</li>
 *   <li>en.mydomain.com</li>
 *   <li>en.us.mydomain.com</li>
 * </ul>
 */
trait LocaleInformationProvider {
  self: ServletRequestInformationProvider =>
  // accepts domains such as: en.us.igloofinder.com
  private val LanguageAndCountryRegex = """(\w{2})\.(\w{2})\.(.*)""".r
  // accepts domains such as: en.igloofinder.com
  private val LanguageOnlyRegex = """(\w{2})\.(.*)""".r

  /**
   * Suggest a localized Request URL based on the urlLocale
   */
  def suggestLocalizedRequestURL(locale: Locale, ignoreCountry: Boolean=true, defaultLanguage: String = "en"): Option[String] = {
    if(self.isHostname) {
      // defaults to en_US : en.us
      val lang = if ((locale.getLanguage.nonEmpty)) locale.getLanguage.toLowerCase else defaultLanguage
      val country = if ((locale.getCountry.nonEmpty)) Some(locale.getCountry.toLowerCase) else None

      val sb = new StringBuilder
      sb.append(lang)
      sb.append(".")
      country.filterNot {case c => ignoreCountry}.foreach {case c =>
        sb.append(c)
        sb.append(".")
      }
      sb.append(nonLocalizedHost)
      Some(self.requestURL(sb.toString))
    } else {
      None
    }
  }

  def isLocalizedRequestURL: Boolean = {
    languageCode.isDefined || countryCode.isDefined
  }
  /**
   * Returns the non localized part of the hostname
   * <p>
   *   If the complete hostname is en.us.mydomain.com, this will return mydomain.com
   * </p>
   * <p>
   *   Also, if the hostname starts with www., the www prefix is going to be stripped,
   *   so nonLocalizedHost(www.mydomain.com) will return mydomain.com
   * </p>
   */
  def nonLocalizedHost: String = {
    host match {
      case LanguageAndCountryRegex(_, _, nonLocalizedHost) => nonLocalizedHost
      case LanguageOnlyRegex(_, nonLocalizedHost) => nonLocalizedHost
      case _ => host.stripPrefix("www.")
    }
  }

  /**
   * Returns the most specific locale that matches the language and country information embedded in the URL
   */
  def urlLocale: Option[Locale] = {
    languageCode match {
      case Some(lang) =>
        countryCode match {
          case Some(country) => Some(new Locale(lang, country))
          case _ => Some(new Locale(lang))
        }
      case _ => None
    }
  }

  /**
   * Extracts the language code that is embedded inside the URL
   */
  def languageCode: Option[String] = doWithValidHostname { case host =>
    host match {
      case LanguageAndCountryRegex(language, country, _) => Some(language)
      case LanguageOnlyRegex(language, _) => Some(language)
      case _ => None
    }
  }(host)

  /**
   * Extracts the country code that is embedded inside the URL
   */
  def countryCode: Option[String] = doWithValidHostname { case host =>
    host match {
      case LanguageAndCountryRegex(language, country, _) => Some(country.toUpperCase)
      case _ => None
    }
  }(host)

  private def doWithValidHostname(f: (String) => Option[String])(host: String): Option[String] = {
    if(isHostname)
      f(host)
    else
      None
  }
}