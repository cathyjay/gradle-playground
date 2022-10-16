package com.cathy.gradle.plugin.buildversion.gradleextensions

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import groovy.lang.Closure
import com.cathy.gradle.plugin.buildversion.utils.GitWrapper
import com.cathy.gradle.plugin.buildversion.utils.closureOf
import org.gradle.api.DomainObjectSet
import org.gradle.api.Project

open class AdvancedBuildVersionConfig(private val project: Project) {

    internal var versionNameConfig = VersionNameConfig()

    internal var versionCodeConfig = VersionCodeConfig(project, GitWrapper(project))

    internal var outputConfig = FileOutputConfig(project)

    val versionName by lazy {
        versionNameConfig.versionName
    }

    val versionCode by lazy {
        versionCodeConfig.versionCode
    }

    fun nameOptions(closure: Closure<*>) {
        project.configure(versionNameConfig, closure)
    }

    fun nameOptions(config: VersionNameConfig.() -> Unit) {
        project.configure(versionNameConfig, closureOf(config))
    }

    fun codeOptions(closure: Closure<*>) {
        project.configure(versionCodeConfig, closure)
    }

    fun codeOptions(config: VersionCodeConfig.() -> Unit) {
        project.configure(versionCodeConfig, closureOf(config))
    }

    fun outputOptions(closure: Closure<*>) {
        project.configure(outputConfig, closure)
    }

    fun outputOptions(config: FileOutputConfig.() -> Unit) {
        project.configure(outputConfig, closureOf(config))
    }

    internal fun increaseVersionCodeIfPossible() {
        versionCodeConfig.increaseVersionCodeIfPossible()
    }

    internal fun renameOutputApkIfPossible(variants: DomainObjectSet<ApplicationVariant>) {
        outputConfig.renameOutputApkIfPossible(variants)
    }

    fun renameOutputApk() {
        project.extensions.findByType(AppExtension::class.java)?.run {
            outputConfig.renameOutputApkIfPossible(applicationVariants)
        }
    }
}
