import java.io.File
import sbt._

class Project(info: ProjectInfo) extends ParentProject(info) with IdeaProject  {
  lazy val iglootoolsRepository = "Iglootools Releases Repository" at "http://www.iglootools.org/artifactory/iglootools-release"

  override def managedStyle = ManagedStyle.Maven
  val publishTo = "Iglootools" at "http://www.iglootools.org/artifactory/iglootools-release-local"
  Credentials(Path.userHome / ".ivy2" / ".credentials", log)

  lazy val lang = project("lang", "iglootools-commons-lang", new Lang(_))
  lazy val javaLang = project("java-lang", "iglootools-commons-java-lang", new JavaLang(_), lang)
  lazy val scalaLang = project("scala-lang", "iglootools-commons-scala-lang", new ScalaLang(_), lang)
  lazy val ext = project("ext", "iglootools-commons-ext", new Ext(_), javaLang, scalaLang)
  lazy val test = project("test", "iglootools-commons-test", new Test(_))

  object Dependencies {
    val HibernateVersion = "3.6.1.Final"
    val SpringFrameworkVersion = "3.0.5.RELEASE"
    val HamcrestVersion = "1.1"
    val JunitVersion = "4.8.2"
    val PostgisJdbcVersion = "1.3.3"
    val Jsr275Version = "1.0-beta-2"
    val ServletApiVersion = "2.5"
    val RadeoxVersion = "1.0-b2"
    val LogbackVersion = "0.9.27"
    val Struts2Version = "2.2.1.1"
    val JunitInterfaceVersion = "0.6"
    val LiftJsonVersion = "2.3"
    val CamelVersion = "2.7.1"
    val ActiveMqVersion = "5.5.0"


    object Test {
      lazy val junit = "junit" % "junit" % Dependencies.JunitVersion % "test" withSources()
      lazy val hamcrest = "org.hamcrest" % "hamcrest-all" % Dependencies.HamcrestVersion % "test" withSources()
      lazy val scalaTest = "org.scalatest" % "scalatest" % "1.2"  % "test" withSources()
      lazy val mockito = "org.mockito" % "mockito-all" % "1.8.5" % "test" withSources()
      lazy val logbackTest = "ch.qos.logback" % "logback-classic" % Dependencies.LogbackVersion % "test" // withSources()
      lazy val springTest = "org.springframework" % "spring-test" % Dependencies.SpringFrameworkVersion % "test" withSources()
      lazy val springContextSupportTest = "org.springframework" % "spring-context-support" % Dependencies.SpringFrameworkVersion % "test" withSources()
    }

    def ivyXML =
      <dependencies>
          <exclude org="ehcache"/>
          <exclude module="hibernate" /> <!-- older versions of hibernate -->
          <exclude module="postgis-stubs" /> <!-- included by postgis, but conflicts with real postgres-jdbc -->
          <exclude org="commons-logging" /> <!-- we want to use jcl-over-slf4j instead -->
        <!-- Spring Security, Struts2, etc depend on old spring -->
          <override org="org.springframework" rev="3.0.5.RELEASE" />

        <!-- Struts2 depends en 1.2.1 -->
          <override module="commons-fileupload" rev="1.2.2" />
          <override module="commons-beanutils" rev="1.8.3" />
      </dependencies>
  }

  class Lang(info: ProjectInfo) extends DefaultProject(info) with IdeaProject {
    override def ivyXML = Dependencies.ivyXML
    // dependencies we always want
    lazy val slf4jApi = "org.slf4j" % "slf4j-api" % "1.6.1" withSources()
    lazy val jclOverSlf4j = "org.slf4j" % "jcl-over-slf4j" % "1.6.1" withSources()
    lazy val guava = "com.google.guava" % "guava" % "r08" withSources()
    lazy val jodaTime = "joda-time" % "joda-time" % "1.6.2" withSources()

    // test dependencies
    lazy val junit = Dependencies.Test.junit
    lazy val scalaTest = Dependencies.Test.scalaTest
    lazy val mockito = Dependencies.Test.mockito
    lazy val springTest = Dependencies.Test.springTest

    override def testOptions = super.testOptions ++ Seq(TestArgument(TestFrameworks.JUnit, "-q", "-v"))
  }

  class JavaLang(info: ProjectInfo) extends DefaultProject(info) with IdeaProject {
    override def ivyXML = Dependencies.ivyXML

