### Project Source Folder Structure

```
src/
├── assets/                # Images, fonts, etc.
│   └── logo.png
├── components/            # Reusable UI components
│   ├── Button.jsx
│   └── Header.jsx
├── i18n/                  # Internationalization files
│   ├── index.js
│   └── locales/
│       ├── en.json
│       └── fr.json
├── pages/                 # App pages or views
│   ├── Home.jsx
│   └── About.jsx
├── services/              # API and service calls
│   └── api.js
├── hooks/                 # Custom React hooks
│   └── useAuth.js
├── utils/                 # Utility functions
│   └── formatDate.js
├── App.jsx
├── index.js
└── i18n.js                # i18n config file
```


/src
    /app
        /core
            /constants
                settings.ts
                index.ts
            /errors
                404.component.ts
            /guards
                auth.guard.ts
                index.ts
            /interceptors
                api-interceptor.ts
                index.ts
            /models
                some-model.ts
                index.ts
            /services
                login.service.ts
                register.service.ts
                some-service.ts
                index.ts
            /utils
                some-util.ts
                index.ts
            core_index.ts
        /feature
            /auth
                /login
                    login.component.ts
                /register
                    register.component.ts
                routes.ts
                index.ts
           /dashborad
                /components
                    /admin-dashboard
                        admin.compoentn.ts
                    /user-dashboard
                        user.comoponet.ts
                /dashboard
                    dashboard.component.ts
                /services
                    dashboard.service.ts
                routes.ts
                index.ts
        /shared
            /components
            /hooks
            /interfaces
            /service
                pagintorservice.ts
                storage.service.ts
                message.service.ts
            /utils
                colors.ts
        /theme
            /admin-layout
            /auth-layout
            /header
            /sidebar
        app-routes.ts
        app.tsx
    /environment
        env.ts
    main.tsx