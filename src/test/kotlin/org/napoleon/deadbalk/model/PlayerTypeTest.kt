package org.napoleon.deadbalk.model

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.napoleon.deadbalk.models.PlayerType
import org.napoleon.deadbalk.utils.findBy

class PlayerTypeTest: ShouldSpec() {
    init {
        context("PlayerType valueOf") {
            should("return PlayerType.Veteran for Veteran") {
                PlayerType::text findBy "Veteran" shouldBe PlayerType.VETERAN
            }
        }
    }
}