# 📝 Angular & TypeScript Best Practices Guide

This guide provides notes and tips for building a scalable, maintainable, and performant Angular application using TypeScript, tailored to your feature-first architecture (`src/app/` with `core/`, `shared/`, `features/`, `layouts/`, etc.). It emphasizes modern Angular practices (standalone components, signals, native control flow) and industry-standard guidelines for clean code, performance, and accessibility.

---

## 🧱 Project Context

This guide aligns with your feature-first folder structure:

```
src/
└── app/
    ├── core/
    │   ├── api/
    │   ├── constants/
    │   ├── guards/
    │   ├── interceptors/
    │   ├── models/
    │   ├── services/
    │   └── utils/
    ├── shared/
    │   ├── components/
    │   ├── directives/
    │   ├── pipes/
    │   ├── services/
    │   ├── models/
    │   ├── utils/
    │   └── constants/
    ├── features/
    │   ├── dashboard/
    │   ├── member/
    │   └── settings/
    ├── layouts/
    ├── assets/
    ├── environments/
    └── tests/
```

The following best practices ensure your project remains modular, performant, and maintainable while leveraging Angular 18+ features (e.g., standalone components, signals, and native control flow).

---

## 🛠️ TypeScript Best Practices

1. **Use Strict Type Checking**:
   - Enable `"strict": true` in `tsconfig.json` to enforce strict type safety (e.g., `noImplicitAny`, `strictNullChecks`).
   - **Tip**: Catch type errors early during development to prevent runtime issues.

2. **Prefer Type Inference**:
   - Let TypeScript infer types for obvious cases (e.g., `const count = 42;` infers `number`).
   - **Tip**: Explicitly declare types for complex objects or function signatures to improve clarity.

3. **Avoid `any`; Use `unknown` for Uncertainty**:
   - Use `unknown` when the type is unclear (e.g., API responses before validation).
   - **Example**:
     ```typescript
     function parseResponse(data: unknown) {
       if (typeof data === 'string') {
         return JSON.parse(data);
       }
       return data;
     }
     ```
   - **Tip**: Narrow `unknown` types with type guards or assertions before use.

4. **Use Interfaces for Models**:
   - Define interfaces in `core/models/` or `shared/models/` for type safety.
   - **Example** (in `core/models/user.ts`):
     ```typescript
     export interface User {
       id: number;
       name: string;
       email: string;
     }
     ```
   - **Tip**: Use `Readonly<T>` for immutable models to prevent unintended mutations.

5. **Leverage Barrel Files**:
   - Use `index.ts` in folders (e.g., `core/models/index.ts`) for clean imports.
   - **Example**:
     ```typescript
     export * from './user';
     export * from './error-response';
     ```

---

## 🚀 Angular Best Practices

### General Guidelines

1. **Use Standalone Components**:
   - Prefer standalone components over `NgModules` for modularity and tree-shaking.
   - **Example** (in `features/member/components/member-card/member-card.component.ts`):
     ```typescript
     import { Component, input, output } from '@angular/core';
     import { CommonModule } from '@angular/common';

     @Component({
       selector: 'app-member-card',
       standalone: true,
       imports: [CommonModule],
       template: `<div>{{ member().name }}</div>`,
       changeDetection: ChangeDetectionStrategy.OnPush,
     })
     export class MemberCardComponent {
       member = input.required<User>();
       select = output<number>();
     }
     ```
   - **Tip**: Import only necessary modules (e.g., `CommonModule`) to reduce bundle size.

2. **Avoid Explicit `standalone: true`**:
   - Since Angular 18, `standalone: true` is implied for components, directives, and pipes.
   - **Tip**: Remove redundant `standalone` properties to keep code clean.

3. **Lazy Load Feature Routes**:
   - Use lazy loading for `features/` routes to optimize initial load time.
   - **Example** (in `app.routes.ts`):
     ```typescript
     import { Routes } from '@angular/router';
     import { DefaultLayoutComponent } from '@app/layouts';

     export const routes: Routes = [
       {
         path: '',
         component: DefaultLayoutComponent,
         children: [
           {
             path: 'member',
             loadComponent: () => import('./features/member/pages/member-page/member-page.component').then(m => m.MemberPageComponent),
           },
         ],
       },
     ];
     ```
   - **Tip**: Use `loadComponent` for standalone components or `loadChildren` for feature modules.

4. **Use `NgOptimizedImage` for Static Images**:
   - Use `NgOptimizedImage` for images in `assets/images/` to enable lazy loading and optimization.
   - **Example** (in `shared/components/header/header.component.html`):
     ```html
     <img ngSrc="/assets/images/logo.png" width="100" height="50" alt="App Logo" priority />
     ```
   - **Tip**: Always specify `width`, `height`, and `alt` for accessibility and performance.

---

### Components

1. **Keep Components Focused**:
   - Ensure each component has a single responsibility (e.g., `member-card` displays a member, `member-form` handles input).
   - **Tip**: Split large components into smaller, reusable ones in `shared/components/` or `features/*/components/`.

