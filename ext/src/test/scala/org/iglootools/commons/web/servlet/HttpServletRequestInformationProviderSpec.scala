package org.iglootools.commons.web.servlet

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.util.Locale
import org.springframework.mock.web.MockHttpServletRequest
import javax.servlet.http.HttpServletRequest

@RunWith(classOf[JUnitRunner])
class HttpServletRequestInformationProviderSpec extends Spec with ShouldMatchers{

  describe("Http Servlet Request Information Provider") {
    it("should extract host") {
      val provider = new HttpServletRequestInformationProvider(request())
      provider.host should be === "en.localhost"
    }

    it("should detect hostname") {
      val provider = new HttpServletRequestInformationProvider(request())
      provider.isHostname should be === true
    }

    it("should detect IP") {
      val provider = new HttpServletRequestInformationProvider(request("127.0.0.1"))
      provider.isHostname should be === false
    }

    it("should create baseUrl with non standard port") {
      val provider = new HttpServletRequestInformationProvider(request())
      provider.baseUrl should be === "http://en.localhost:9090/igloofinder"
    }

    it("should create baseUrl with standard HTTP port") {
      val provider = new HttpServletRequestInformationProvider(request(port=80))
      provider.baseUrl should be === "http://en.localhost/igloofinder"
    }

    it("should create baseUrl with standard HTTPS port") {
      val provider = new HttpServletRequestInformationProvider(request(port=443, scheme="https"))
      provider.baseUrl should be === "https://en.localhost/igloofinder"
    }

    it("should create requestURL") {
      val provider = new HttpServletRequestInformationProvider(request())
      provider.requestURL should be === "http://en.localhost:9090/igloofinder/home?test=ok"
    }

    it("should create requestURL without query String") {
      val provider = new HttpServletRequestInformationProvider(request(queryString=None))
      provider.requestURL should be === "http://en.localhost:9090/igloofinder/home"
    }
  }

  def request(host: String = "en.localhost", port: Int=9090, scheme: String="http", queryString:Option[String] = Some("test=ok")): HttpServletRequest = {
    var request: MockHttpServletRequest = new MockHttpServletRequest
    request.setScheme(scheme)
    request.setServerName(host)
    request.setServerPort(port)
    request.setRequestURI("/igloofinder/home")
    request.setQueryString(queryString.getOrElse(null))
    request.setContextPath("/igloofinder")
    request

  }
}