import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BigDataForLifeSharedModule } from '../../shared';

import { RENT_BUY_ROUTE, RentOrBuyComponent } from './';

@NgModule({
    imports: [
        BigDataForLifeSharedModule,
        RouterModule.forRoot([ RENT_BUY_ROUTE ], { useHash: true })
    ],
    declarations: [
        RentOrBuyComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BigDataForLifeRentOrBuyModule {}
