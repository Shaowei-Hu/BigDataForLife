import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Visitor } from './visitor.model';
import { VisitorService } from './visitor.service';

@Component({
    selector: 'jhi-visitor-detail',
    templateUrl: './visitor-detail.component.html'
})
export class VisitorDetailComponent implements OnInit, OnDestroy {

    visitor: Visitor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private visitorService: VisitorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVisitors();
    }

    load(id) {
        this.visitorService.find(id).subscribe((visitor) => {
            this.visitor = visitor;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVisitors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'visitorListModification',
            (response) => this.load(this.visitor.id)
        );
    }
}
