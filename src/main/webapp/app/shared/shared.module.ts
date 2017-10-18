import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    BigDataForLifeSharedLibsModule,
    BigDataForLifeSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    Principal,
    JhiTrackerService,
    HasAnyAuthorityDirective,
    JhiSocialComponent,
    SocialService,
    JhiLoginModalComponent
} from './';

import { MyRestaurantChartModule } from './chart/chart.module';
import { ChartComponent } from './chart/chart.component';

@NgModule({
    imports: [
        BigDataForLifeSharedLibsModule,
        BigDataForLifeSharedCommonModule,
        MyRestaurantChartModule
    ],
    declarations: [
        JhiSocialComponent,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        JhiTrackerService,
        AuthServerProvider,
        SocialService,
        UserService,
        DatePipe
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        BigDataForLifeSharedCommonModule,
        JhiSocialComponent,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe,
        ChartComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class BigDataForLifeSharedModule {}
