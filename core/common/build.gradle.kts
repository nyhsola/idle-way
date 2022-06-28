sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

dependencies {
    implementation(libs.kstdlib)
    implementation(libs.kapp)
    implementation(libs.kmath)
    implementation(libs.gashley)
    implementation(libs.gfreetype)
    implementation(libs.koin)
    implementation(libs.koina)
    ksp(libs.koinc)
    testImplementation(kotlin("test"))
}