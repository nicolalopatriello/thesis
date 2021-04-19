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
    title: 'Dependencies manager',
    icon: 'flip-2-outline',
    link: '/pages/dependencies-manager',
  }
];