    // java <-> scala interop
    lazy val scalaj_collection = "org.scalaj" % "scalaj-collection_2.8.0" % "1.0"

    // test dependencies
    lazy val junit = Dependencies.Test.junit
    lazy val scalaTest = Dependencies.Test.scalaTest
    lazy val mockito = Dependencies.Test.mockito
    lazy val springTest = Dependencies.Test.springTest

    override def testOptions = super.testOptions ++ Seq(TestArgument(TestFrameworks.JUnit, "-q", "-v"))
  }

  class ScalaLang(info: ProjectInfo) extends DefaultProject(info) with IdeaProject {
    override def ivyXML = Dependencies.ivyXML
    override def testOptions = TestFilter(s => true) :: super.testOptions.toList

    lazy val grizzledSlf4j = "org.clapper" %% "grizzled-slf4j" % "0.4"
    lazy val scalaTime = "org.scala-tools.time" %% "time" % "0.3" // withSources()

    lazy val servletApi = "javax.servlet" % "servlet-api"  % Dependencies.ServletApiVersion % "provided" withSources()


    // test dependencies
    lazy val junit = Dependencies.Test.junit
    lazy val scalaTest = Dependencies.Test.scalaTest
    lazy val mockito = Dependencies.Test.mockito
    lazy val springTest = Dependencies.Test.springTest
    lazy val springContextSupportTest = Dependencies.Test.springContextSupportTest
  }

  class Ext(info: ProjectInfo) extends DefaultProject(info) with IdeaProject {
    override def ivyXML = Dependencies.ivyXML

    // JodaTimeSerializer
    lazy val liftJson = "net.liftweb" %% "lift-json" % Dependencies.LiftJsonVersion % "provided" withSources()
    lazy val liftJsonExt = "net.liftweb" %% "lift-json-ext" % Dependencies.LiftJsonVersion  % "provided" withSources()

    // JPA utilities utils
    lazy val hibernateCore = "org.hibernate" % "hibernate-core" % Dependencies.HibernateVersion % "provided" withSources()
    lazy val hibernateEntityManager = "org.hibernate" % "hibernate-entitymanager" % Dependencies.HibernateVersion % "provided" withSources()
    lazy val postgisJdbc = "org.postgis" % "postgis-jdbc" % Dependencies.PostgisJdbcVersion % "provided" withSources()
    lazy val jts = "com.vividsolutions" % "jts" % "1.8" % "provided" // withSources()

    // springsecurity
    lazy val springSecurity = "org.springframework.security" % "spring-security-core" % Dependencies.SpringFrameworkVersion % "provided" withSources()

    // units
    lazy val jsr275 = "net.java.dev.jsr-275" % "jsr-275" % Dependencies.Jsr275Version % "provided" withSources()

    // web
    lazy val servletApi = "javax.servlet" % "servlet-api"  % Dependencies.ServletApiVersion % "provided" withSources()
    lazy val struts2Core = "org.apache.struts" % "struts2-core" % Dependencies.Struts2Version % "provided"
    lazy val rome = "rome" % "rome" % "0.9" % "provided"

    // markup
    lazy val radeox = "radeox" % "radeox" % Dependencies.RadeoxVersion % "provided" // withSources()

    // test dependencies
    lazy val junit = Dependencies.Test.junit
    lazy val scalaTest = Dependencies.Test.scalaTest
    lazy val mockito = Dependencies.Test.mockito
    lazy val springTest = Dependencies.Test.springTest

    override def testOptions = super.testOptions ++ Seq(TestArgument(TestFrameworks.JUnit, "-q", "-v"))
  }

  class Test(info: ProjectInfo) extends DefaultProject(info) with IdeaProject {
    override def ivyXML = Dependencies.ivyXML

    lazy val jodaTime = "joda-time" % "joda-time" % "1.6.2" withSources()

    // FIXME: this should be "provided", but intellij does not seem to like it...
    lazy val hamcrest = "org.hamcrest" % "hamcrest-all" % Dependencies.HamcrestVersion  withSources()
    lazy val junit = "junit" % "junit" % Dependencies.JunitVersion withSources()
    lazy val springTest = "org.springframework" % "spring-test" % Dependencies.SpringFrameworkVersion withSources()

    override def testOptions = super.testOptions ++ Seq(TestArgument(TestFrameworks.JUnit, "-q", "-v"))
  }

}
