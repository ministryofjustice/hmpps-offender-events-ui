#!/usr/bin/env bash
export AWS_ACCESS_KEY_ID=foobar
export AWS_SECRET_ACCESS_KEY=foobar
export AWS_DEFAULT_REGION=eu-west-2
aws --endpoint-url=http://localhost:4575 sns create-topic --name offender_events

aws --endpoint-url=http://localhost:4576 sqs create-queue --queue-name offender_events_ui_dlq
aws --endpoint-url=http://localhost:4576 sqs create-queue --queue-name offender_events_ui_queue
aws --endpoint-url=http://localhost:4576 sqs set-queue-attributes --queue-url "http://localhost:4576/queue/offender_events_ui_queue" --attributes '{"RedrivePolicy":"{\"maxReceiveCount\":\"3\", \"deadLetterTargetArn\":\"arn:aws:sqs:eu-west-2:000000000000:offender_events_ui_dlq\"}"}'
aws --endpoint-url=http://localhost:4575 sns subscribe \
    --topic-arn arn:aws:sns:eu-west-2:000000000000:offender_events \
    --protocol sqs \
    --notification-endpoint http://localhost:4576/queue/offender_events_ui_queue
