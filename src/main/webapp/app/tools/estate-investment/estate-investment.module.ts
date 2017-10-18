import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BigDataForLifeSharedModule } from '../../shared';

import { ESTATE_INVESTMENT_ROUTE, EstateInvestmentComponent } from './';

@NgModule({
    imports: [
        BigDataForLifeSharedModule,
        RouterModule.forRoot([ ESTATE_INVESTMENT_ROUTE ], { useHash: true })
    ],
    declarations: [
        EstateInvestmentComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BigDataForLifeEstateInvestmentModule {}
