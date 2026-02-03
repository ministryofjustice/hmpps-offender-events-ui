package uk.gov.justice.hmpps.offenderevents.service

import org.assertj.core.api.Assertions.assertThat
import org.awaitility.kotlin.await
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import uk.gov.justice.hmpps.offenderevents.resource.DisplayMessage

@ExtendWith(OutputCaptureExtension::class)
class ListenerIntegrationTest : IntegrationTest() {
  @Test
  fun `Should consume and store an offender event message`() {
    val message = "/messages/externalMovement.json".readResourceAsText()

    awsSqsClient.sendMessage(SendMessageRequest.builder().queueUrl(queueUrl).messageBody(message).build()).get()

    `Wait for empty queue`()

    assertThat(eventStore.getPageOfMessages(null, null, null, null, null, 1))
      .extracting<String>(DisplayMessage::eventType)
      .containsExactly("EXTERNAL_MOVEMENT_RECORD-INSERTED")
  }

  @Test
  fun `Should consume and store an offender event message in Redis`(output: CapturedOutput) {
    val message = "/messages/externalMovement2.json".readResourceAsText()

    awsSqsClient.sendMessage(SendMessageRequest.builder().queueUrl(queueUrl).messageBody(message).build()).get()

    // Wait until we know we have received the message
    await.untilAsserted {
      assertThat(output.toString()).contains("Offender event received raw message")
    }
    // and that the queue is then empty
    `Wait for empty queue`()

    val redisEvent = eventRepository.findById("b730c957-1e17-4739-9808-a3419cecdd4a")

    assertThat(redisEvent.get().messageId).isEqualTo("b730c957-1e17-4739-9808-a3419cecdd4a")
    assertThat(redisEvent.get().wholeMessage).isEqualTo(message)
  }
}
