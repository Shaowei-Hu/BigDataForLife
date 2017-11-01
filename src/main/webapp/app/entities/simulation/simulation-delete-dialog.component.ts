import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Simulation } from './simulation.model';
import { SimulationPopupService } from './simulation-popup.service';
import { SimulationService } from './simulation.service';

@Component({
    selector: 'jhi-simulation-delete-dialog',
    templateUrl: './simulation-delete-dialog.component.html'
})
export class SimulationDeleteDialogComponent {

    simulation: Simulation;

    constructor(
        private simulationService: SimulationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.simulationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'simulationListModification',
                content: 'Deleted an simulation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-simulation-delete-popup',
    template: ''
})
export class SimulationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private simulationPopupService: SimulationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.simulationPopupService
                .open(SimulationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
