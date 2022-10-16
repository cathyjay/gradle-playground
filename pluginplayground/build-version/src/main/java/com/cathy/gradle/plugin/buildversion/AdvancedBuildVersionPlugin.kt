package com.cathy.gradle.plugin.buildversion

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.FeaturePlugin
import com.android.build.gradle.LibraryPlugin
import com.cathy.gradle.plugin.buildversion.gradleextensions.AdvancedBuildVersionConfig
import com.cathy.gradle.plugin.buildversion.utils.checkAndroidGradleVersion
import com.cathy.gradle.plugin.buildversion.utils.checkJavaRuntimeVersion
import com.cathy.gradle.plugin.buildversion.utils.checkMinimumGradleVersion
import com.cathy.gradle.plugin.buildversion.utils.getAndroidPlugin
import org.eclipse.jgit.errors.NotSupportedException
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @since 10-16-2022
 */
class AdvancedBuildVersionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        checkJavaRuntimeVersion()
        checkMinimumGradleVersion()
        checkAndroidGradleVersion(project)

        println("Applying Advanced Build Version Plugin")

        val advancedBuildVersionPlugin = project.extensions.create(
            EXTENSION_NAME, AdvancedBuildVersionConfig::class.java, project
        )

        project.afterEvaluate {
            project.plugins.all { plugin ->
                when (plugin) {
                    is AppPlugin -> configureAndroid(project, advancedBuildVersionPlugin)
                    is FeaturePlugin -> throw NotSupportedException("Feature module is not supported")
                    is LibraryPlugin -> throw NotSupportedException("Library module is not supported yet")
                }
            }
        }
    }

    private fun configureAndroid(project: Project, config: AdvancedBuildVersionConfig) {
        config.increaseVersionCodeIfPossible()

        if (getAndroidPlugin(project)?.version?.compareTo("4.1.0") == -1) { // versions prior to 4.1.0
            val appExtension = project.extensions.getByType(AppExtension::class.java)
            config.renameOutputApkIfPossible(appExtension.applicationVariants)
        }
    }

    companion object {
        const val EXTENSION_NAME = "buildVersioning"
    }
}