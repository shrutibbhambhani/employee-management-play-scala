name := """employee-management"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.13"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test
//libraryDependencies ++= Seq(
//  "org.scalikejdbc" %% "scalikejdbc"       % "3.5.0",
//  "com.h2database"  %  "h2"                % "1.4.200",
//  "ch.qos.logback"  %  "logback-classic"   % "1.2.12"
//)
libraryDependencies ++= Seq(
  "com.h2database"  %  "h2"                           % "1.4.200", // your jdbc driver here
  "org.scalikejdbc" %% "scalikejdbc"                  % "3.4.0",
  "org.scalikejdbc" %% "scalikejdbc-config"           % "3.4.0",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.7.0-scalikejdbc-3.4"
)
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "employee-management.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "employee-management.binders._"
