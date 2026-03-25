# hmpps-offender-events-ui
[![Ministry of Justice Repository Compliance Badge](https://github-community.service.justice.gov.uk/repository-standards/api/hmpps-offender-events-ui/badge?style=flat)](https://github-community.service.justice.gov.uk/repository-standards/hmpps-offender-events-ui)
[![Docker Repository on ghcr](https://img.shields.io/badge/ghcr.io-repository-2496ED.svg?logo=docker)](https://ghcr.io/ministryofjustice/hmpps-offender-events-ui)

Dev tool to surface recent offender events

## Running Locally

To start up a local instance of localstack to mock aws, run the command:

```docker-compose up localstack``` 

Then start the application from Intellij with active profile `localstack`.

You can now inject messages onto the topic using command `./create-prison-movements-messages-local.bash`.
