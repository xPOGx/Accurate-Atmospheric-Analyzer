plugins {
    id("mega.triple.aaa.convention.application")
    id("mega.triple.aaa.convention.di")
}

android {
    namespace = libs.versions.applicationId.get()
}

dependencies {
    // Modules
    implementation(project(libs.versions.projectCoreCommon.get()))
    implementation(project(libs.versions.projectCoreStrings.get()))
    implementation(project(libs.versions.projectCoreUi.get()))
    implementation(project(libs.versions.projectDataNetwork.get()))
    implementation(project(libs.versions.projectDataProto.get()))
    implementation(project(libs.versions.projectDataPreference.get()))
    implementation(project(libs.versions.projectDataLocal.get()))
    implementation(project(libs.versions.projectDomain.get()))
    implementation(project(libs.versions.projectFeatureMain.get()))
    implementation(project(libs.versions.projectFeatureSync.get()))
    implementation(project(libs.versions.projectFeatureHome.get()))
    implementation(project(libs.versions.projectFeatureSearch.get()))
    implementation(project(libs.versions.projectFeatureSettings.get()))
    implementation(project(libs.versions.projectFeatureWidget.get()))
}
