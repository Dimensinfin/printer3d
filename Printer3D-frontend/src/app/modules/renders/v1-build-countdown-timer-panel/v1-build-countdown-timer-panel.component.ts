// - CORE
import { Component } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Subscription } from "rxjs";
import { timer } from 'rxjs';
// - DOMAIN
import { V3MachineRenderComponent } from '../v3-machine-render/v3-machine-render.component';

@Component({
    selector: 'v1-build-countdown-timer',
    templateUrl: './v1-build-countdown-timer-panel.component.html',
    styleUrls: ['./v1-build-countdown-timer-panel.component.scss']
})
export class V1BuildCountdownTimerPanelComponent implements OnDestroy {
    @Input() parent: V3MachineRenderComponent
    public show: boolean = false
    public hours: number = 0
    public minutes: number = 0
    private duration: number = 0
    private timerSubscription: Subscription;
    private lastMinute: boolean = false
    private timerCompleted: boolean = false

    public ngOnDestroy(): void {
        if (null != this.timerSubscription) this.timerSubscription.unsubscribe();
    }

    public isLastMinute(): boolean {
        return this.lastMinute
    }
    public isCompleted(): boolean {
        return this.timerCompleted
    }

    // - V I E W   I N T E R A C T I O N
    /**
     * Interaction function to start the timer count down.
     * @param durationInSeconds the duration of the timing.
     */
    public activate(): void {
        console.log('>[V1BuildCountdownTimerPanelComponent.activate]> Timer duration: ' + this.duration);
        this.timerCompleted = false
        if (this.duration > 0) {
            const timer$ = timer(200, 1000);
            this.timerSubscription = timer$.subscribe(elapsed => {
                // Calculate the number of secods left.
                let left = this.duration - elapsed;
                if (left < 60) this.lastMinute = true
                else this.lastMinute = false
                // Convert to hours/minutes.
                this.hours = Math.floor(left / 3600);
                this.minutes = Math.floor((left - this.hours * 3600) / 60)
                if (left < 1) this.completeTimer();
            })
        } else this.completeTimer();
    }
    /**
     * Stops the timer by disconnecting the subscription.
     */
    public deactivate(): void {
        console.log('>[V1BuildCountdownTimerPanelComponent.deactivate]> Timer deactivated');
        if (null != this.timerSubscription) this.timerSubscription.unsubscribe();
    }
    public setTime(newTime: number): void {
        this.setTimer(newTime)
    }
    private completeTimer(): void {
        console.log('>[V1BuildCountdownTimerPanelComponent.completeTimer]> Timer completed');
        this.parent.completeTime();
        this.timerCompleted = true
        if (null != this.timerSubscription) this.timerSubscription.unsubscribe();
    }
    /**
     * Set the time to the number of seconds specified. The input os expected to be in seconds.
     * @param durationInSeconds the value of the countdown timer in seconds
     */
    private setTimer(durationInSeconds: number): void {
        console.log('>[V1BuildCountdownTimerPanelComponent.setTimer]> Duration: ' + durationInSeconds);
        this.duration = durationInSeconds;
        this.hours = Math.floor(durationInSeconds / 3600);
        console.log('>[V1BuildCountdownTimerPanelComponent.setTimer]> Hours: ' + this.hours);
        this.minutes = Math.floor((durationInSeconds - this.hours * 3600) / 60)
        console.log('>[V1BuildCountdownTimerPanelComponent.setTimer]> Minutes: ' + this.minutes);
    }
}
