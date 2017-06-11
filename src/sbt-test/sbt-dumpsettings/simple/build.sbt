lazy val check = taskKey[Unit]("checks this plugin")
  
lazy val root = (project in file(".")).
  enablePlugins(DumpSettingsPlugin).
  settings(
    name := "hellohello",
    version := "0.1",
    scalaVersion := "2.11.8",
    homepage := Some(url("http://example.com")),
    licenses := Seq("MIT License" -> url("https://github.com/sbt/sbt-buildinfo/blob/master/LICENSE")),
    scalacOptions ++= Seq("-Ywarn-unused-import", "-Xfatal-warnings", "-Yno-imports"),
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.5",
    dumpSettingsKeys := Seq[DumpSettingsKey](
      name,
      version,
      scalaVersion,
      sbtVersion,
      libraryDependencies
    ),
    check := {
      val f = baseDirectory.value / "dumpsettings.sh"
      val lines = scala.io.Source.fromFile(f).getLines.toList
      lines match {
        case """name="hellohello"""" ::
             """version="0.1"""" ::
             """scalaVersion="2.11.8"""" ::
             """sbtVersion="0.13.15"""" ::
             """declare -a libraryDependencies=("org.scala-lang:scala-library:2.11.8" "org.scala-lang.modules:scala-xml:1.0.5")""" ::
             buildAtString :: buildAtMillis :: Nil =>
        case _ => sys.error("unexpected output: \n" + lines.mkString("\n"))
      }
      ()
    }
  )


