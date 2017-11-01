import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BigDataForLifeSharedModule } from '../../shared';
import {
    VisitorService,
    VisitorPopupService,
    VisitorComponent,
    VisitorDetailComponent,
    VisitorDialogComponent,
    VisitorPopupComponent,
    VisitorDeletePopupComponent,
    VisitorDeleteDialogComponent,
    visitorRoute,
    visitorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...visitorRoute,
    ...visitorPopupRoute,
];

@NgModule({
    imports: [
        BigDataForLifeSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VisitorComponent,
        VisitorDetailComponent,
        VisitorDialogComponent,
        VisitorDeleteDialogComponent,
        VisitorPopupComponent,
        VisitorDeletePopupComponent,
    ],
    entryComponents: [
        VisitorComponent,
        VisitorDialogComponent,
        VisitorPopupComponent,
        VisitorDeleteDialogComponent,
        VisitorDeletePopupComponent,
    ],
    providers: [
        VisitorService,
        VisitorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BigDataForLifeVisitorModule {}
