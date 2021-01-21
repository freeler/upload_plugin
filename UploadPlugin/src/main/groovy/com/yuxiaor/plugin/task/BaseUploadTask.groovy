package com.yuxiaor.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 *
 */
abstract class BaseUploadTask extends DefaultTask {

    BaseUploadTask() {
        setupDependenciesIfNeeded()
    }

    /**
     * setup dependencies,run on configuration phase
     */
    abstract void setupDependenciesIfNeeded()

    /**
     * upload
     */
    @TaskAction
    abstract void upload()


    String getExceptionsDir() {

        return project.buildDir.absolutePath + "${File.separator}QUploadPlugin${File.separator}exceptions"
    }

    String getArchivesDir() {

        return project.buildDir.absolutePath + "${File.separator}QUploadPlugin${File.separator}archive"
    }


}