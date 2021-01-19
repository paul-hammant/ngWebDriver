load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "io_opentracing_opentracing_api",
      artifact = "io.opentracing:opentracing-api:0.33.0",
      artifact_sha256 = "4534541b8e9f41a17bcdf1d09affe45b98c13574db6e529a93a58264b9472c7c",
  )
