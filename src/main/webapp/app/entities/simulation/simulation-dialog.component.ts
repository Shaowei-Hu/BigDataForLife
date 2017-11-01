import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Simulation } from './simulation.model';
import { SimulationPopupService } from './simulation-popup.service';
import { SimulationService } from './simulation.service';

@Component({
    selector: 'jhi-simulation-dialog',
    templateUrl: './simulation-dialog.component.html'
})
export class SimulationDialogComponent implements OnInit {

    simulation: Simulation;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private simulationService: SimulationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.simulation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.simulationService.update(this.simulation));
        } else {
            this.subscribeToSaveResponse(
                this.simulationService.create(this.simulation));
        }
    }

    private subscribeToSaveResponse(result: Observable<Simulation>) {
        result.subscribe((res: Simulation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Simulation) {
        this.eventManager.broadcast({ name: 'simulationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-simulation-popup',
    template: ''
})
export class SimulationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private simulationPopupService: SimulationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.simulationPopupService
                    .open(SimulationDialogComponent as Component, params['id']);
            } else {
                this.simulationPopupService
                    .open(SimulationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
