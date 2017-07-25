lazy val commonSettings: Seq[Setting[_]] = Seq(
  git.baseVersion in ThisBuild := "0.1.0",
  organization in ThisBuild := "eu.psiska"
)

lazy val root = (project in file(".")).
  enablePlugins(GitVersioning).
  settings(
    commonSettings,
    sbtPlugin := true,
    name := "sbt-dumpsettings",
    scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-language:implicitConversions"),
    description := "sbt plugin to generate json/ini/shell files with settings",
    licenses := Seq("MIT License" -> url("https://github.com/psiska/sbt-dumpsettings/blob/master/LICENSE")),
    // bintray settings
    publishMavenStyle := false,
    bintrayRepository := "sbt-plugins",
    bintrayOrganization in bintray := None,
    bintrayPackageLabels := Seq("sbt", "keen"),
    bintrayVcsUrl := Some("git@github.com:psiska/sbt-dumpsettings.git")

  )
