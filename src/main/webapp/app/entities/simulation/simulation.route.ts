import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SimulationComponent } from './simulation.component';
import { SimulationDetailComponent } from './simulation-detail.component';
import { SimulationPopupComponent } from './simulation-dialog.component';
import { SimulationDeletePopupComponent } from './simulation-delete-dialog.component';

export const simulationRoute: Routes = [
    {
        path: 'simulation',
        component: SimulationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bigDataForLifeApp.simulation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'simulation/:id',
        component: SimulationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bigDataForLifeApp.simulation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const simulationPopupRoute: Routes = [
    {
        path: 'simulation-new',
        component: SimulationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bigDataForLifeApp.simulation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'simulation/:id/edit',
        component: SimulationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bigDataForLifeApp.simulation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'simulation/:id/delete',
        component: SimulationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bigDataForLifeApp.simulation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
