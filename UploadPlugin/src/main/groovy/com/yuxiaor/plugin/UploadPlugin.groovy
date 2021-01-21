package com.yuxiaor.plugin


import com.yuxiaor.plugin.extension.QiNiuUploadExtension
import com.yuxiaor.plugin.extension.WrapperExtension
import com.yuxiaor.plugin.task.QiNiuUploadTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * - create extension
 * - create tasks
 */
class UploadPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        //create extension

        project.extensions.create("QUpload", WrapperExtension)
        project.QUpload.extensions.create("qiniu", QiNiuUploadExtension, project)

        project.afterEvaluate {

            //check QUpload plugin status
            def upload = project.QUpload
            if (!upload.uploadEnabled) {
                project.logger.error("QUpload is disabled.")
                return
            }

            //create tasks

            project.tasks.create("QiNiuUploadTask", QiNiuUploadTask)

        }
    }
}
