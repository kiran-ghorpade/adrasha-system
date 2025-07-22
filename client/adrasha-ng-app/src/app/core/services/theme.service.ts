import { Injectable, computed, effect, inject, signal } from '@angular/core';
import { LocalStorageService } from '@shared/services';

export interface AppTheme {
  name: 'light' | 'dark' | 'system';
  icon: string;
}

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private appTheme = signal<'light' | 'dark' | 'system'>('system');
  private storageService = inject(LocalStorageService);

  private themes: AppTheme[] = [
    { name: 'light', icon: 'light_mode' },
    { name: 'dark', icon: 'dark_mode' },
    { name: 'system', icon: 'desktop_windows' },
  ];

  selectedTheme = computed(() =>
    this.themes.find((t) => t.name === this.appTheme())
  );

  getThemes() {
    return this.themes;
  }

  setTheme(theme: 'light' | 'dark' | 'system') {
    this.appTheme.set(theme);
    this.storageService.set('theme', theme);
  }

  constructor() {
    effect(() => {
      const appTheme = this.appTheme();
      const lastTheme = this.storageService.get('theme');
      const colorScheme = appTheme === 'system' ? 'light dark' : appTheme;
      document.documentElement.style.setProperty('color-scheme', lastTheme ?? colorScheme);
    });
  }
}
