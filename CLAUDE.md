# CLAUDE.md — paubox-java

Developer context for working on this SDK.

## Overview

This repo is the official Java wrapper for two Paubox APIs:

- **Paubox Email API** — send HIPAA-compliant emails, check delivery status. Requires an API key and username. Base URL: `https://api.paubox.net/v1/{API_USER}/`
- **Paubox Forms API** — retrieve form definitions, submit responses, and manage forms/submissions. Public respondent endpoints (get public form data, submit) need no key; management endpoints (list/create/update/archive/copy forms, stats, submissions, CSV/PDF export) need a scoped API key with the `forms` scope. Base URL: `https://apx.paubox.com/forms`

## Project Layout

```
paubox-java/
├── README.md                       # User-facing install + usage guide
├── api.md                          # Full API reference
├── CLAUDE.md                       # This file
└── Paubox_Java/
    └── paubox-java/                # Maven module
        ├── pom.xml
        └── src/
            ├── com/paubox/
            │   ├── common/         # Constants
            │   ├── config/         # ConfigurationManager (loads .properties)
            │   ├── data/           # DTOs / response models
            │   └── service/        # Interfaces + service implementations
            └── test/               # JUnit 4 tests
```

## Build

```bash
mvn package -f Paubox_Java/paubox-java/pom.xml
```

Produces `Paubox_Java/paubox-java/target/Paubox.Email.API.jar` (fat JAR with all dependencies).

## Run Tests

```bash
mvn test -f Paubox_Java/paubox-java/pom.xml -Dproperties=Paubox_Java/paubox-java/src/test/config.properties
```

The test config file is not committed. Copy the sample and fill in your credentials:

```bash
cp Paubox_Java/paubox-java/src/test/config.properties.sample \
   Paubox_Java/paubox-java/src/test/config.properties
```

Required properties for email tests:

```
APIKEY=your-api-key
APIUSER=your-api-username
RECIPIENTS=recipient@example.com
PDFFILE=src/test/testFile.pdf
```

Additional properties for forms tests:

```
FORM_ID=550e8400-e29b-41d4-a716-446655440000
FORMSAPIKEY=scoped-api-key-with-forms-scope
CUSTOMER_ID=12345
```

`FORM_ID` is used by the public and submission-listing tests, `FORMSAPIKEY` by all forms management tests (they skip when it's absent), and `CUSTOMER_ID` by the form create/update lifecycle test.

## Architecture Patterns

### Token-authenticated endpoints (Email API)
`EmailService` passes `"Token token=" + Constants.API_KEY` as the `Authorization` header via `APIHelper.callToAPIByGet` / `callToAPIByPost`. Responses are JSON — deserialize with Jackson `ObjectMapper` using `FAIL_ON_UNKNOWN_PROPERTIES = false`.

### Public endpoints (Forms API)
For the respondent endpoints (`getForm`, `submitForm`), `FormsService` passes `null` for the auth header (already handled by `APIHelper`). The submit endpoint returns HTTP 201 with an empty body, so it uses `APIHelper.callToAPIByPostReturnCode` which returns the status code as an int instead of the body string.

### Scoped-key endpoints (Forms API management)
All other Forms methods pass `"Bearer " + apiKey` as the `Authorization` header. The key is a scoped API key with the `forms` scope, held in a `private final String apiKey` field on `FormsService`: `new FormsService(String apiKey)` sets it explicitly, and the no-arg constructor falls back to `Constants.FORMS_API_KEY` (loaded by `ConfigurationManager` from the `FORMSAPIKEY` property). A missing key only fails when an authenticated method is called. Binary exports (CSV/PDF) use `APIHelper.callToAPIByGetBytes`, which returns `byte[]`; `updateForm` uses `APIHelper.callToAPIByPut`.

### Adding a new endpoint
1. Add a response DTO in `src/com/paubox/data/` — use `@JsonProperty` for snake_case fields.
2. Add the method to the relevant interface (`EmailInterface` or `FormsInterface`).
3. Implement in the service class — follow the GET or POST pattern already there.
4. Add tests in `src/test/`.

## Key Dependencies

| Library | Version | Use |
|---|---|---|
| Apache HttpClient | 4.5.13 | HTTP requests |
| Jackson Databind | 2.13.2.1 | JSON serialization |
| JSON Simple | 1.1.1 | Request body building (email only) |
| JUnit | 4.13.1 | Tests |

## Releases

Releases are automated with [release-please](https://github.com/googleapis/release-please). Merging to `master` refreshes a standing release PR; merging *that* PR bumps `version.txt` and the `<version>` in `Paubox_Java/paubox-java/pom.xml`, writes `CHANGELOG.md`, creates a bare `vX.Y.Z` tag and a GitHub release, and **attaches a freshly built jar to that release**.

Do **not** hand-edit the pom version, `version.txt`, or `CHANGELOG.md` — release-please owns all three.

The next version comes from PR titles: `feat:` gives a minor bump, `fix:` a patch, and a `!` suffix or a `BREAKING CHANGE:` footer gives a major. `.github/workflows/pr-title.yml` rejects titles release-please cannot parse.

### Not published to a registry

This SDK is **not on Maven Central**, and publishing is deliberately out of scope. The `groupId`/`artifactId` are still `Paubox`/`Paubox`, which Central would reject — changing them to reverse-DNS coordinates is part of the publishing work, not this.

Until then the GitHub release *is* the distribution channel. The jar committed under `stable-jar-file/` was last rebuilt in **November 2019** and predates all of the Forms API work; prefer the jar attached to the latest release.

### Two configuration details that fail silently if changed

**The pom XPath must stay namespace-agnostic.** The pom declares `xmlns="http://maven.apache.org/POM/4.0.0"`. A plain `//project/version` matches nothing and reports no error, so a release would tag and write a changelog while leaving the pom on its old version. The config uses `/*[local-name()='project']/*[local-name()='version']`.

**`CHANGELOG.md` must keep a heading matching `/\n###? v?[0-9[]/`.** That is why the staging heading is the bracketed `## [Unreleased]`. Without a match release-please prepends a second `# Changelog` title and demotes every existing heading.

`release-type` is `simple`, not `maven`: the pom is not at the repository root, and the `maven` strategy rewrites it to `X.Y.Z-SNAPSHOT` after every release.
