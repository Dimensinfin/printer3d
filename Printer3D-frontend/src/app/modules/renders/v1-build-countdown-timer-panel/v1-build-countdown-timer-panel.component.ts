// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
import { Observable } from "rxjs";
// import { ISubscription } from 'rxjs/Subscription';
import { Subscription } from "rxjs";
// import "rxjs/add/observable/timer";
// import "rxjs/add/operator/finally";
// import "rxjs/add/operator/takeUntil";
// import "rxjs/add/operator/map"; import { BehaviorSubject } from 'rxjs';
import { timer } from 'rxjs';
import { NEVER } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { map } from 'rxjs/operators';
import { tap } from 'rxjs/operators';
import { takeWhile } from 'rxjs/operators';
import { startWith } from 'rxjs/operators';
import { take } from 'rxjs/operators';
import { V2MachineRenderComponent } from '../v2-machine-render/v2-machine-render.component';

const K = 1000;
const INTERVAL = K;
const toMinutes = (ms: number) =>
    Math.floor(ms / K / 60);
const toSeconds = (ms: number) =>
    Math.floor(ms / K) % 60;

@Component({
    selector: 'v1-build-countdown-timer-panel',
    templateUrl: './v1-build-countdown-timer-panel.component.html',
    styleUrls: ['./v1-build-countdown-timer-panel.component.scss']
})
export class V1BuildCountdownTimerPanelComponent implements OnInit {
    @Input() parent: V2MachineRenderComponent;
    @Input() time: number;
    public minutes: number = 10;
    public seconds: number = 0;
    private duration: number = 100;
    private timerSubscription: Subscription;

    public ngOnInit(): void {
        console.log('>[V1BuildCountdownTimerPanelComponent.ngOnInit]')
        this.minutes = toMinutes(this.time * 60 * K);
        this.seconds = toSeconds(this.time * 6 * K);
        // If the parent is available report to it that we have benn loaded.
        if (null != this.parent)
            this.activate(this.parent.getRemainingTime());
    }

    // - V I E W   I N T E R A C T I O N
    /**
     * Interaction function to start the timer count down.
     * @param durationInSeconds the duration of the timing.
     */
    public activate(durationInSeconds: number): void {
        if (null != durationInSeconds) {
            this.duration = durationInSeconds;
            const timer$ = timer(0, 1000);
            this.timerSubscription = timer$.subscribe(elapsed => {
                // Calculate the number of secods left.
                let left = this.duration - elapsed;
                // Convert to minutes/seconds.
                this.minutes = toMinutes(left * K);
                this.seconds = toSeconds(left * K);
                if (left < 1) this.completeTimer();
            });
        } else {
            // The can be a restart of the previous timer.
            if (this.duration > 0) this.activate(this.duration);
        }
    }
    /**
     * Stops the timer by disconnecting the subscription.
     */
    public deactivate(): void {
        console.log('>[SessionTimerComponent.deactivate]> Timer deactivated');
        if (null != this.timerSubscription) this.timerSubscription.unsubscribe();
    }
    private completeTimer(): void {
        console.log('>[SessionTimerComponent.completeTimer]> Timer completed');
        if (null != this.timerSubscription) this.timerSubscription.unsubscribe();
    }
}
