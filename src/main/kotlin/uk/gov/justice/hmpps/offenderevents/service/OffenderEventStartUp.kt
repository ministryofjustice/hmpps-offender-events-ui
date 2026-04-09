package uk.gov.justice.hmpps.offenderevents.service

import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import uk.gov.justice.hmpps.offenderevents.data.Event
import uk.gov.justice.hmpps.offenderevents.data.EventRepository

@Component
class OffenderEventStartUp(
  private val offenderEventStore: OffenderEventStore,
  private val eventRepository: EventRepository,
  private val gson: Gson,
  private val redisTemplate: RedisTemplate<Event, String>,
) {
  private companion object {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
  }

  @EventListener(ContextRefreshedEvent::class)
  fun readAllEvents() {
    redisTemplate.execute { connection ->
      val keys = connection.keyCommands().keys("event:*[^m]".toByteArray())
      log.info("Found {} items in redis using db size", keys?.size ?: 0)
      keys?.forEach {
        eventRepository.findById(String(it).substring("event:".length)).ifPresent { event ->
          val message = gson.fromJson(event.wholeMessage, Message::class.java)
          offenderEventStore.handleMessage(message)
        }
      }
    }
    log.info("Finished loading existing messages")
  }
}
