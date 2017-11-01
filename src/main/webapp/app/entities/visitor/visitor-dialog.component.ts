import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Visitor } from './visitor.model';
import { VisitorPopupService } from './visitor-popup.service';
import { VisitorService } from './visitor.service';

@Component({
    selector: 'jhi-visitor-dialog',
    templateUrl: './visitor-dialog.component.html'
})
export class VisitorDialogComponent implements OnInit {

    visitor: Visitor;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private visitorService: VisitorService,
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
        if (this.visitor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.visitorService.update(this.visitor));
        } else {
            this.subscribeToSaveResponse(
                this.visitorService.create(this.visitor));
        }
    }

    private subscribeToSaveResponse(result: Observable<Visitor>) {
        result.subscribe((res: Visitor) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Visitor) {
        this.eventManager.broadcast({ name: 'visitorListModification', content: 'OK'});
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
    selector: 'jhi-visitor-popup',
    template: ''
})
export class VisitorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private visitorPopupService: VisitorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.visitorPopupService
                    .open(VisitorDialogComponent as Component, params['id']);
            } else {
                this.visitorPopupService
                    .open(VisitorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
