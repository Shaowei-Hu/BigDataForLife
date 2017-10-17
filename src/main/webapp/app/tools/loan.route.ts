import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { LoanComponent } from './';

export const LOAN_ROUTE: Route = {
    path: 'loan',
    component: LoanComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
};
