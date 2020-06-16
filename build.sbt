name := "scalaGraphQLPagination"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "com.github.ghostdogpr"        %% "caliban" % "0.7.7",
  "com.github.ghostdogpr"        %% "caliban-http4s" % "0.7.7",
  "com.github.ghostdogpr"        %% "caliban-cats" % "0.7.7",
  "com.github.ghostdogpr"        %% "caliban-monix" % "0.7.7",
  "io.circe"                     %% "circe-generic" % "0.13.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
)
enablePlugins(CodegenPlugin)
//scalacOptions += "-Ypartial-unification"