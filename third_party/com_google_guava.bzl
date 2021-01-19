load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "com_google_guava_failureaccess",
      artifact = "com.google.guava:failureaccess:1.0.1",
      artifact_sha256 = "a171ee4c734dd2da837e4b16be9df4661afab72a41adaf31eb84dfdaf936ca26",
  )


  import_external(
      name = "com_google_guava_guava",
      artifact = "com.google.guava:guava:27.1-jre",
      artifact_sha256 = "4a5aa70cc968a4d137e599ad37553e5cfeed2265e8c193476d7119036c536fe7",
      deps = [
          "@com_google_code_findbugs_jsr305",
          "@com_google_errorprone_error_prone_annotations",
          "@com_google_guava_failureaccess",
          "@com_google_guava_listenablefuture",
          "@com_google_j2objc_j2objc_annotations",
          "@org_checkerframework_checker_qual",
          "@org_codehaus_mojo_animal_sniffer_annotations"
      ],
  )


  import_external(
      name = "com_google_guava_listenablefuture",
      artifact = "com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava",
      artifact_sha256 = "b372a037d4230aa57fbeffdef30fd6123f9c0c2db85d0aced00c91b974f33f99",
  )
