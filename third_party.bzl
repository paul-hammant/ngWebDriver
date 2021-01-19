load("//:third_party/org_testng.bzl", org_testng_deps = "dependencies")

load("//:third_party/org_seleniumhq_selenium.bzl", org_seleniumhq_selenium_deps = "dependencies")

load("//:third_party/org_seleniumhq_selenium_fluent.bzl", org_seleniumhq_selenium_fluent_deps = "dependencies")

load("//:third_party/org_jetbrains_kotlin.bzl", org_jetbrains_kotlin_deps = "dependencies")

load("//:third_party/org_jetbrains.bzl", org_jetbrains_deps = "dependencies")

load("//:third_party/org_hamcrest.bzl", org_hamcrest_deps = "dependencies")

load("//:third_party/org_eclipse_jetty.bzl", org_eclipse_jetty_deps = "dependencies")

load("//:third_party/org_codehaus_mojo.bzl", org_codehaus_mojo_deps = "dependencies")

load("//:third_party/org_checkerframework.bzl", org_checkerframework_deps = "dependencies")

load("//:third_party/org_apache_extras_beanshell.bzl", org_apache_extras_beanshell_deps = "dependencies")

load("//:third_party/org_apache_commons.bzl", org_apache_commons_deps = "dependencies")

load("//:third_party/net_bytebuddy.bzl", net_bytebuddy_deps = "dependencies")

load("//:third_party/javax_servlet.bzl", javax_servlet_deps = "dependencies")

load("//:third_party/io_opentracing.bzl", io_opentracing_deps = "dependencies")

load("//:third_party/com_squareup_okio.bzl", com_squareup_okio_deps = "dependencies")

load("//:third_party/com_squareup_okhttp3.bzl", com_squareup_okhttp3_deps = "dependencies")

load("//:third_party/com_google_j2objc.bzl", com_google_j2objc_deps = "dependencies")

load("//:third_party/com_google_guava.bzl", com_google_guava_deps = "dependencies")

load("//:third_party/com_google_errorprone.bzl", com_google_errorprone_deps = "dependencies")

load("//:third_party/com_google_code_findbugs.bzl", com_google_code_findbugs_deps = "dependencies")

load("//:third_party/com_beust.bzl", com_beust_deps = "dependencies")


load("//:macros.bzl", "maven_archive", "maven_proto")

def third_party_dependencies():
      

  com_beust_deps()


  com_google_code_findbugs_deps()


  com_google_errorprone_deps()


  com_google_guava_deps()


  com_google_j2objc_deps()


  com_squareup_okhttp3_deps()


  com_squareup_okio_deps()


  io_opentracing_deps()


  javax_servlet_deps()


  net_bytebuddy_deps()


  org_apache_commons_deps()


  org_apache_extras_beanshell_deps()


  org_checkerframework_deps()


  org_codehaus_mojo_deps()


  org_eclipse_jetty_deps()


  org_hamcrest_deps()


  org_jetbrains_deps()


  org_jetbrains_kotlin_deps()


  org_seleniumhq_selenium_fluent_deps()


  org_seleniumhq_selenium_deps()


  org_testng_deps()
