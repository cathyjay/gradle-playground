package com.cathy.gradle.plugin.buildversion.gradleextensions

import com.cathy.gradle.plugin.buildversion.utils.GitWrapper
import com.cathy.gradle.plugin.buildversion.utils.closureOf
import groovy.lang.Closure
import org.gradle.api.Project

open class AdvancedBuildVersionConfig(private val project: Project) {


    internal var versionCodeConfig = VersionCodeConfig(project, GitWrapper(project))

    val versionCode by lazy {
        versionCodeConfig.versionCode
    }

    fun codeOptions(closure: Closure<*>) {
        project.configure(versionCodeConfig, closure)
    }

    fun codeOptions(config: VersionCodeConfig.() -> Unit) {
        project.configure(versionCodeConfig, closureOf(config))
    }


    internal fun increaseVersionCodeIfPossible() {
        versionCodeConfig.increaseVersionCodeIfPossible()
    }
}
