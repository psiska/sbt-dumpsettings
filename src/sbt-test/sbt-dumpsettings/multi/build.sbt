lazy val check = taskKey[Unit]("check this plugin")

lazy val commonSettings = Seq(
  version := "0.1",
  organization := "com.example",
  scalaVersion := "2.11.8"
)

lazy val root = (project in file(".")).
  aggregate(app).
  settings(commonSettings: _*)

lazy val app = (project in file("app")).
  enablePlugins(DumpSettingsPlugin).
  settings(commonSettings: _*).
  settings(
    name := "scripted-example-app",
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
        case """name="scripted-example-app"""" ::
             """version="0.1"""" ::
             """scalaVersion="2.11.8"""" ::
             """sbtVersion="0.13.15"""" ::
             """declare -a libraryDependencies=("org.scala-lang:scala-library:2.11.8")""" ::
             buildAtString :: buildAtMillis :: Nil =>
        case _ => sys.error("unexpected output: \n" + lines.mkString("\n"))
      }
      ()
    }
  )