2. **Use `input()` and `output()` Functions**:
   - Replace `@Input()` and `@Output()` decorators with `input()` and `output()` for modern Angular.
   - **Example** (in `features/member/components/member-list/member-list.component.ts`):
     ```typescript
     import { Component, output } from '@angular/core';
     import { Member } from '@app/core/models';

     @Component({
       selector: 'app-member-list',
       standalone: true,
       template: `
         @for (member of members; track member.id) {
           <app-member-card [member]="member" (select)="onSelect($event)" />
         }
       `,
       changeDetection: ChangeDetectionStrategy.OnPush,
     })
     export class MemberListComponent {
       members: Member[] = [];
       select = output<number>();
     }
     ```

3. **Use `computed()` for Derived State**:
   - Compute derived values reactively with signals.
   - **Example**:
     ```typescript
     import { Component, signal, computed } from '@angular/core';

     @Component({
       selector: 'app-member-details',
       standalone: true,
       template: `<p>Full Name: {{ fullName() }}</p>`,
     })
     export class MemberDetailsComponent {
       firstName = signal('John');
       lastName = signal('Doe');
       fullName = computed(() => `${this.firstName()} ${this.lastName()}`);
     }
     ```

4. **Set `ChangeDetectionStrategy.OnPush`**:
   - Use `OnPush` to reduce unnecessary change detection cycles.
   - **Tip**: Combine with signals for reactive updates.

5. **Use Inline Templates for Small Components**:
   - For simple components, define templates inline to reduce file clutter.
   - **Example**:
     ```typescript
     @Component({
       selector: 'app-spinner',
       standalone: true,
       template: `<div class="spinner"></div>`,
       styles: [`.spinner { /* styles */ }`],
       changeDetection: ChangeDetectionStrategy.OnPush,
     })
     export class SpinnerComponent {}
     ```

6. **Prefer Reactive Forms**:
   - Use `ReactiveFormsModule` for complex forms in `features/*/components/`.
   - **Example** (in `features/member/components/member-form/member-form.component.ts`):
     ```typescript
     import { Component, inject } from '@angular/core';
     import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

     @Component({
       selector: 'app-member-form',
       standalone: true,
       imports: [ReactiveFormsModule],
       template: `
         <form [formGroup]="form" (ngSubmit)="onSubmit()">
           <input formControlName="name" />
           <button type="submit">Save</button>
         </form>
       `,
     })
     export class MemberFormComponent {
       private fb = inject(FormBuilder);
       form = this.fb.group({ name: [''] });
       onSubmit() {
         console.log(this.form.value);
       }
     }
     ```

7. **Avoid `ngClass` and `ngStyle`**:
   - Use `[class]` and `[style]` bindings for better type safety and clarity.
   - **Example**:
     ```html
     <div [class.active]="isActive" [style.backgroundColor]="color">Content</div>
     ```

---

### State Management

1. **Use Signals for Local State**:
   - Manage component state reactively with `signal()`.
   - **Example** (in `features/member/pages/member-page/member-page.component.ts`):
     ```typescript
     import { Component, signal } from '@angular/core';
     import { Member } from '@app/core/models';

     @Component({
       selector: 'app-member-page',
       standalone: true,
       template: `<app-member-list [members]="members()" />`,
     })
     export class MemberPageComponent {
       members = signal<Member[]>([]);
     }
     ```

2. **Use `computed()` for Derived State**:
   - Derive state from signals to keep transformations reactive.
   - **Example**:
     ```typescript
     import { Component, signal, computed } from '@angular/core';

     @Component({
       selector: 'app-member-filter',
       standalone: true,
       template: `<p>Active Members: {{ activeMembers().length }}</p>`,
     })
     export class MemberFilterComponent {
       members = signal<Member[]>([]);
       activeMembers = computed(() => this.members().filter(m => m.active));
     }
     ```

3. **Keep State Transformations Pure**:
   - Ensure state updates are predictable and side-effect-free.
   - **Tip**: Use `signal().update()` or `signal().set()` for controlled updates.

4. **Consider NgRx for Global State**:
   - For complex apps, use NgRx in `core/` for centralized state management.
   - **Tip**: Store NgRx-related files (actions, reducers, effects) in `core/state/`.

---

### Templates

1. **Keep Templates Simple**:
   - Avoid complex logic in templates; delegate to components or services.
   - **Tip**: Use methods or computed signals for logic.

2. **Use Native Control Flow**:
   - Replace `*ngIf`, `*ngFor`, and `*ngSwitch` with `@if`, `@for`, and `@switch`.
   - **Example** (in `features/member/components/member-list/member-list.component.html`):
     ```html
     @for (member of members; track member.id) {
       <app-member-card [member]="member" />
     } @empty {
       <p>No members found.</p>
     }
     ```

3. **Use Async Pipe for Observables**:
   - Subscribe to observables in templates with `| async` to avoid manual subscriptions.
   - **Example**:
     ```html
     @if (member$ | async; as member) {
       <app-member-details [member]="member" />
     }
     ```

