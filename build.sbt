name := "PFPScala"

version := "0.1"

ThisBuild / scalaVersion := "2.13.8"

val refinedV = "0.9.25"
val derevoV = "0.12.5"
val monocleCoreV = "3.0.0"

lazy val root = (project in file("."))
  .settings(
    name := "minimal",
    libraryDependencies ++= Seq(
      compilerPlugin(
        "org.typelevel" %% "kind-projector" % "0.13.2"
          cross CrossVersion.full
      ),
      "org.typelevel" %% "cats-core"             % "2.7.0",
      "org.typelevel" %% "cats-effect"           % "3.3.12",
      "org.typelevel" %% "cats-mtl"              % "1.2.0",
      "org.typelevel" %% "squants"               % "1.6.0",
      "co.fs2"        %% "fs2-core"              % "3.0.3",
      "dev.optics"    %% "monocle-core"          % monocleCoreV,
      "dev.optics"    %% "monocle-macro"         % monocleCoreV,
      "io.estatico"   %% "newtype"               % "0.4.4",
      "eu.timepit"    %% "refined"               % refinedV,
      "eu.timepit"    %% "refined-cats"          % refinedV,
      "tf.tofu"       %% "derevo-cats"           % derevoV,
      "tf.tofu"       %% "derevo-cats-tagless"   % derevoV,
      "tf.tofu"       %% "derevo-circe-magnolia" % derevoV,
      "tf.tofu"       %% "tofu-core-higher-kind" % "0.10.2"
    ),
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-Wconf:cat=unused:info"
    )
  )
