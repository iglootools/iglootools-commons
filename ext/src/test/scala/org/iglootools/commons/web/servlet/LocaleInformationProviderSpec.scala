package org.iglootools.commons.web.servlet

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.util.Locale

case class MyLocaleProvider(val host: String) extends LocaleInformationProvider with ServletRequestInformationProvider{
  def contextPath: String = "/igloofinder"
  def queryString: Option[String] = Some("test=ok")
  def requestURI: String = "/igloofinder/home"
  def isSecure: Boolean = false
  def serverPort: Int = 80
  def protocolScheme: String = "http"
}

@RunWith(classOf[JUnitRunner])
class LocaleInformationProviderSpec extends Spec with ShouldMatchers{

  describe("Locale Information Provider") {
    it("should extract language and country") {
      MyLocaleProvider("en.us.igloofinder.com").languageCode should be === Some("en")
      MyLocaleProvider("en.us.igloofinder.com").countryCode should be === Some("US")
      MyLocaleProvider("en.us.igloofinder.com").urlLocale should be === Some(new Locale("en", "US"))
    }

    it("should extract language only") {
      MyLocaleProvider("en.igloofinder.com").languageCode should be === Some("en")
      MyLocaleProvider("en.igloofinder.com").countryCode should be === None
      MyLocaleProvider("en.igloofinder.com").urlLocale should be === Some(new Locale("en"))

    }

    it("should extract neither the language nor the country when hostname does not contain it") {
      MyLocaleProvider("www.igloofinder.com").languageCode should be === None
      MyLocaleProvider("www.igloofinder.com").countryCode should be === None
      MyLocaleProvider("www.igloofinder.com").urlLocale should be === None
    }

    it("should extract neither the language nor the country when hostname is an IP") {
      MyLocaleProvider("192.168.1.1").languageCode should be === None
      MyLocaleProvider("192.168.1.1").countryCode should be === None
      MyLocaleProvider("192.168.1.1").urlLocale should be === None
    }

    it("should extract non localized host when host contains language and country") {
      MyLocaleProvider("en.us.igloofinder.com").nonLocalizedHost should be === "igloofinder.com"
    }

    it("should extract non localized host when host contains language only") {
      MyLocaleProvider("en.igloofinder.com").nonLocalizedHost should be === "igloofinder.com"
    }

    it("should extract non localized host when host is not localized") {
      MyLocaleProvider("igloofinder.com").nonLocalizedHost should be === "igloofinder.com"
    }

    it("should strip www. when extracting non localized host") {
      MyLocaleProvider("www.igloofinder.com").nonLocalizedHost should be === "igloofinder.com"
    }

    it("should suggest localized URL with country") {
      MyLocaleProvider("www.igloofinder.com").suggestLocalizedRequestURL(
        locale=new Locale("en", "US"), ignoreCountry=false) should be === Some("http://en.us.igloofinder.com/igloofinder/home?test=ok")
    }

    it("should suggest localized URL without") {
      MyLocaleProvider("www.igloofinder.com").suggestLocalizedRequestURL(
        locale=new Locale("en", "US"), ignoreCountry=true) should be === Some("http://en.igloofinder.com/igloofinder/home?test=ok")
    }

    it("should not suggest localized URL when host is an IP") {
      MyLocaleProvider("127.0.0.1").suggestLocalizedRequestURL(locale=new Locale("en", "US")) should be === None
    }
  }
}