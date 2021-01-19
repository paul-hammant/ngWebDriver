load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "org_testng_testng",
      artifact = "org.testng:testng:6.14.3",
      artifact_sha256 = "7266b68b9668ea0c4e86e384fd72ccaa29a20a2538b20c586fd3902f008b114e",
      deps = [
          "@com_beust_jcommander",
          "@org_apache_extras_beanshell_bsh"
      ],
    # EXCLUDES junit:junit
  )
