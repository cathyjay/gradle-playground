package com.cathy.gradle.plugin.playground

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author zourb @ Zebra Inc.
 * @since 10-16-2022
 */
class GreetingPlugin : Plugin<Project> {
    override fun apply(proj: Project) {
        val extension = proj.extensions.create(EXTENSION_NAME, GreetingExtension::class.java)
        val task = proj.tasks.register("greeting", GreetingTask::class.java)

        proj.afterEvaluate {
            task.configure { target ->
                target.who.set(extension.who)
            }

            // assemble task
            val assembleTaskName = "assembleDebug"
            val assembleTask = proj.tasks.findByName(assembleTaskName) ?: throw kotlin.IllegalStateException("Can not find $assembleTaskName")
            assembleTask.dependsOn(task)
        }
    }

    companion object {
        const val EXTENSION_NAME = "greeting"
    }
}