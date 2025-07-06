export type Menu = {
  label: string;
  route: string;
  icon: string;
};

const user: Menu[] = [
  { label: 'Dashboard', route: '/dashboard', icon: 'dashboard' },
  { label: 'Request', route: '/role-request/new', icon: 'create' },
  { label: 'History', route: '/role-request/history', icon: 'history' },
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
    route: '/registry',
  },
  {
    label: 'Search',
    icon: 'search',
    route: '/search',
  },
  {
    label: 'Analytics',
    icon: 'show_chart',
    route: '/anaytics',
  },
  // {
  //   label: 'Reports',
  //   icon: 'picture_as_pdf',
  //   route: '/reports',
  // },
];

const admin: Menu[] = [
  {
    label: 'Dashboard',
    route: '/dashboard',
    icon: 'dashboard',
  },
  // {
  //   label: 'Users',
  //   route: '/users',
  //   icon: 'group',
  // },
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
