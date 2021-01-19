load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "com_squareup_okhttp3_okhttp",
      artifact = "com.squareup.okhttp3:okhttp:4.0.1",
      artifact_sha256 = "0e0392ea5c0d303bca20e13b2340086d7a347b22ad625f967989ee8723b6ac3c",
      deps = [
          "@com_squareup_okio_okio",
          "@org_jetbrains_kotlin_kotlin_stdlib"
      ],
  )
