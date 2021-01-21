# upload
gradle 插件用于apk上传


#### 通用配置
| 属性 | 类型 | 是否必填 | 作用 |
| :-- | :-- | :-- | :-- |
| fileDir | String | 是 | 要上传的文件所在路径 ，不可空,否则抛出异常，目前只支持绝对路径 |
| archiveDir | String | 否 | 上传成功后，一些文件的存档路径，如蒲公英返回的json数据等，可空，空时使用默认存档路径,目前只支持绝对路径 |
| exceptionDir | String | 否 | 上传出现异常时，异常日志的存档路径，用于错误分析，可空 ，空时使用默认存档路径，目前只支持绝对路径 |
| dependsTask | String | 否 | 所依赖的task，可空，非空时，在执行upload之前，必须要先执行dependsTask， 如’build’ |
| filter | String数组 | 否 | 过滤条件，用于过滤出要上传的文件，如’.apk’,’.war’,’*.jar’等，可空，空时上传所有文件 |


#### 七牛上传方式的专有配置
| 属性 | 类型 | 是否必填 | 作用 |
| :-- | :-- | :-- | :-- |
| accessKey | String | 是 | 七牛accessKey,必传 |
| secretKey | String | 是 | 七牛secretKey,必传 |
| bucket | String | 是 | 上传到七牛哪个bucket下,必传 |

