import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RentOrBuyComponent } from './';

export const RENT_BUY_ROUTE: Route = {
    path: 'rent-buy',
    component: RentOrBuyComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
};
