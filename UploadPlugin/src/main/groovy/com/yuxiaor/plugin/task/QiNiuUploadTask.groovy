package com.yuxiaor.plugin.task

import com.yuxiaor.plugin.exception.UploadException
import com.yuxiaor.plugin.extension.QiNiuUploadExtension
import com.yuxiaor.plugin.utils.CheckUtil
import com.yuxiaor.plugin.utils.FileUtil
import com.qiniu.common.QiniuException
import com.qiniu.storage.Configuration
import com.qiniu.storage.UploadManager
import com.qiniu.util.Auth

/**
 * 用于上传到七牛云
 */
class QiNiuUploadTask extends BaseUploadTask {
    private static final String TAG = "QiNiuUploadTask"

    QiNiuUploadTask() {
        group = "QUploadPlugin"
    }

    @Override
    void setupDependenciesIfNeeded() {
        final def dependsTask = project.extensions.QUpload.qiniu.dependsTask
        if (dependsTask != null) {
            def task = project.tasks.findByName(dependsTask)
            if (task != null && task.enabled) dependsOn task
        }
    }

    @Override
    void upload() {

        //ensure params
        QiNiuUploadExtension config = project.extensions.QUpload.qiniu
        config.checkParams()

        //obtain token
        Auth auth = Auth.create(config.accessKey, config.secretKey)

        //upload
        Configuration qnConfig = new Configuration()
        UploadManager uploadManager = new UploadManager(qnConfig)
        project.logger.error("config.fileDir=${config.fileDir}")
        def sourceDir = new File(config.fileDir)
        def files = FileUtil.parseFileWithFile(sourceDir, config.filter)
        if (files == null || files.size() == 0) return
        project.logger.error("${TAG}:uploading...")
        project.logger.error("${TAG}:qiniu->空间名 = ${config.bucket}")
        files.each {
            final String upToken = auth.uploadToken(config.bucket, it.name)
            uploadFile(uploadManager, it, upToken, config)
        }
    }

    void uploadFile(UploadManager uploadManager, File file, String upToken, QiNiuUploadExtension config) {
        try {
            project.logger.error("${TAG}:uploadFile->fileName = ${file.getName()}")
            project.logger.error("${TAG}:uploadFile->filePath = ${file.getAbsolutePath()}")
            uploadManager.put(file, file.getName(), upToken)
            project.logger.error("${TAG}:uploadFile->${file.getName()} upload success...")
        } catch (Exception exception) {
            //save exception to local file .
            def fileDir = CheckUtil.isEmpty(config.exceptionDir) ? getExceptionsDir() : config.exceptionDir
            def errorMsg
            if (exception instanceof QiniuException) {
                def e = exception as QiniuException
                errorMsg = e.error()
            } else {
                errorMsg = exception.toString()
            }
            project.logger.error("${TAG}:QiniuException=${errorMsg}")
            FileUtil.stringToFile(fileDir, "QiniuException", errorMsg, FileUtil.TXT_SUFFIX)

            // throw new Exception to notify developer
            throw new UploadException("上传七牛云异常，请查看${fileDir}中QiniuException开头的日志文件")
        }
    }
}