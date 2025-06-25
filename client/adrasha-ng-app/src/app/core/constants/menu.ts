type sidebar_menu = {
  label: string;
  route: string;
  icon: string;
};

const user: sidebar_menu[] = [
  { label: 'Request Status', route: '/requestStatus', icon: 'time' },
  { label: 'Settings', route: '/profile', icon: 'person' },
];

const asha: sidebar_menu[] = [
  { label: 'Profile', route: '/profile', icon: 'group' },
  { label: 'Dashboard', route: '/dashboard', icon: 'group' },
  { label: 'Registry', route: '/registry', icon: 'group' },
  { label: 'Reports', route: '/reports', icon: 'description' },
  { label: 'Analytics', route: '/analytics', icon: 'p-setting' },
  { label: 'Settings', route: '/settings', icon: 'person' },
];

const admin: sidebar_menu[] = [
  {
    label: 'Dashboard',
    route: '/dashboard',
    icon: 'group',
  },
  {
    label: 'User Management',
    route: '/users',
    icon: 'admin_panel_settings',
  },
  { label: 'Settings', route: '/settings', icon: 'settings' },
];

export const SIDEBAAR_MENUS = { user, asha, admin };
