package dockerjavaexample

import com.github.dockerjava.api.async.ResultCallbackTemplate
import com.github.dockerjava.api.model.BuildResponseItem
import com.github.dockerjava.api.model.ResponseItem
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient
import java.io.File

fun main() {
    val config = DefaultDockerClientConfig.createDefaultConfigBuilder().build()
    val client = DockerClientImpl.getInstance(config, ZerodepDockerHttpClient.Builder().dockerHost(config.dockerHost).build())
    val callback = object : ResultCallbackTemplate<Nothing, BuildResponseItem>() {
        lateinit var imageId: String
        lateinit var error: ResponseItem.ErrorDetail

        override fun onNext(`object`: BuildResponseItem?) {
            val item = `object` ?: return
            if (item.isBuildSuccessIndicated) {
                imageId = item.imageId
            } else if (item.isErrorIndicated) {
                error = item.errorDetail!!
            }
            item.stream?.let { print(it) }
        }
    }

    client
        .buildImageCmd(File("docker"))
        .withTags(setOf("testo"))
        .exec(callback)
        .awaitCompletion()

    println(callback.imageId)
}