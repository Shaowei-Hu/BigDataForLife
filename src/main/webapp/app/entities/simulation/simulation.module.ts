import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BigDataForLifeSharedModule } from '../../shared';
import {
    SimulationService,
    SimulationPopupService,
    SimulationComponent,
    SimulationDetailComponent,
    SimulationDialogComponent,
    SimulationPopupComponent,
    SimulationDeletePopupComponent,
    SimulationDeleteDialogComponent,
    simulationRoute,
    simulationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...simulationRoute,
    ...simulationPopupRoute,
];

@NgModule({
    imports: [
        BigDataForLifeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SimulationComponent,
        SimulationDetailComponent,
        SimulationDialogComponent,
        SimulationDeleteDialogComponent,
        SimulationPopupComponent,
        SimulationDeletePopupComponent,
    ],
    entryComponents: [
        SimulationComponent,
        SimulationDialogComponent,
        SimulationPopupComponent,
        SimulationDeleteDialogComponent,
        SimulationDeletePopupComponent,
    ],
    providers: [
        SimulationService,
        SimulationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BigDataForLifeSimulationModule {}
