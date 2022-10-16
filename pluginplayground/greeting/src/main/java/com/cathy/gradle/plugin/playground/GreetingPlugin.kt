package com.cathy.gradle.plugin.playground

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author zourb @ Zebra Inc.
 * @since 10-16-2022
 */
class GreetingPlugin : Plugin<Project> {
    override fun apply(proj: Project) {
        val extension = proj.extensions.create("greeting", GreetingExtension::class.java)
        val task = proj.tasks.register("greeting", GreetingTask::class.java)

        proj.afterEvaluate {
            task.configure { target ->
                target.who.set(extension.who)
            }

            // assemble task
            val assembleTaskName = "assemble"
            val assembleTask = proj.tasks.findByName(assembleTaskName) ?: throw kotlin.IllegalStateException("Can not find $assembleTaskName")
            assembleTask.dependsOn(task)
        }
    }
}