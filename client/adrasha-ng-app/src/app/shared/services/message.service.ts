import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { LocalStorageService } from './storage.service';

export interface LogItem {
  message: string;
  timestamp: Date;
  elapsedTime: number;
  status: 'success' | 'failure' | 'info';
  source: string;
  data?: any;
  isViewed?: boolean;
}

const STORAGE_KEY = 'app.logs';

@Injectable({
  providedIn: 'root',
})
export class LogService {
  private messageSubject = new BehaviorSubject<LogItem[]>([]);
  logs$ = this.messageSubject.asObservable();

  constructor(private storageService: LocalStorageService) {
    this.loadFromStorage();

    // Sync between tabs
    window.addEventListener('storage', (event) => {
      if (event.key === STORAGE_KEY) {
        this.loadFromStorage();
      }
    });
  }

  add(message: LogItem) {
    if (!message.timestamp) message.timestamp = new Date();

    const messageList = [...this.messageSubject.getValue(), message];
    const sortedList = this.sortLogsByTimestamp(messageList);

    this.messageSubject.next(sortedList);
    this.saveToStorage();
  }

  update(index: number, message: LogItem) {
    const messageList = this.messageSubject.getValue();
    messageList[index] = message;

    const sortedList = this.sortLogsByTimestamp([...messageList]);

    this.messageSubject.next(sortedList);
    this.saveToStorage();
  }

  clear() {
    this.messageSubject.next([]);
    localStorage.removeItem(STORAGE_KEY);
  }

  private loadFromStorage() {
    let messageList: LogItem[] = [];

    const raw = this.storageService.get(STORAGE_KEY);
    if (raw && Array.isArray(raw)) {
      messageList = raw.map((log: any) => ({
        ...log,
        timestamp: new Date(log.timestamp),
      }));

      messageList = this.sortLogsByTimestamp(messageList);
    }

    this.messageSubject.next(messageList);
  }

  private sortLogsByTimestamp(logs: LogItem[]): LogItem[] {
    return logs.sort((a, b) => a.timestamp.getTime() - b.timestamp.getTime());
  }

  private saveToStorage() {
    const messageList = this.messageSubject.getValue();
    localStorage.setItem(STORAGE_KEY, JSON.stringify(messageList));
  }
}
