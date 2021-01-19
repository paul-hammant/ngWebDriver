load("//:import_external.bzl", import_external = "safe_exodus_maven_import_external")

def dependencies():

  import_external(
      name = "org_seleniumhq_selenium_fluent_fluent_selenium",
      artifact = "org.seleniumhq.selenium.fluent:fluent-selenium:1.21",
      artifact_sha256 = "9c5e9109fccfcba94421787a88fb9d437d28b17031135b6471dbd7767be74ca8",
      srcjar_sha256 = "5b358f79c161ce35c43a61826512c22608d1e6ec809f4b4d08396d3c59ea7df8",
    # EXCLUDES org.seleniumhq.selenium:selenium-java
  )
