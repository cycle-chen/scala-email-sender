name := "scala-email-sender"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq (
  "com.typesafe.akka" % "akka-actor_2.11" % "2.4-M2",
  "org.slf4j" % "slf4j-api" % "1.7.12",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "javax.mail" % "javax.mail-api" % "1.5.4",
  "com.sun.mail" % "javax.mail" % "1.5.4",
  "org.springframework" % "spring-context-support" % "4.2.0.RELEASE"
)