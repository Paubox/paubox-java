# CLAUDE.md — paubox-java

Developer context for working on this SDK.

## Overview

This repo is the official Java wrapper for two Paubox APIs:

- **Paubox Email API** — send HIPAA-compliant emails, check delivery status. Requires an API key and username. Base URL: `https://api.paubox.net/v1/{API_USER}/`
- **Paubox Forms API** — retrieve form definitions and submit responses. No API key required. Base URL: `https://next.paubox.com`

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

Additional property for forms tests:

```
FORM_ID=550e8400-e29b-41d4-a716-446655440000
```

## Architecture Patterns

### Token-authenticated endpoints (Email API)
`EmailService` passes `"Token token=" + Constants.API_KEY` as the `Authorization` header via `APIHelper.callToAPIByGet` / `callToAPIByPost`. Responses are JSON — deserialize with Jackson `ObjectMapper` using `FAIL_ON_UNKNOWN_PROPERTIES = false`.

### Public endpoints (Forms API)
`FormsService` passes `null` for the auth header (already handled by `APIHelper`). The submit endpoint returns HTTP 201 with an empty body, so it uses `APIHelper.callToAPIByPostReturnCode` which returns the status code as an int instead of the body string.

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
