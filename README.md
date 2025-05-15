# hmpps-offender-events-ui
[![repo standards badge](https://img.shields.io/badge/endpoint.svg?&style=flat&logo=github&url=https%3A%2F%2Foperations-engineering-reports.cloud-platform.service.justice.gov.uk%2Fapi%2Fv1%2Fcompliant_public_repositories%2Fhmpps-offender-events-ui)](https://operations-engineering-reports.cloud-platform.service.justice.gov.uk/public-report/hmpps-offender-events-ui "Link to report")
[![Docker Repository on ghcr](https://img.shields.io/badge/ghcr.io-repository-2496ED.svg?logo=docker)](https://ghcr.io/ministryofjustice/hmpps-offender-events-ui)

Dev tool to surface recent offender events

## Running Locally

To start up a local instance of localstack to mock aws, run the command:

```docker-compose up localstack``` 

Then start the application from Intellij with active profile `localstack`.

You can now inject messages onto the topic using command `./create-prison-movements-messages-local.bash`.
