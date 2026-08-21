# Changelog

All notable changes to this project will be documented in this file.

## 1.0.0 (2026-08-21)

First tagged release. This SDK had never been published to a registry and had
never carried a git tag, so the notes below describe the state of the source at
`1.0.0` rather than a diff against a previously shipped artifact — there isn't
one.

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
