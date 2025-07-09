import { Injectable } from '@angular/core';

export interface LogItem {
  message: string;
  timestamp: Date;
  elapsedTime: number;
  status: 'success' | 'failure' | 'info';
  source?: string;
  data?: any;
}

const STORAGE_KEY = 'app.logs';

@Injectable({
  providedIn: 'root',
})
export class LogService {
  private messages: LogItem[] = [];

  constructor() {
    this.loadFromStorage();

    // Sync between tabs
    window.addEventListener('storage', (event) => {
      if (event.key === STORAGE_KEY) {
        this.loadFromStorage();
      }
    });
  }

  get logs(): LogItem[] {
    return [...this.messages];
  }

  add(message: LogItem) {
    this.messages.push(message);
    this.saveToStorage();
  }

  clear() {
    this.messages = [];
    localStorage.removeItem(STORAGE_KEY);
  }

  private loadFromStorage() {
    try {
      const raw = localStorage.getItem(STORAGE_KEY);
      if (raw) {
        this.messages = JSON.parse(raw).map((log: any) => ({
          ...log,
          timestamp: new Date(log.timestamp),
        }));
      } else {
        this.messages = [];
      }
    } catch {
      this.messages = [];
    }
  }

  private saveToStorage() {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(this.messages));
  }
}
