import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Visitor } from './visitor.model';
import { VisitorPopupService } from './visitor-popup.service';
import { VisitorService } from './visitor.service';

@Component({
    selector: 'jhi-visitor-delete-dialog',
    templateUrl: './visitor-delete-dialog.component.html'
})
export class VisitorDeleteDialogComponent {

    visitor: Visitor;

    constructor(
        private visitorService: VisitorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.visitorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'visitorListModification',
                content: 'Deleted an visitor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-visitor-delete-popup',
    template: ''
})
export class VisitorDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private visitorPopupService: VisitorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.visitorPopupService
                .open(VisitorDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
