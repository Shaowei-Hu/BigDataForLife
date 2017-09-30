import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BigDataForLifeSharedModule } from '../shared';

import { CITY_ROUTE, CityComponent } from './';

@NgModule({
    imports: [
        BigDataForLifeSharedModule,
        RouterModule.forRoot([ CITY_ROUTE ], { useHash: true })
    ],
    declarations: [
        CityComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BigDataForLifeCityModule {}
