resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

lazy val commonSettings = Seq(
// Refine scalac params from tpolecat
  scalacOptions --= Seq(
    "-Xfatal-warnings"
  )
)

lazy val zioDeps = libraryDependencies ++= Seq(
  "dev.zio" %% "zio"         % Version.zio,
  "dev.zio" %% "zio-streams" % Version.zio
)

lazy val root = (project in file("."))
  .settings(
    organization := "Neurodyne",
    name := "roots",
    version := "0.0.1",
    scalaVersion := "2.13.2",
    maxErrors := 3,
    commonSettings,
    zioDeps
  )

