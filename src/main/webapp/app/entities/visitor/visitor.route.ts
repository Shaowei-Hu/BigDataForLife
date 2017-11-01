import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VisitorComponent } from './visitor.component';
import { VisitorDetailComponent } from './visitor-detail.component';
import { VisitorPopupComponent } from './visitor-dialog.component';
import { VisitorDeletePopupComponent } from './visitor-delete-dialog.component';

export const visitorRoute: Routes = [
    {
        path: 'visitor',
        component: VisitorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bigDataForLifeApp.visitor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'visitor/:id',
        component: VisitorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bigDataForLifeApp.visitor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const visitorPopupRoute: Routes = [
    {
        path: 'visitor-new',
        component: VisitorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bigDataForLifeApp.visitor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'visitor/:id/edit',
        component: VisitorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bigDataForLifeApp.visitor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'visitor/:id/delete',
        component: VisitorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bigDataForLifeApp.visitor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
