name := """com.elovirta.kuhnuri.cms"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += jdbc
libraryDependencies += cache
libraryDependencies += ws
libraryDependencies += filters
libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"
libraryDependencies += "org.jooq" % "jooq" % "3.9.1"
libraryDependencies += "org.jooq" % "jooq-codegen-maven" % "3.9.1"
libraryDependencies += "org.jooq" % "jooq-meta" % "3.9.1"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test

val generateJOOQ = taskKey[Seq[File]]("Generate JooQ classes")
generateJOOQ := {
  toError((runner in Compile).value.run("org.jooq.util.GenerationTool", (fullClasspath in Compile).value.files, Array("conf/queue.xml"), streams.value.log))
  ((sourceManaged.value / "main/generated") ** "*.java").get
}

unmanagedSourceDirectories in Compile += sourceManaged.value / "main"
