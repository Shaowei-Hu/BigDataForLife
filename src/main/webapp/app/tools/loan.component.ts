import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../shared';

@Component({
    selector: 'jhi-loan',
    templateUrl: './loan.component.html',
    styleUrls: [
        'loan.scss'
    ]

})
export class LoanComponent implements OnInit {

    principalG: number;
    monthes: number;
    rate: number;
    interestG: number;
    monthlyPayment: number;
    amortization: any[];
    Math: any;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private sanitizer: DomSanitizer,
    ) {
    }

    ngOnInit() {
        this.principalG = 0;
        this.monthes = 0;
        this.rate = 0;
        this.interestG = 0;
        this.monthlyPayment = 0;
        this.Math = Math;
    }

    calculate() {
        this.getMonthlyPayment();
        this.getAmortization();
    }

    getMonthlyPayment() {
        const monthlyRate = this.rate / 100 / 12;
        this.monthlyPayment = this.principalG * monthlyRate / (1 - (Math.pow(1 / (1 + monthlyRate), this.monthes)));
    }

    getAmortization() {
        const monthlyPayment = this.monthlyPayment;
        const monthlyRate = this.rate / 100 / 12;
        let balance = this.principalG;
        const amortization = [];
        for (let m = 0; m < this.monthes; m ++) {
            const interestM = balance * monthlyRate;
            const principalM = monthlyPayment - interestM;
            balance = balance - principalM;
            this.interestG += interestM;
            amortization.push({principalM, interestM, balance});
        }
        this.amortization = amortization;
    }

    // getAmortizationByYear() {
    //     const monthlyPayment = this.monthlyPayment;
    //     const monthlyRate = this.rate / 100 / 12;
    //     let balance = this.principalG;
    //     const amortization = [];
    //     for (let y = 0; y < this.years; y ++) {
    //         let interestY = 0;
    //         let principalY = 0;
    //         for (let m = 0; m < 12; m ++) {
    //             const interestM = balance * monthlyRate;
    //             const principalM = monthlyPayment - interestM;
    //             interestY = interestY + interestM;
    //             principalY = principalY + principalM;
    //             balance = balance - principalM;
    //         }
    //         amortization.push({principalY, interestY, balance});
    //     }
    //     this.amortization = amortization;
    // }

    getBarStyle(num: number) {
        return this.sanitizer.bypassSecurityTrustStyle('flex:' + num + ';-webkit-flex:' + num + ';');
    }

}
