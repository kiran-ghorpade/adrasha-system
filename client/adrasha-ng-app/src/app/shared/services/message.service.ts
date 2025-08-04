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
const LOGGING_STATE_KEY = 'app.loggingState'; // Key to sync logging state across tabs

@Injectable({
  providedIn: 'root',
})
export class LogService {
  private messageSubject = new BehaviorSubject<LogItem[]>([]);
  private loggingState: BehaviorSubject<boolean>;
  logs$ = this.messageSubject.asObservable();

  constructor(private storageService: LocalStorageService) {
    const initialLoggingState = this.getLoggingStateFromStorage();
    this.loggingState = new BehaviorSubject<boolean>(initialLoggingState);

    this.loadFromStorage();

    // Sync between tabs for loggingState
    window.addEventListener('storage', (event) => {
      if (event.key === LOGGING_STATE_KEY) {
        this.loggingState.next(JSON.parse(event.newValue ?? 'false'));
      }
      if (event.key === STORAGE_KEY) {
        this.loadFromStorage();
      }
    });
  }

  currentLoggingState() {
    return this.loggingState.asObservable();
  }

  // Toggle the logging state (start/stop logging)
  toggleLogging(): void {
    const newState = !this.loggingState.value;
    this.loggingState.next(newState);
    this.saveLoggingStateToStorage(newState); // Save the new state to localStorage
    console.log(`Logging is now ${newState ? 'enabled' : 'disabled'}`);
  }

  // Add a new log message only if logging is enabled
  add(message: LogItem): void {
    if (this.loggingState.value) {
      if (!message.timestamp) message.timestamp = new Date();

      const messageList = [...this.messageSubject.getValue(), message];
      const sortedList = this.sortLogsByTimestamp(messageList);

      this.messageSubject.next(sortedList);
      this.saveToStorage();
    } else {
      console.log('Logging is disabled. Message not added.');
    }
  }

  // Update an existing log message by index
  update(index: number, message: LogItem): void {
    const messageList = this.messageSubject.getValue();
    messageList[index] = message;

    const sortedList = this.sortLogsByTimestamp([...messageList]);

    this.messageSubject.next(sortedList);
    this.saveToStorage();
  }

  // Clear all logs
  clear(): void {
    this.messageSubject.next([]);
    localStorage.removeItem(STORAGE_KEY);
  }

  // Load logs from local storage
  private loadFromStorage(): void {
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

  // Sort logs by timestamp in ascending order
  private sortLogsByTimestamp(logs: LogItem[]): LogItem[] {
    return logs.sort((a, b) => a.timestamp.getTime() - b.timestamp.getTime());
  }

  // Save logs to local storage
  private saveToStorage(): void {
    const messageList = this.messageSubject.getValue();
    localStorage.setItem(STORAGE_KEY, JSON.stringify(messageList));
  }

  // Retrieve logging state from localStorage
  private getLoggingStateFromStorage(): boolean {
    const storedState = localStorage.getItem(LOGGING_STATE_KEY);
    return storedState ? JSON.parse(storedState) : true; // Default to true (enabled) if not found
  }

  // Save logging state to localStorage
  private saveLoggingStateToStorage(state: boolean): void {
    localStorage.setItem(LOGGING_STATE_KEY, JSON.stringify(state));
  }
}
