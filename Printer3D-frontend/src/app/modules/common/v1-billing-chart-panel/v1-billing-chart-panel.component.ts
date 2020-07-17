// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
// - ROUTER
import { Router } from '@angular/router';
// - SERVICES
import { DockService } from '@app/services/dock.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { AppComponent } from '@app/app.component';
import { WeekData } from '@domain/WeekData.domain';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { BackendService } from '@app/services/backend.service';
import { WeekAmount } from '@domain/dto/WeekAmount.dto';
import { WeekAmountToWeekDataConverter } from '@domain/converter/WeekAmountToWeekData.converter';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';

const DEFAULT_NUMBER_OF_WEEKS: number = 4;

@Component({
    selector: 'v1-billing-chart-panel',
    templateUrl: './v1-billing-chart-panel.component.html',
    styleUrls: ['./v1-billing-chart-panel.component.scss']
})
export class V1BillingChartPanelComponent extends BackgroundEnabledComponent implements OnInit, Refreshable {
    public yscale: number = 10
    public billingChartData: WeekData[] = [];
    public colorScheme = {
        domain: ['blueviolet']
    };
    public yaxisTicks: any[] = [10]

    constructor(protected backendService: BackendService) { super() }

    public ngOnInit(): void {
        console.log(">[V1BillingChartPanelComponent.ngOnInit]");
        this.refresh();
        console.log("<[V1BillingChartPanelComponent.ngOnInit]");
    }

    public hasData(): boolean {
        if (this.billingChartData.length < 1) return false
        else return true
    }
    // - R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        this.clean()
        this.getWeekAmounts()
    }
    // - B A C K E N D
    private getWeekAmounts(): void {
        const weekDataConverter: WeekAmountToWeekDataConverter = new WeekAmountToWeekDataConverter()
        this.backendConnections.push(
            this.backendService.apiAccountingRequestAmountsPerWeek_v1(DEFAULT_NUMBER_OF_WEEKS, new ResponseTransformer()
                .setDescription('Do HTTP transformation to "WeekAmount" list.')
                .setTransformation((entrydata: any): WeekAmount[] => {
                    const weeks: WeekAmount[] = []
                    entrydata.forEach(element => {
                        weeks.push(new WeekAmount(element));
                    });
                    return weeks;
                }))
                .subscribe((weeks: WeekAmount[]) => {
                    // Transform the week amount data into the format suitable for the chart.
                    const charData: WeekData[] = []
                    for (const week of weeks) {
                        charData.push(weekDataConverter.convert(week))
                    }
                    this.yaxisTicks = this.generateTicksFromData(charData)
                    this.billingChartData = charData
                })
        );
    }
    private generateTicksFromData(data: WeekData[]): number[] {
        const ticks: number[] = []
        let max: number = 0
        let min: number = 9999
        for (const week of data) {
            if (week.value > max) max = week.value
            if (week.value < min) min = week.value
        }
        const tickSize = Math.floor(max / 4)
        let tick: number = 0
        while (tick < max) {
            tick += tickSize
            ticks.push(tick)
        }
        this.yscale = tick
        return ticks
    }
}
