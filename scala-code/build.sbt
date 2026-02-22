ThisBuild / scalaVersion := "3.8.1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "it.unibo"
ThisBuild / organizationName := "unibo"

lazy val root = (project in file("."))
  .settings(
    name := "scala-code",
    libraryDependencies ++= Seq(
      "org.junit.jupiter" % "junit-jupiter-api"    % "5.11.0" % Test,
      "org.junit.jupiter" % "junit-jupiter-engine" % "5.11.0" % Test,
      "net.aichler"       % "jupiter-interface"    % "0.11.1" % Test
    )
  )
