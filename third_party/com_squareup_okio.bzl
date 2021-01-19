load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "com_squareup_okio_okio",
      artifact = "com.squareup.okio:okio:2.2.2",
      artifact_sha256 = "e58c97406a6bb1138893750299ac63c6aa04b38b6b49eae1bfcad1a63ef9ba1b",
      runtime_deps = [
          "@org_jetbrains_kotlin_kotlin_stdlib"
      ],
  )
