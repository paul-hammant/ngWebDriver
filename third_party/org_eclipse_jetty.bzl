load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "org_eclipse_jetty_jetty_http",
      artifact = "org.eclipse.jetty:jetty-http:9.4.24.v20191120",
      artifact_sha256 = "0a0f7e1d1195ee584337dea1c1b262a1d1f9064cc328823dfddbb4ffb2b0fc42",
      deps = [
          "@org_eclipse_jetty_jetty_io",
          "@org_eclipse_jetty_jetty_util"
      ],
  )


  import_external(
      name = "org_eclipse_jetty_jetty_io",
      artifact = "org.eclipse.jetty:jetty-io:9.4.24.v20191120",
      artifact_sha256 = "8bdb052b607152ea1485425dd70e432b12f6170ee5c3f23f1c0d0f3d2b902e32",
      deps = [
          "@org_eclipse_jetty_jetty_util"
      ],
  )


  import_external(
      name = "org_eclipse_jetty_jetty_server",
      artifact = "org.eclipse.jetty:jetty-server:9.4.24.v20191120",
      artifact_sha256 = "704631682465f2464e56fa9487c1df70944ce6f37c9585400359843bc26fb538",
      deps = [
          "@javax_servlet_javax_servlet_api//:linkable",
          "@org_eclipse_jetty_jetty_http",
          "@org_eclipse_jetty_jetty_io"
      ],
  )


  import_external(
      name = "org_eclipse_jetty_jetty_util",
      artifact = "org.eclipse.jetty:jetty-util:9.4.24.v20191120",
      artifact_sha256 = "e202b02e0be3e164dfd834c30bf4da731f249f1f554250de9a59579509901121",
  )
