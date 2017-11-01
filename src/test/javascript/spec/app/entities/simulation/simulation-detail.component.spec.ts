/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BigDataForLifeTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SimulationDetailComponent } from '../../../../../../main/webapp/app/entities/simulation/simulation-detail.component';
import { SimulationService } from '../../../../../../main/webapp/app/entities/simulation/simulation.service';
import { Simulation } from '../../../../../../main/webapp/app/entities/simulation/simulation.model';

describe('Component Tests', () => {

    describe('Simulation Management Detail Component', () => {
        let comp: SimulationDetailComponent;
        let fixture: ComponentFixture<SimulationDetailComponent>;
        let service: SimulationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BigDataForLifeTestModule],
                declarations: [SimulationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SimulationService,
                    JhiEventManager
                ]
            }).overrideTemplate(SimulationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SimulationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SimulationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Simulation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.simulation).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
