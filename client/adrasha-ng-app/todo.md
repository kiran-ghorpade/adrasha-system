# Adrasha System Project: To-Do List

This document outlines the necessary tasks to bring the Adrasha System project to completion, based on an analysis of the current codebase.

---


### **Status Legend**
| Emoji | Meaning         |
| ----- | --------------- |
| 🟢    | Done            |
| 🟠    | Pending         |
| 🟡    | In Progress     |
| 🔴    | Blocked / Issue |

---


### **Task Status Table**

| Task                                                                     | Status | Description                                                                                                                                                             |
| ------------------------------------------------------------------------ | ------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Create Centralized Error Handling      | 🟠     | Pending         |
| Show alert according to errors         | 🟢     | Done            |
| Update en.json, remove unused values   | 🟢     | Done            |
| Add mr.json for Marathi                | 🟡     | In Progress     |
| Add language change option in settings | 🟢     | Done            |
| Add new feature                        | 🔴     | Blocked / Issue |
| **High-Priority Bugs**                                                   |        |                                                                                                                                                                         |
| Fix `EmptyError` on application startup                                  | 🔴     | Modify `error.interceptor.ts` to `throwError` instead of returning `EMPTY`, which is causing the app initializer to fail on API errors.                                  |
| Fix `NullPointerException` in Analytics Services                         | 🔴     | Add null-checks in `AgeGroupCountService`, `GenderCountService`, `NCDCountService`, and `PovertyStatusCountService` to handle cases where no previous count exists.    |
| Correct `user-service` and `user-form-factory` in Frontend `users` feature | 🔴     | These files were incorrectly copied from the `role-request` feature. They need to be rewritten with the correct logic for managing users.                                |
| Fix `mat-checkbox` value binding in Health Record Form                     | 🟠     | In `health-record-form.component.html`, change `value="ncd.id"` to `[value]="ncd.id"` to correctly bind the NCD's UUID to the checkbox.                            |
| Implement Search Functionality                                           | 🟠     | In `search-page.component.ts`, connect the `searchControl` value to the `getMemberPage` API call to filter results based on user input.                           |
| **Backend Development**                                                  |        |                                                                                                                                                                         |
| Refactor User Data Management in `user-service`                          | 🟠     | Avoid creating duplicate `User` entities. The `user-service` should fetch user details from `auth-service` instead of storing its own copy.                           |
| Refactor Family & Head Member Creation in `data-service`                 | 🟠     | Make the creation of a `Family` and its head `Member` a more robust, atomic transaction to prevent inconsistent data states.                                         |
| Complete Event Listeners in `analytics-service`                          | 🟠     | Implement the remaining event listeners for `Family`, `HealthRecord`, and `RoleRequest` events to ensure analytics data is comprehensive.                             |
| Enhance JWT Claims in `api-gateway`                                      | 🟠     | Clarify the JWT claims in `JwtUtil.java`. Rename the `"id"` claim to `"username"` to avoid confusion and ensure consistency.                                        |
| **Frontend Development**                                                 |        |                                                                                                                                                                         |
| Refactor `effect` Anti-Pattern in Analytics Components                   | 🟠     | In all line chart components, remove subscriptions inside `effect`. Convert the observable chains to signals using `toSignal` for a more declarative approach.     |
| Fix Race Condition in `family-details-page`                              | 🟠     | Use `forkJoin` or `combineLatest` to ensure both `familyDetails` and `memberList` observables have emitted before creating the final data signal.                  |
| Fix Form Initialization in `family-edit-form`                            | 🟠     | Implement `ngOnChanges` to patch the form with data when the `entity` input is asynchronously updated by the parent component.                                      |
| Complete Master Data CRUD UI                                             | 🟠     | Finish the UI and logic for creating, reading, updating, and deleting Locations, Health Centers, and NCDs.                                                      |
| Create Centralized Error Handling                                        | 🟠     | Implement a global error handling strategy that uses the `AlertService` to display user-friendly messages for different types of API and client-side errors.       |
| **Internationalization (i18n)**                                          |        |                                                                                                                                                                         |
| Add `mr.json` for Marathi Language                                       | 🟡     | Create the Marathi translation file and populate it with the keys extracted from the `en.json` file.                                                                |
| Add Language Change Option in Settings                                   | 🟠     | Implement a UI component in the settings page that allows the user to switch between English and Marathi.                                                           |
| Update `en.json`, remove unused values                                   | 🟢     | Done.                                                                                                                                                                   |
| **Testing & Documentation**                                              |        |                                                                                                                                                                         |
| Write Backend Unit & Integration Tests                                   | 🟠     | Create comprehensive tests for all backend services to ensure reliability and catch regressions.                                                                    |
| Write Frontend Unit Tests                                                | 🟠     | Write unit tests for components, services, and guards in the Angular application.                                                                                       |
| Update Project READMEs                                                   | 🟠     | Update the main `README.md` and individual service/app READMEs with detailed setup instructions, architecture overview, and API documentation links.            |
| **Deployment**                                                           |        |                                                                                                                                                                         |
| Finalize Docker Compose Configuration                                    | 🟡     | Test and finalize the `docker-compose.yml` file to ensure all services start and communicate correctly.                                                         |
| Set up CI/CD Pipeline                                                    | 🟠     | Create a continuous integration and deployment pipeline (e.g., using GitHub Actions) to automate testing and deployment.                                            |
