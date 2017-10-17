import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BigDataForLifeSharedModule } from '../shared';

import { LOAN_ROUTE, LoanComponent } from './';

@NgModule({
    imports: [
        BigDataForLifeSharedModule,
        RouterModule.forRoot([ LOAN_ROUTE ], { useHash: true })
    ],
    declarations: [
        LoanComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BigDataForLifeLoanModule {}
