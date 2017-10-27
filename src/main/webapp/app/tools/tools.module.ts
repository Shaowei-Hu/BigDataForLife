import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BigDataForLifeLoanModule } from './loan/loan.module';
import { BigDataForLifeEstateInvestmentModule } from './estate-investment/estate-investment.module';
import { BigDataForLifeRentOrBuyModule } from './rent-buy/rent-buy.module';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BigDataForLifeLoanModule,
        BigDataForLifeEstateInvestmentModule,
        BigDataForLifeRentOrBuyModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BigDataForLifeToolsModule {}
