import { NbMenuItem } from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'Dashboard',
    icon: 'activity-outline',
    link: '/pages/dashboard',
    home: true,
  },
  {
    title: 'Test vectors',
    icon: 'layers-outline',
    link: '/pages/test-vectors',
  },
  {
    title: 'Repositories',
    icon: 'code-outline',
    link: '/pages/repositories',
  },
  {
    title: 'Moon Cloud tests',
    icon: 'moon-outline',
    link: '/pages/moon-cloud-tests',
  },
  {
    title: 'Notifications',
    icon: 'bell-outline',
    link: '/pages/notifications',
  },
  {
    title: 'Connections',
    icon: 'link-2-outline',
    link: '/pages/connections',
  }
];
