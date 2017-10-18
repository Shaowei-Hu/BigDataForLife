import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EstateInvestmentComponent } from './';

export const ESTATE_INVESTMENT_ROUTE: Route = {
    path: 'estate-investment',
    component: EstateInvestmentComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
};
