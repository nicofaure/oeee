

name := "api"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "redis.clients" % "jedis" % "2.6.2",
  "org.slf4j" % "log4j-over-slf4j" % "1.7.6",
  "org.springframework" % "spring-context" % "3.2.8.RELEASE",
  "commons-io" % "commons-io" % "2.4",
  "org.projectlombok" % "lombok" % "1.12.6",
  "org.hamcrest" % "hamcrest-all" % "1.3",
  "org.mockito" % "mockito-core" % "1.10.19" % "test"
)     

play.Project.playJavaSettings
