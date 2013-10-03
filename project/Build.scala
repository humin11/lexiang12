import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "lexiang12"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
	"securesocial" %% "securesocial" % "master-SNAPSHOT",
	"org.reactivemongo" %% "play2-reactivemongo" % "0.10-SNAPSHOT",
	"com.feth" %% "play-easymail" % "0.3-SNAPSHOT"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
	resolvers += Resolver.url("sbt-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns),
	resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
	resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
	resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns)
  )

}
