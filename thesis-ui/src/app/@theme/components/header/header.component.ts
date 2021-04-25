import {Component, OnDestroy, OnInit} from '@angular/core';
import {NbMediaBreakpointsService, NbMenuService, NbSidebarService, NbThemeService} from '@nebular/theme';

import {UserData} from '../../../@core/data/users';
import {LayoutService} from '../../../@core/utils';
import {filter, map, takeUntil} from 'rxjs/operators';
import {Subject} from 'rxjs';
import {UsersService} from '../../../@core/services/users.service';
import {User} from '../../../@core/models/user';
import {Router} from '@angular/router';

@Component({
  selector: 'ngx-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();
  userPictureOnly: boolean = false;
  user: any;

  currentTheme = 'default';

  userMenu = [{title: 'Log out'}];
  public loggedUser: User;

  constructor(private sidebarService: NbSidebarService,
              private menuService: NbMenuService,
              private usersService: UsersService,
              private themeService: NbThemeService,
              private userService: UserData,
              private layoutService: LayoutService,
              private router: Router,
              private breakpointService: NbMediaBreakpointsService) {
  }

  ngOnInit() {
    this.currentTheme = this.themeService.currentTheme;

    this.menuService.onItemClick().pipe(
      filter(menu => menu.tag === 'userMenu')
    ).subscribe(t => {
      switch (t.item.title) {
        case 'Log out':
          localStorage.clear();
          this.router.navigate(['auth', 'login']);
          break;
      }
    });

    const {xl} = this.breakpointService.getBreakpointsMap();
    this.themeService.onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe((isLessThanXl: boolean) => this.userPictureOnly = isLessThanXl);


    this.usersService.self().subscribe(user => {
      this.loggedUser = user;
    });
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }


  toggleSidebar(): boolean {
    this.sidebarService.toggle(true, 'menu-sidebar');
    this.layoutService.changeLayoutSize();
    return false;
  }

  navigateHome() {
    this.menuService.navigateHome();
    return false;
  }
}
