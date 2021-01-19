load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "net_bytebuddy_byte_buddy",
      artifact = "net.bytebuddy:byte-buddy:1.9.12",
      artifact_sha256 = "3688c3d434bebc3edc5516296a2ed0f47b65e451071b4afecad84f902f0efc11",
  )
