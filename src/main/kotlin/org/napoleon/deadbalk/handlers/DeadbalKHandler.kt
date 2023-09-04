package org.napoleon.deadbalk.handlers

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.napoleon.deadbalk.models.PlayerDto
import org.napoleon.deadbalk.models.toDto
import org.napoleon.deadbalk.models.toEntity
import org.napoleon.deadbalk.repository.DeadbalKRepository
import org.napoleon.deadbalk.services.NameGenerator
import org.napoleon.deadbalk.services.PlayerGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ResourceLoader
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class DeadbalKHandler(
    private val deadbalKReposiotry: DeadbalKRepository,
    private val nameGenerator: NameGenerator,
    private val playerGenerator: PlayerGenerator
) {
    @Autowired
    private lateinit var resourceLoader: ResourceLoader

    suspend fun getName(req: ServerRequest): ServerResponse {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyAndAwait(
                flow{
                    emit(nameGenerator.getRandomName())
                }
            )
    }

    suspend fun getById(req: ServerRequest): ServerResponse {
        val id = Integer.parseInt(req.pathVariable("id"))
        val existingPlayer = deadbalKReposiotry.findById(id.toLong())

        return existingPlayer?.let {
            ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(it.toDto())
        } ?: ServerResponse.notFound().buildAndAwait()
    }

    suspend fun getAll(req: ServerRequest): ServerResponse {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyAndAwait(
                deadbalKReposiotry.findAll().map { it.toDto() }
            )
    }

    suspend fun add(req: ServerRequest): ServerResponse {
        val receivedPlayer = req.awaitBodyOrNull(PlayerDto::class)

        val completePlayer = playerGenerator.generateCompletePlayer(receivedPlayer)

        return completePlayer.let {
            ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(
                    deadbalKReposiotry
                        .save(it.toEntity())
                        .toDto()
                )
        } ?: ServerResponse.badRequest().buildAndAwait()
    }

    suspend fun deletePlayerById(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id")

        return if (deadbalKReposiotry.existsById(id.toLong())) {
            deadbalKReposiotry.deleteById(id.toLong())
            ServerResponse.noContent().buildAndAwait()
        } else {
            ServerResponse.notFound().buildAndAwait()
        }
    }

    suspend fun generatePlayer(req: ServerRequest): ServerResponse {
        return playerGenerator.generateRandomPlayer().let {
            ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(
                    deadbalKReposiotry.save(it.toEntity())
                        .toDto()
                )
        } ?: ServerResponse.badRequest().buildAndAwait()
    }

}