load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "org_jetbrains_kotlin_kotlin_stdlib",
      artifact = "org.jetbrains.kotlin:kotlin-stdlib:1.3.40",
      artifact_sha256 = "f76f9812a703ba5085af8f51769e60e8ecd5e99b55b2ced097cf2343e972ad7b",
      deps = [
          "@org_jetbrains_annotations",
          "@org_jetbrains_kotlin_kotlin_stdlib_common"
      ],
  )


  import_external(
      name = "org_jetbrains_kotlin_kotlin_stdlib_common",
      artifact = "org.jetbrains.kotlin:kotlin-stdlib-common:1.3.40",
      artifact_sha256 = "5f551a3ffe7683f4741d96fdc49835864aa08ddfc63d38109884973e19eed1cb",
  )
