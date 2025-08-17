export type Menu = {
  label: string;
  route: string;
  icon: string;
};

const user: Menu[] = [
  {
    label: 'app.navigation.sidebar.dashboard',
    route: '/dashboard',
    icon: 'dashboard',
  },
  {
    label: 'app.features.dashboard.user.viewHistory',
    route: '/role-requests/',
    icon: 'history',
  },
];

const asha: Menu[] = [
  {
    label: 'app.navigation.sidebar.dashboard',
    icon: 'dashboard',
    route: '/dashboard',
  },
  {
    label: 'app.navigation.sidebar.registry',
    icon: 'app_registration',
    route: '/registry/families',
  },
  {
    label: 'app.features.search.title',
    icon: 'search',
    route: '/search',
  },
  {
    label: 'app.features.analytics.page.title',
    icon: 'show_chart',
    route: '/analytics',
  },
  {
    label: 'app.navigation.sidebar.reports',
    icon: 'picture_as_pdf',
    route: '/reports',
  },
];

const admin: Menu[] = [
  {
    label: 'app.navigation.sidebar.dashboard',
    route: '/dashboard',
    icon: 'dashboard',
  },
  {
    label: 'app.features.users.page.title',
    route: '/users',
    icon: 'group',
  },
  {
    label: 'app.features.roleRequest.page.title',
    route: '/role-requests',
    icon: 'person_add',
  },
  {
    label: 'app.navigation.sidebar.masterdata',
    route: '/masterdata',
    icon: 'storage',
  },
];

export const SIDEBAR_MENUS = { user, asha, admin };
