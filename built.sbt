name := "finals"

version := "1.0"

scalaVersion := "2.12.11"

fork := true

// https://mvnrepository.com/artifact/org.scalafx/scalafx
libraryDependencies += "org.scalafx" % "scalafx_2.12" % "8.0.144-R12"
libraryDependencies += "org.scalafx" % "scalafxml-core-sfx8_2.12" % "0.4"
libraryDependencies += "org.scalikejdbc" %% "scalikejdbc" % "3.1.0"
libraryDependencies += "com.h2database" % "h2" % "1.4.196"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "org.apache.derby" % "derby" % "10.13.1.1"
// libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1103-jdbc3"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
