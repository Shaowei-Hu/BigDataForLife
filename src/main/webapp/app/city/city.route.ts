import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { CityComponent } from './';

export const CITY_ROUTE: Route = {
    path: 'city',
    component: CityComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
};
