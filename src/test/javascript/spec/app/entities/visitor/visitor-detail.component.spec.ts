/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BigDataForLifeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VisitorDetailComponent } from '../../../../../../main/webapp/app/entities/visitor/visitor-detail.component';
import { VisitorService } from '../../../../../../main/webapp/app/entities/visitor/visitor.service';
import { Visitor } from '../../../../../../main/webapp/app/entities/visitor/visitor.model';

describe('Component Tests', () => {

    describe('Visitor Management Detail Component', () => {
        let comp: VisitorDetailComponent;
        let fixture: ComponentFixture<VisitorDetailComponent>;
        let service: VisitorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BigDataForLifeTestModule],
                declarations: [VisitorDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VisitorService,
                    JhiEventManager
                ]
            }).overrideTemplate(VisitorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VisitorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VisitorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Visitor(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.visitor).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
