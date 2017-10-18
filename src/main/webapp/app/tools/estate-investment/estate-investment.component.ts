import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../../shared';
import { ChartComponent} from '../../shared/chart/chart.component';

@Component({
    selector: 'jhi-estate-investment',
    templateUrl: './estate-investment.component.html',
    styleUrls: [
        'estate-investment.scss'
    ]

})
export class EstateInvestmentComponent implements OnInit {

    name: string;
    telephone: string;
    email: string;
    taxRate: number;
    situation: string;
    intention: string;

    estateCapital: number;
    rent: number;
    fee: number;
    rentWithFee: number;
    month: number;
    propertyTax: number;
    renovationCosts: number;
    otherCosts: number;
    prediction: number;

    year: number;

    accepte: boolean;

    principalG: number;
    monthes: number;
    rate: number;
    interestG: number;
    monthlyPayment: number;
    amortization: any[];

    axe: number[];
    taxMICROFONCIER: number[];
    taxFONCIERREEL: number[];
    taxMICROBIC: number[];
    taxLMNPBICREEL: number[];

    dataSet: any[];

    constructor(
        private eventManager: JhiEventManager,
        private sanitizer: DomSanitizer,
    ) {
    }

    ngOnInit() {
        this.accepte = true;
        this.year = 20;

        // this.taxRate = 0.3;
        // this.estateCapital = 240000;
        // this.principalG = 150000;
        // this.monthes = 230;
        // this.rent = 800;
        // this.rentWithFee = 880;
        // this.renovationCosts = 500;
        // this.otherCosts = 300;
        // this.month = 10.5;
        // this.fee = 100;
        // this.rate = 1.25;
        // this.propertyTax = 1400;

        this.axe = [];
        this.taxFONCIERREEL = [];
        this.taxLMNPBICREEL = [];
        this.taxMICROBIC = [];
        this.taxMICROFONCIER = [];

        this.dataSet = [];
    }

    calculate() {
        this.getAxe();
        this.getMonthlyPayment();
        this.getAmortizationByYear();
        this.MICROFONCIER();
        this.FONCIERREEL();
        this.MICROBIC();
        this.LMNPBICREEL();

        this.dataSet = [this.taxMICROFONCIER, this.taxFONCIERREEL, this.taxMICROBIC, this.taxLMNPBICREEL];
    }

    getAxe() {
        for (let i = 0; i < this.year; i ++) {
            this.axe.push(i);
        }
        console.log(this.axe);
    }

    MICROFONCIER() {
        for (let i = 0; i < this.year; i ++) {
            const tax = this.rent * this.month * (1 - 0.3) * (0.155 + this.taxRate);
            this.taxMICROFONCIER.push(tax);
        }
    }

    FONCIERREEL() {
        let interest = 0;
        if (this.amortization.length > 0) {
            interest = this.amortization[0].interestY;
            console.log(interest);
        }
        let tax = (this.rentWithFee * this.month - this.renovationCosts - this.otherCosts - interest - this.propertyTax) * (0.155 + this.taxRate);
        this.taxFONCIERREEL.push(tax);
        for (let i = 1; i < this.year; i ++) {
            if (i < this.amortization.length) {
                interest = this.amortization[i].interestY;
            } else {
                interest = 0
            }
            tax = (this.rentWithFee * this.month - this.otherCosts - interest) * (0.155 + this.taxRate);
            this.taxFONCIERREEL.push(tax);
        }
        console.log(this.taxFONCIERREEL);
    }

    MICROBIC() {
        for (let i = 0; i < this.year; i ++) {
            const tax = this.rentWithFee * this.month * (1 - 0.5) * (0.155 + this.taxRate);
            this.taxMICROBIC.push(tax);
        }
    }

    LMNPBICREEL() {
        for (let i = 0; i < this.year; i ++) {
            const tax = 0;
            this.taxLMNPBICREEL.push(tax);
        }
    }

    getMonthlyPayment() {
        const monthlyRate = this.rate / 100 / 12;
        this.monthlyPayment = this.principalG * monthlyRate / (1 - (Math.pow(1 / (1 + monthlyRate), this.monthes)));
    }

    getAmortizationByYear() {
    const monthlyPayment = this.monthlyPayment;
    const monthlyRate = this.rate / 100 / 12;
    let balance = this.principalG;
    const amortization = [];
    const years = Math.floor(this.monthes / 12);
    for (let y = 0; y < years; y ++) {
        let interestY = 0;
        let principalY = 0;
        for (let m = 0; m < 12; m ++) {
            const interestM = balance * monthlyRate;
            const principalM = monthlyPayment - interestM;
            interestY = interestY + interestM;
            principalY = principalY + principalM;
            balance = balance - principalM;
        }
        amortization.push({principalY, interestY, balance});
    }
    this.amortization = amortization;
    console.log(this.amortization);
    }

    getBarStyle(num: number) {
        return this.sanitizer.bypassSecurityTrustStyle('flex:' + num + ';-webkit-flex:' + num + ';');
    }
}
