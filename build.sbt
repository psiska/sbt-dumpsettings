lazy val commonSettings: Seq[Setting[_]] = Seq(
  organization in ThisBuild := "eu.psiska"
)

lazy val root = (project in file(".")).
  settings(
    commonSettings,
    sbtPlugin := true,
    name := "sbt-dumpsettings",
    // sbtVersion in Global := "0.13.0"
    // scalaVersion in Global := "2.10.2"
    scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-language:implicitConversions"),
    description := "sbt plugin to generate json/ini/shell files with settings",
    licenses := Seq("MIT License" -> url("https://github.com/sbt/sbt-buildinfo/blob/master/LICENSE"))
  )
  //settings(lsSettings: _*).
  //settings(
  //  LsKeys.tags in LsKeys.lsync := Seq("sbt", "codegen")
  //)
