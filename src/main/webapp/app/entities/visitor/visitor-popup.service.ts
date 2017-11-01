import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Visitor } from './visitor.model';
import { VisitorService } from './visitor.service';

@Injectable()
export class VisitorPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private visitorService: VisitorService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.visitorService.find(id).subscribe((visitor) => {
                    visitor.arriveDate = this.datePipe
                        .transform(visitor.arriveDate, 'yyyy-MM-ddTHH:mm:ss');
                    visitor.leaveDate = this.datePipe
                        .transform(visitor.leaveDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.visitorModalRef(component, visitor);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.visitorModalRef(component, new Visitor());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    visitorModalRef(component: Component, visitor: Visitor): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.visitor = visitor;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
