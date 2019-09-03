name := """sharpener"""

version := "1.1.2"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies += "org.webjars" % "swagger-ui" % "3.1.5"
libraryDependencies += "javax.validation" % "validation-api" % "1.1.0.Final"
libraryDependencies += guice
libraryDependencies += filters
