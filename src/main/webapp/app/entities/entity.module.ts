import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BigDataForLifeVisitorModule } from './visitor/visitor.module';
import { BigDataForLifeSimulationModule } from './simulation/simulation.module';
import { BigDataForLifeMessageModule } from './message/message.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BigDataForLifeVisitorModule,
        BigDataForLifeSimulationModule,
        BigDataForLifeMessageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BigDataForLifeEntityModule {}