---

### Services

1. **Single Responsibility**:
   - Design services in `core/services/` or `features/*/services/` for specific domains (e.g., `MemberService` for member-related API calls).
   - **Example** (in `features/member/services/member.service.ts`):
     ```typescript
     import { inject, Injectable } from '@angular/core';
     import { HttpClient } from '@angular/common/http';
     import { Observable } from 'rxjs';
     import { Member } from '@app/core/models';

     @Injectable({ providedIn: 'root' })
     export class MemberService {
       private http = inject(HttpClient);
       private apiUrl = 'https://api.example.com/members';

       getMembers(): Observable<Member[]> {
         return this.http.get<Member[]>(this.apiUrl);
       }
     }
     ```

2. **Use `providedIn: 'root'` for Singletons**:
   - Register app-wide services in `core/services/` with `providedIn: 'root'`.
   - **Tip**: For feature-specific services, scope to the feature module or standalone component.

3. **Use `inject()` Over Constructor Injection**:
   - Use `inject()` for dependency injection in modern Angular.
   - **Example**:
     ```typescript
     import { Component, inject } from '@angular/core';
     import { MemberService } from '@app/features/member/services';

     @Component({
       selector: 'app-member-page',
       standalone: true,
       template: `<app-member-list [members]="members" />`,
     })
     export class MemberPageComponent {
       private memberService = inject(MemberService);
       members = signal<Member[]>([]);

       ngOnInit() {
         this.memberService.getMembers().subscribe(members => this.members.set(members));
       }
     }
     ```

---

## 🗂️ Integration with Your Folder Structure

### `core/`
- **Services**: Store singleton services (e.g., `AuthService`, `NotificationService`) in `core/services/` with `providedIn: 'root'`.
- **Models**: Define app-wide interfaces (e.g., `User`, `ErrorResponse`) in `core/models/`.
- **Guards/Interceptors**: Use `core/guards/` and `core/interceptors/` for routing and HTTP logic.

### `shared/`
- **Components**: Store reusable, stateless components (e.g., `ButtonComponent`, `SpinnerComponent`) in `shared/components/`.
- **Pipes/Directives**: Place reusable pipes and directives in `shared/pipes/` and `shared/directives/`.
- **Tip**: Use a `SharedModule` (if not fully standalone) to export shared resources.

### `features/`
- **Standalone Components**: Use standalone components in `features/*/components/` and `features/*/pages/`.
- **Lazy Loading**: Define routes in `features/*/routes.ts` for lazy loading.
- **Example** (in `features/member/routes.ts`):
  ```typescript
  import { Routes } from '@angular/router';
  import { MemberPageComponent } from './pages/member-page/member-page.component';

  export const memberRoutes: Routes = [
    { path: '', component: MemberPageComponent },
    { path: ':id', loadComponent: () => import('./pages/member-details-page/member-details-page.component').then(m => m.MemberDetailsPageComponent) },
  ];
  ```

### `layouts/`
- **Standalone Layouts**: Define layouts (e.g., `DefaultLayoutComponent`) as standalone components in `layouts/`.
- **Routing**: Apply layouts in `app.routes.ts` using `component` and `children`.

### `assets/`
- Use `NgOptimizedImage` for images in `assets/images/`.
- Store translation files in `assets/i18n/` for internationalization.

### `environments/`
- Use `environment.ts` and `environment.prod.ts` for configuration.
- **Tip**: Avoid hardcoding sensitive data; use environment variables.

---

## 📝 Notes for Future Reference

1. **Adopt Standalone Components**:
   - Transition to standalone components for new features to simplify the architecture.
   - Gradually refactor `NgModules` to standalone in `features/`.

2. **Leverage Signals**:
   - Use signals for reactive state management in components.
   - Combine with `computed()` for derived state and `effect()` for side effects.

3. **Optimize Performance**:
   - Enable `OnPush` change detection and lazy loading universally.
   - Use `NgOptimizedImage` and analyze bundle size with `ng build --stats-json`.

4. **Testing**:
   - Write unit tests (`.spec.ts`) for all components and services using Jasmine/Karma.
   - Use Cypress for e2e tests in `tests/`.

5. **Accessibility**:
   - Use semantic HTML, ARIA attributes, and `NgOptimizedImage`’s `alt` property.
   - Test with Lighthouse or axe for compliance.

6. **Code Quality**:
   - Enforce ESLint and Prettier for consistent code style.
   - Use Husky for pre-commit hooks to run linting and tests.

7. **Documentation**:
   - Maintain a `README.md` in each feature folder (e.g., `features/member/`) to document purpose and usage.
   - Use JSDoc for TypeScript functions and interfaces.

8. **CI/CD**:
   - Set up GitHub Actions for automated builds, tests, and deployments.
   - Use `ng build --prod` for optimized production builds.

This guide ensures your Angular project remains modern, scalable, and aligned with best practices. Refer to it when adding new features, refactoring, or optimizing your application.