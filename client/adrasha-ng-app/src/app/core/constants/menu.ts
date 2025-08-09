export type Menu = {
  label: string;
  route: string;
  icon: string;
};

const user: Menu[] = [
  { label: 'Dashboard', route: '/dashboard', icon: 'dashboard' },
  { label: 'History', route: '/role-requests/', icon: 'history' },
  // { label: 'Request', route: '/role-requests/new', icon: 'create' },
];

const asha: Menu[] = [
  {
    label: 'Dashboard',
    icon: 'dashboard',
    route: '/dashboard',
  },
  {
    label: 'Registry',
    icon: 'app_registration',
    route: '/registry/families',
  },
  {
    label: 'Search',
    icon: 'search',
    route: '/search',
  },
  {
    label: 'Analytics',
    icon: 'show_chart',
    route: '/analytics',
  },
  {
    label: 'Reports',
    icon: 'picture_as_pdf',
    route: '/reports',
  },
];

const admin: Menu[] = [
  {
    label: 'Dashboard',
    route: '/dashboard',
    icon: 'dashboard',
  },
  {
    label: 'Users',
    route: '/users',
    icon: 'group',
  },
  {
    label: 'RoleRequests',
    route: '/role-requests',
    icon: 'person_add',
  },
  {
    label: 'MasterData',
    route: '/masterdata',
    icon: 'storage',
  },
];

export const SIDEBAR_MENUS = { user, asha, admin };
