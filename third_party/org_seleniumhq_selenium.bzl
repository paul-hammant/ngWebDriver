load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "org_seleniumhq_selenium_selenium_api",
      artifact = "org.seleniumhq.selenium:selenium-api:4.0.0-alpha-3",
      artifact_sha256 = "b4e1eab11568ea13396eea69e16358d42d8879de60b43b65cfe24997e13f99e7",
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_chrome_driver",
      artifact = "org.seleniumhq.selenium:selenium-chrome-driver:4.0.0-alpha-3",
      artifact_sha256 = "46ff1810806a56da778436da96c9ba9178a5abaf2a922a1dce2f4bf5f89c7059",
      deps = [
          "@com_google_guava_guava",
          "@org_seleniumhq_selenium_selenium_api",
          "@org_seleniumhq_selenium_selenium_chromium_driver",
          "@org_seleniumhq_selenium_selenium_devtools",
          "@org_seleniumhq_selenium_selenium_http",
          "@org_seleniumhq_selenium_selenium_json",
          "@org_seleniumhq_selenium_selenium_remote_driver"
      ],
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_chromium_driver",
      artifact = "org.seleniumhq.selenium:selenium-chromium-driver:4.0.0-alpha-3",
      artifact_sha256 = "165e125d340e909b818f9b3218ce06738aebb7d852a0569d78a3fa94aa9a436e",
      deps = [
          "@com_google_guava_guava",
          "@org_seleniumhq_selenium_selenium_devtools",
          "@org_seleniumhq_selenium_selenium_json",
          "@org_seleniumhq_selenium_selenium_remote_driver"
      ],
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_devtools",
      artifact = "org.seleniumhq.selenium:selenium-devtools:4.0.0-alpha-3",
      artifact_sha256 = "2833259ad2ceedff329324c57c65557fa4eb8a02b65d8285714dd1cadefcb17d",
      deps = [
          "@com_google_guava_guava",
          "@org_seleniumhq_selenium_selenium_api",
          "@org_seleniumhq_selenium_selenium_json",
          "@org_seleniumhq_selenium_selenium_remote_driver"
      ],
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_edge_driver",
      artifact = "org.seleniumhq.selenium:selenium-edge-driver:4.0.0-alpha-3",
      artifact_sha256 = "482c5ae43ada4c356d53a7ba1547b0ef2b0dc177266b163a7840d80a7a91bcc0",
      deps = [
          "@com_google_guava_guava",
          "@org_seleniumhq_selenium_selenium_api",
          "@org_seleniumhq_selenium_selenium_chromium_driver",
          "@org_seleniumhq_selenium_selenium_remote_driver"
      ],
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_edgehtml_driver",
      artifact = "org.seleniumhq.selenium:selenium-edgehtml-driver:4.0.0-alpha-3",
      artifact_sha256 = "abeec083d22b46e112c5dbf1a14f2069735a783fc9c515198f641b163853b9ed",
      deps = [
          "@com_google_guava_guava",
          "@org_seleniumhq_selenium_selenium_edge_driver",
          "@org_seleniumhq_selenium_selenium_remote_driver"
      ],
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_firefox_driver",
      artifact = "org.seleniumhq.selenium:selenium-firefox-driver:4.0.0-alpha-3",
      artifact_sha256 = "d1ba6336e010ab24da7da73bc247cfbd91891dd936cd625983c921f9bbd909d9",
      deps = [
          "@com_google_guava_guava",
          "@org_seleniumhq_selenium_selenium_json",
          "@org_seleniumhq_selenium_selenium_remote_driver"
      ],
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_firefox_xpi_driver",
      artifact = "org.seleniumhq.selenium:selenium-firefox-xpi-driver:4.0.0-alpha-3",
      artifact_sha256 = "8c12b4562e94433f4d9c4582a217602b46bce2dd69736413afebaf078b11905b",
      deps = [
          "@com_google_guava_guava",
          "@org_seleniumhq_selenium_selenium_api",
          "@org_seleniumhq_selenium_selenium_firefox_driver",
          "@org_seleniumhq_selenium_selenium_remote_driver"
      ],
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_http",
      artifact = "org.seleniumhq.selenium:selenium-http:4.0.0-alpha-3",
      artifact_sha256 = "40f74e0547c64cde1f05f1b851db5b4adc1468df828285e65920fc5aebac5d4c",
      deps = [
          "@com_google_guava_guava",
          "@org_seleniumhq_selenium_selenium_api",
          "@org_seleniumhq_selenium_selenium_json"
      ],
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_ie_driver",
      artifact = "org.seleniumhq.selenium:selenium-ie-driver:4.0.0-alpha-3",
      artifact_sha256 = "bb8f7d0429b754c7c44958d149558773f11ed82f67d736c4dcbf208521b68133",
      deps = [
          "@com_google_guava_guava",
          "@org_seleniumhq_selenium_selenium_api",
          "@org_seleniumhq_selenium_selenium_remote_driver"
      ],
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_java",
      artifact = "org.seleniumhq.selenium:selenium-java:4.0.0-alpha-3",
      artifact_sha256 = "b6e62f156ecefe6931468a34bc72d17c2854047833a21bce1a7fea35b1777194",
      deps = [
          "@org_seleniumhq_selenium_selenium_api",
          "@org_seleniumhq_selenium_selenium_chrome_driver",
          "@org_seleniumhq_selenium_selenium_edge_driver",
          "@org_seleniumhq_selenium_selenium_edgehtml_driver",
          "@org_seleniumhq_selenium_selenium_firefox_driver",
          "@org_seleniumhq_selenium_selenium_firefox_xpi_driver",
          "@org_seleniumhq_selenium_selenium_ie_driver",
          "@org_seleniumhq_selenium_selenium_opera_driver",
          "@org_seleniumhq_selenium_selenium_remote_driver",
          "@org_seleniumhq_selenium_selenium_safari_driver",
          "@org_seleniumhq_selenium_selenium_support"
      ],
    # EXCLUDES org.eclipse.jetty.websocket:websocket-client
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_json",
      artifact = "org.seleniumhq.selenium:selenium-json:4.0.0-alpha-3",
      artifact_sha256 = "8f2eda62e2ef5221c428c7f25351d3e35e092c875287e2918bf73363d658a8bb",
      deps = [
          "@org_seleniumhq_selenium_selenium_api"
      ],
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_opera_driver",
      artifact = "org.seleniumhq.selenium:selenium-opera-driver:4.0.0-alpha-3",
      artifact_sha256 = "d252d77bd5c71b88b13b5cb8d6d26274af273249fb59039804fe908807e24440",
      deps = [
          "@com_google_guava_guava",
          "@org_seleniumhq_selenium_selenium_api",
          "@org_seleniumhq_selenium_selenium_remote_driver"
      ],
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_remote_driver",
      artifact = "org.seleniumhq.selenium:selenium-remote-driver:4.0.0-alpha-3",
      artifact_sha256 = "b71488dcd3b2d826a96e9fedb40e995b039e7d2a95d5b3980341a45fc3d409c6",
      deps = [
          "@com_google_guava_guava",
          "@com_squareup_okhttp3_okhttp",
          "@io_opentracing_opentracing_api",
          "@net_bytebuddy_byte_buddy",
          "@org_apache_commons_commons_exec",
          "@org_seleniumhq_selenium_selenium_api",
          "@org_seleniumhq_selenium_selenium_http",
          "@org_seleniumhq_selenium_selenium_json"
      ],
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_safari_driver",
      artifact = "org.seleniumhq.selenium:selenium-safari-driver:4.0.0-alpha-3",
      artifact_sha256 = "0314b381f5b9bbd7de8d8fde422c0d2ea78fa00786be693fe8f05d46050344da",
      deps = [
          "@com_google_guava_guava",
          "@org_seleniumhq_selenium_selenium_api",
          "@org_seleniumhq_selenium_selenium_remote_driver"
      ],
  )


  import_external(
      name = "org_seleniumhq_selenium_selenium_support",
      artifact = "org.seleniumhq.selenium:selenium-support:4.0.0-alpha-3",
      artifact_sha256 = "5964d4d27eca811df6c3fb062dbc3d0e8fb23dbfec784d938b7a9a3909d4435b",
      deps = [
          "@com_google_guava_guava",
          "@org_seleniumhq_selenium_selenium_api",
          "@org_seleniumhq_selenium_selenium_chrome_driver",
          "@org_seleniumhq_selenium_selenium_devtools",
          "@org_seleniumhq_selenium_selenium_json",
          "@org_seleniumhq_selenium_selenium_remote_driver"
      ],
  )
