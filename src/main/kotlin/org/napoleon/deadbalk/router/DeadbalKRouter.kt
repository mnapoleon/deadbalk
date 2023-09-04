package org.napoleon.deadbalk.router

import org.napoleon.deadbalk.handlers.DeadbalKHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class DeadbalKRouter(
    private val deadBalkHandler: DeadbalKHandler
) {

    @Bean
    fun apiRouter() = coRouter {
        "/api/players".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("", deadBalkHandler::getAll)

                contentType(MediaType.APPLICATION_JSON).nest {
                    POST("", deadBalkHandler::add)
                }
                contentType(MediaType.APPLICATION_JSON).nest {
                    POST("generate", deadBalkHandler::generatePlayer)
                }

                "/{id}".nest {
                    GET("", deadBalkHandler::getById)
                    DELETE("", deadBalkHandler::deletePlayerById)
                }
            }
        }
        "/api/generate".nest{
            accept(MediaType.APPLICATION_JSON).nest {
                contentType(MediaType.APPLICATION_JSON).nest {
                    POST("", deadBalkHandler::generatePlayer)
                }
            }
        }
        "/api/names".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("", deadBalkHandler::getName)
            }
        }
    }
}