load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "org_apache_commons_commons_exec",
      artifact = "org.apache.commons:commons-exec:1.3",
      artifact_sha256 = "cb49812dc1bfb0ea4f20f398bcae1a88c6406e213e67f7524fb10d4f8ad9347b",
  )
