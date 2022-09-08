name := "PFPScala"

version := "0.1"

ThisBuild / scalaVersion := "2.13.8"

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
      "co.fs2"        %% "fs2-core"              % "3.0.3",
      "dev.optics"    %% "monocle-core"          % "3.0.0",
      "dev.optics"    %% "monocle-macro"         % "3.0.0",
      "io.estatico"   %% "newtype"               % "0.4.4",
      "eu.timepit"    %% "refined"               % "0.9.25",
      "eu.timepit"    %% "refined-cats"          % "0.9.25",
      "tf.tofu"       %% "derevo-cats"           % "0.12.5",
      "tf.tofu"       %% "derevo-cats-tagless"   % "0.12.5",
      "tf.tofu"       %% "derevo-circe-magnolia" % "0.12.5",
      "tf.tofu"       %% "tofu-core-higher-kind" % "0.10.2"
    ),
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-Wconf:cat=unused:info"
    )
  )
