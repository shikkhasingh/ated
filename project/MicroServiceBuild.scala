import sbt._

object MicroServiceBuild extends Build with MicroService {
  val appName = "ated"

  override lazy val appDependencies: Seq[ModuleID] = AppDependencies()
}

private object AppDependencies {

  import play.sbt.PlayImport._
  import play.core.PlayVersion

  private val microserviceBootstrapVersion = "8.3.0"
  private val domainVersion = "5.2.0"
  private val scalaTestVersion = "2.2.6"
  private val pegdownVersion = "1.6.0"
  private val scalaTestPlusVersion = "1.5.1"
  private val playReactivemongoVersion = "6.2.0"
  private val reactivemongoTestVersion = "3.1.0"
  private val jsonEncryptionVersion = "3.2.0"

  val compile = Seq(
    ws,
    "uk.gov.hmrc" %% "microservice-bootstrap" % microserviceBootstrapVersion,
    "uk.gov.hmrc" %% "domain" % domainVersion,
    "uk.gov.hmrc" %% "play-reactivemongo" % playReactivemongoVersion,
    "com.kenshoo" %% "metrics-play" % "2.3.0_0.1.8",
    "com.codahale.metrics" % "metrics-graphite" % "3.0.2",
    "uk.gov.hmrc" %% "json-encryption" % jsonEncryptionVersion
  )

  trait TestDependencies {
    lazy val scope: String = "test"
    lazy val test: Seq[ModuleID] = ???
  }

  object Test {
    def apply() = new TestDependencies {
      override lazy val test = Seq(
        "org.scalatest" %% "scalatest" % scalaTestVersion % scope,
        "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusVersion % scope,
        "org.pegdown" % "pegdown" % pegdownVersion % scope,
        "org.mockito" % "mockito-all" % "1.10.19" % scope,
        "com.typesafe.play" %% "play-test" % PlayVersion.current % scope,
        "uk.gov.hmrc" %% "reactivemongo-test" % reactivemongoTestVersion % scope
      )
    }.test
  }

  def apply() = compile ++ Test()
}

