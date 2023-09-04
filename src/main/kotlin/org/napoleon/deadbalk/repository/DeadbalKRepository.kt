package org.napoleon.deadbalk.repository

import kotlinx.coroutines.flow.Flow
import org.napoleon.deadbalk.models.Player
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface DeadbalKRepository: CoroutineCrudRepository<Player, Long> {
    override fun findAll(): Flow<Player>
    override suspend fun findById(id: Long): Player?
    override suspend fun <S : Player> save(entity: S): Player
    override suspend fun deleteById(id: Long)
}