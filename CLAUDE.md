# CLAUDE.md — paubox-java

Developer context for working on this SDK.

## Overview

This repo is the official Java wrapper for two Paubox APIs:

- **Paubox Email API** — send HIPAA-compliant emails, check delivery status. Requires an API key. Base URL: `https://api.paubox.com/v1/`
- **Paubox Forms API** — retrieve form definitions, submit responses, and manage forms/submissions. Public respondent endpoints (get public form data, submit) need no key; management endpoints (list/create/update/archive/copy forms, stats, submissions, CSV/PDF export) need a scoped API key with the `forms` scope. Base URL: `https://api.paubox.com/forms`

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
