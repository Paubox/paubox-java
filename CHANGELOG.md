# Changelog

All notable changes to this project will be documented in this file.

## Unreleased

This SDK has never been published to a registry and has never carried a git
tag. `1.0.0` will be the first tagged release. The notes below cover the state
of the source as of that release, not a diff against any previously shipped
artifact — there isn't one.

### 🚀 Features

- **Transactional Email** via `EmailService` / `EmailInterface`: send messages, send bulk messages, and retrieve delivery disposition
- **Paubox Forms** via `FormsService` / `FormsInterface`
  - Public endpoints, no credential attached: retrieve a form, submit a form with attachments
  - Form management with a scoped API key (`forms` scope, sent as a Bearer token): list, create, update, archive, unarchive, copy, and form statistics
  - Submissions: list submissions, and CSV and PDF export
- Request and response models under `com.paubox.data` for every endpoint

### ⚠️ Notes for consumers

- The `Paubox.Email.API.jar` committed under `stable-jar-file/` was last rebuilt in **November 2019** and predates all of the Forms work above. Releases from `1.0.0` onward attach a freshly built jar to the GitHub release; prefer that over the committed file
- The SDK is not on Maven Central. Publishing is deliberately out of scope for now
