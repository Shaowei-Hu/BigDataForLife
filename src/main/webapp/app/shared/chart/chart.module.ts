import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ChartsModule } from 'ng2-charts';
import { ChartComponent } from './chart.component';

@NgModule({
    imports: [
        ChartsModule
    ],
    declarations: [
        ChartComponent
    ],
    entryComponents: [
        ChartComponent
    ],
    exports: [
        ChartComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyRestaurantChartModule {}
