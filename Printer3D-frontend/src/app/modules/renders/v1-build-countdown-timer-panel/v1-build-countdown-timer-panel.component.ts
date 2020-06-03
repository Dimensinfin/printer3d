// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
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
export class V1BuildCountdownTimerPanelComponent implements OnInit, OnDestroy {
    @Input() parent: V2MachineRenderComponent;
    @Input() time: number;
    public minutes: number;
    public seconds: number;
    private duration: number;
    private timerSubscription: Subscription;

    public ngOnInit(): void {
        console.log('>[V1BuildCountdownTimerPanelComponent.ngOnInit]')
        this.setTimer(this.time);
        if (null != this.parent) if (this.parent.isAutostart()) this.activate(this.time);
    }
    public ngOnDestroy(): void {
        if (null != this.timerSubscription) this.timerSubscription.unsubscribe();
    }

    // - V I E W   I N T E R A C T I O N
    /**
     * Interaction function to start the timer count down.
     * @param durationInSeconds the duration of the timing.
     */
    public activate(durationInSeconds?: number): void {
        if (null != durationInSeconds) {
            this.duration = durationInSeconds;
            const timer$ = timer(1000, 1000);
            this.timerSubscription = timer$.subscribe(elapsed => {
                // Calculate the number of secods left.
                let left = this.duration - elapsed;
                // Convert to minutes/seconds.
                this.minutes = toMinutes(left * K);
                this.seconds = toSeconds(left * K);
                if (left < 1) this.completeTimer();
            });
        } else {
            // This can be a restart of the previous timer.
            console.log('>[V1BuildCountdownTimerPanelComponent.activate]> Restart: ' + this.duration);
            if (this.duration > 0) this.activate(this.duration);
        }
    }
    /**
     * Stops the timer by disconnecting the subscription.
     */
    public deactivate(): void {
        console.log('>[V1BuildCountdownTimerPanelComponent.deactivate]> Timer deactivated');
        if (null != this.timerSubscription) this.timerSubscription.unsubscribe();
    }
    private completeTimer(): void {
        console.log('>[V1BuildCountdownTimerPanelComponent.completeTimer]> Timer completed');
        if (null != this.timerSubscription) this.timerSubscription.unsubscribe();
    }
    private setTimer(durationInSeconds: number): void {
        console.log('>[V1BuildCountdownTimerPanelComponent.setTimer]> Duration: ' + durationInSeconds);
        this.duration = durationInSeconds;
        this.minutes = toMinutes(durationInSeconds * K);
        console.log('>[V1BuildCountdownTimerPanelComponent.setTimer]> Minutes: ' + this.minutes);
        this.seconds = toSeconds(durationInSeconds * K);
        console.log('>[V1BuildCountdownTimerPanelComponent.setTimer]> Seconds: ' + this.seconds);
    }
}
