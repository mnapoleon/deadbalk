package org.napoleon.deadbalk.repository

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import kotlinx.coroutines.flow.flow
import org.junit.jupiter.api.Test
import org.napoleon.deadbalk.handlers.DeadbalKHandler
import org.napoleon.deadbalk.models.Player
import org.napoleon.deadbalk.models.PlayerDto
import org.napoleon.deadbalk.models.toDto
import org.napoleon.deadbalk.router.DeadbalKRouter
import org.napoleon.deadbalk.services.NameGenerator
import org.napoleon.deadbalk.services.PlayerGenerator
import org.napoleon.deadbalk.services.PositionGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList

@WebFluxTest
@Import(DeadbalKRouter::class, DeadbalKHandler::class, NameGenerator::class, PlayerGenerator::class, PositionGenerator::class)
class DeadbalKRepositoryTest(
    @Autowired private val client: WebTestClient
) {

    @MockkBean
    private lateinit var repository: DeadbalKRepository

    private fun aPlayer(
        name: String = "Jim Jones",
        position: String = "3B",
        handedness: String = "Right",
        playerType: String = "Old_Timer",
        batterTarget: Int = 35,
        onBaseTarget: Int = 40
    ) =
        Player(
            name = name,
            position = position,
            handedness = handedness,
            playerType = playerType,
            batterTarget = batterTarget,
            onBaseTarget = onBaseTarget
        )

    @Test
    fun `Retrieve all players`() {
        every {
            repository.findAll()
        } returns flow {
            emit(aPlayer())
        }

        client
            .get()
            .uri("/api/players")
            .exchange()
            .expectStatus()
            .isOk
            .expectBodyList<PlayerDto>()
            .hasSize(1)
            .contains(aPlayer().toDto())
    }

    @Test
    fun `Retrieve existing player by id`() {
        coEvery {
            repository.findById(any())
        } coAnswers {
            aPlayer()
        }

        client
            .get()
            .uri("/api/players/1")
            .exchange()
            .expectStatus()
            .isOk
            .expectBody<PlayerDto>()
            .isEqualTo(aPlayer().toDto())
    }

    @Test
    fun `Delete existing player`() {
        coEvery {
            repository.existsById(any())
        } coAnswers {
            true
        }

        coEvery {
            repository.deleteById(any())
        } coAnswers {
            nothing
        }

        client
            .delete()
            .uri("/api/players/2")
            .exchange()
            .expectStatus()
            .isNoContent

        coVerify {
            repository.deleteById(any())
        }
    }

    @Test
    fun `Delete non-existent player`() {
        coEvery {
            repository.existsById(any())
        } coAnswers {
            false
        }

        client
            .delete()
            .uri("/api/olayers/2")
            .exchange()
            .expectStatus()
            .isNotFound

        coVerify(exactly = 0) { repository.deleteById(any())}
    }
}