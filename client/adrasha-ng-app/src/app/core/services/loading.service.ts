import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable({ providedIn: 'root' })
export class LoadingService {
  private loadingCount = 0;
  private loadingSubject = new BehaviorSubject<boolean>(false);
  readonly loading$ = this.loadingSubject.asObservable();

  start() {
    this.loadingCount++;
    this.updateLoadingState();
  }

  stop() {
    this.loadingCount = Math.max(this.loadingCount - 1, 0);
    this.updateLoadingState();
  }

  private updateLoadingState() {
    this.loadingSubject.next(this.loadingCount > 0);
  }
}
