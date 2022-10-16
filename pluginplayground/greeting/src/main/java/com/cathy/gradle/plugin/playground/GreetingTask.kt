package com.cathy.gradle.plugin.playground

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

/**
 * @author zourb @ Zebra Inc.
 * @since 10-16-2022
 */
abstract class GreetingTask : DefaultTask() {
    @get:Optional
    @get:Input
    abstract val who: Property<String>

    @TaskAction
    fun greet() {
        val w = who.getOrElse("there")
        println("\nHi $w~\n")
    }
}