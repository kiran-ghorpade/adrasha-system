export type SideMenu = {
  label: string,
  route: string,
  icon : string,
}

export type SideMenuUsers = {
  user: SideMenu[],
  asha : SideMenu[],
  admin : SideMenu[],
}

export const SIDEBAR_MENUS : SideMenuUsers = {
  user: [
    { label: 'Dashboard', route: '/dashboard', icon: 'dashboard' },
    { label: 'Profile', route: '/profile', icon: 'person' },
  ],
  asha: [
    { label: 'Patients', route: '/asha/patients', icon: 'group' },
    { label: 'Reports', route: '/asha/reports', icon: 'description' },
  ],
  admin: [
    { label: 'User Management', route: '/admin/users', icon: 'admin_panel_settings' },
    { label: 'Settings', route: '/admin/settings', icon: 'settings' },
  ]
};