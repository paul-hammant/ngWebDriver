load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "org_hamcrest_hamcrest_all",
      artifact = "org.hamcrest:hamcrest-all:1.3",
      artifact_sha256 = "4877670629ab96f34f5f90ab283125fcd9acb7e683e66319a68be6eb2cca60de",
  )
