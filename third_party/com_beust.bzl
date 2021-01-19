load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "com_beust_jcommander",
      artifact = "com.beust:jcommander:1.72",
      artifact_sha256 = "e0de160b129b2414087e01fe845609cd55caec6820cfd4d0c90fabcc7bdb8c1e",
  )
