import java.io.File
import sbt._

class Project(info: ProjectInfo) extends ParentProject(info) with IdeaProject  {
  lazy val iglootoolsRepository = "Iglootools Releases Repository" at "http://www.iglootools.org/artifactory/iglootools-release"

  override def managedStyle = ManagedStyle.Maven
  val publishTo = "Iglootools" at "http://www.iglootools.org/artifactory/iglootools-release-local"
  Credentials(Path.userHome / ".ivy2" / ".credentials", log)

  lazy val java = project("java", "iglootools-commons-java", new Java(_))
  lazy val scala = project("scala", "iglootools-commons-scala", new Scala(_), java)

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
      //lazy val junitInterface = "com.novocode" % "junit-interface" % Dependencies.JunitInterfaceVersion % "test->default"
      lazy val hamcrest = "org.hamcrest" % "hamcrest-all" % Dependencies.HamcrestVersion % "test" withSources()
      lazy val scalaTest = "org.scalatest" % "scalatest" % "1.2"  % "test" withSources()
      lazy val mockito = "org.mockito" % "mockito-all" % "1.8.5" % "test" withSources()
      lazy val logbackTest = "ch.qos.logback" % "logback-classic" % Dependencies.LogbackVersion % "test" // withSources()
      lazy val springTest = "org.springframework" % "spring-test" % Dependencies.SpringFrameworkVersion % "test" withSources()
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

  class Java(info: ProjectInfo) extends DefaultProject(info) with IdeaProject {
    override def ivyXML = Dependencies.ivyXML

    // commons
    lazy val slf4jApi = "org.slf4j" % "slf4j-api" % "1.6.1" withSources()
    lazy val jclOverSlf4j = "org.slf4j" % "jcl-over-slf4j" % "1.6.1" withSources()

    lazy val guava = "com.google.guava" % "guava" % "r08" withSources()
    lazy val jodaTime = "joda-time" % "joda-time" % "1.6.2" withSources()
    
    // optional dependencies
    lazy val hibernateCore = "org.hibernate" % "hibernate-core" % Dependencies.HibernateVersion % "provided" withSources()
    lazy val hibernateEntityManager = "org.hibernate" % "hibernate-entitymanager" % Dependencies.HibernateVersion % "provided" withSources()
    lazy val springSecurity = "org.springframework.security" % "spring-security-core" % Dependencies.SpringFrameworkVersion % "provided" withSources()
    lazy val jsr275 = "net.java.dev.jsr-275" % "jsr-275" % Dependencies.Jsr275Version % "provided" withSources()
    lazy val servletApi = "javax.servlet" % "servlet-api"  % Dependencies.ServletApiVersion % "provided" withSources()
    lazy val postgisJdbc = "org.postgis" % "postgis-jdbc" % Dependencies.PostgisJdbcVersion % "provided" withSources()
    lazy val jts = "com.vividsolutions" % "jts" % "1.8" % "provided" // withSources()
    lazy val radeox = "radeox" % "radeox" % Dependencies.RadeoxVersion % "provided" // withSources()
    lazy val struts2Core = "org.apache.struts" % "struts2-core" % Dependencies.Struts2Version % "provided"
    lazy val rome = "rome" % "rome" % "0.9" % "provided"

    // FIXME: this should be "provided", but intellij does not seem to like it...
    lazy val providedHamcrest = "org.hamcrest" % "hamcrest-all" % Dependencies.HamcrestVersion  withSources()
    lazy val providedJunit = "junit" % "junit" % Dependencies.JunitVersion withSources()

    // test dependencies
    //lazy val hamcrest = Dependencies.Test.hamcrest
    lazy val junit = Dependencies.Test.junit
    //lazy val junitInterface = Dependencies.Test.junitInterface
    lazy val scalaTest = Dependencies.Test.scalaTest
    lazy val mockito = Dependencies.Test.mockito
    //lazy val logback = Dependencies.Test.logbackTest
    lazy val springTest = Dependencies.Test.springTest

    override def testOptions = super.testOptions ++ Seq(TestArgument(TestFrameworks.JUnit, "-q", "-v"))
  }

  class Scala(info: ProjectInfo) extends DefaultProject(info) with IdeaProject {
    override def ivyXML = Dependencies.ivyXML
    override def testOptions = TestFilter(s => true) :: super.testOptions.toList

    lazy val scalaTime = "org.scala-tools.time" %% "time" % "0.3" // withSources()
    lazy val scalaj_collection = "org.scalaj" % "scalaj-collection_2.8.0" % "1.0"

    // test dependencies
    //lazy val hamcrest = Dependencies.Test.hamcrest
    lazy val junit = Dependencies.Test.junit
    //lazy val junitInterface = Dependencies.Test.junitInterface
    lazy val scalaTest = Dependencies.Test.scalaTest
    lazy val mockito = Dependencies.Test.mockito
    //lazy val logback = Dependencies.Test.logbackTest
    lazy val springTest = Dependencies.Test.springTest
  }

}
