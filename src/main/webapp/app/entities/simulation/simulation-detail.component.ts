import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Simulation } from './simulation.model';
import { SimulationService } from './simulation.service';

@Component({
    selector: 'jhi-simulation-detail',
    templateUrl: './simulation-detail.component.html'
})
export class SimulationDetailComponent implements OnInit, OnDestroy {

    simulation: Simulation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private simulationService: SimulationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSimulations();
    }

    load(id) {
        this.simulationService.find(id).subscribe((simulation) => {
            this.simulation = simulation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSimulations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'simulationListModification',
            (response) => this.load(this.simulation.id)
        );
    }
}
