load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "org_apache_extras_beanshell_bsh",
      artifact = "org.apache-extras.beanshell:bsh:2.0b6",
      artifact_sha256 = "a17955976070c0573235ee662f2794a78082758b61accffce8d3f8aedcd91047",
  )
