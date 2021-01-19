load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "org_checkerframework_checker_qual",
      artifact = "org.checkerframework:checker-qual:2.5.2",
      artifact_sha256 = "64b02691c8b9d4e7700f8ee2e742dce7ea2c6e81e662b7522c9ee3bf568c040a",
  )
