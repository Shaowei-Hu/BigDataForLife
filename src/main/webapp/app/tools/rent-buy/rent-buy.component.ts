import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../../shared';
import { ChartComponent} from '../../shared/chart/chart.component';

@Component({
    selector: 'jhi-rent-buy',
    templateUrl: './rent-buy.component.html',
    styleUrls: [
        'rent-buy.scss'
    ]

})
export class RentOrBuyComponent implements OnInit {

    name: string;
    telephone: string;
    email: string;
    taxRate: string;
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

    year: number; // simulation years

    accepte: boolean;

    principalG: number;
    monthes: number;
    rate: number;
    interestG: number;
    monthlyPayment: number;
    amortization: any[];

    investmentCapital: number;

    axe: number[];
    taxMICROFONCIER: number[];
    taxFONCIERREEL: number[];
    taxMICROBIC: number[];
    taxLMNPBICREEL: number[];

    rentalIncomesMICROFONCIER: number[];
    rentalIncomesFONCIERREEL: number[];
    rentalIncomesMICROBIC: number[];
    rentalIncomesLMNPBICREEL: number[];
    propertyIncomes: number[];

    pureIncomesMICROFONCIER: number[];
    pureIncomesFONCIERREEL: number[];
    pureIncomesMICROBIC: number[];
    pureIncomesLMNPBICREEL: number[];

    taxDataSet: any[];
    incomeDataSet: any[];
    pureIncomeDataSet: any[];

    constructor(
        private eventManager: JhiEventManager,
        private sanitizer: DomSanitizer,
    ) {
    }

    ngOnInit() {
        this.accepte = true;
        this.year = 20;

        this.taxRate = '0.3';
        this.estateCapital = 240000;
        this.principalG = 150000;
        this.monthes = 230;
        this.rent = 800;
        this.rentWithFee = 880;
        this.renovationCosts = 500;
        this.otherCosts = 300;
        this.month = 10.5;
        this.fee = 100;
        this.rate = 1.25;
        this.propertyTax = 1400;
        this.prediction = 0.1;

        this.investmentCapital = 0;
        this.axe = [];
        this.taxFONCIERREEL = [];
        this.taxLMNPBICREEL = [];
        this.taxMICROBIC = [];
        this.taxMICROFONCIER = [];

        this.rentalIncomesFONCIERREEL = [];
        this.rentalIncomesLMNPBICREEL = [];
        this.rentalIncomesMICROBIC = [];
        this.rentalIncomesMICROFONCIER = [];
        this.propertyIncomes = [];

        this.pureIncomesFONCIERREEL = [];
        this.pureIncomesLMNPBICREEL = [];
        this.pureIncomesMICROBIC = [];
        this.pureIncomesMICROFONCIER = [];

        this.taxDataSet = [];
        this.incomeDataSet = [];
        this.pureIncomeDataSet = [];
    }

    calculate() {
        this.axe = [];
        this.taxFONCIERREEL = [];
        this.taxLMNPBICREEL = [];
        this.taxMICROBIC = [];
        this.taxMICROFONCIER = [];

        this.taxDataSet = [];
        this.incomeDataSet = [];
        this.pureIncomeDataSet = [];

        this.getInvestmentCapital();
        this.getAxe();
        this.getMonthlyPayment();
        this.getAmortizationByYear();
        this.MICROFONCIER();
        this.FONCIERREEL();
        this.MICROBIC();
        this.LMNPBICREEL();

        this.getRentalIncome();
        this.getPropertyIncome();
        this.getPureIncome();

        this.taxDataSet = [{data: this.taxMICROFONCIER, label: 'MICROFONCIER'},
                        {data: this.taxFONCIERREEL, label: 'FONCIERREEL'},
                        {data: this.taxMICROBIC, label: 'MICROBIC'},
                        {data: this.taxLMNPBICREEL, label: 'LMNPBICREEL'}];

        this.incomeDataSet = [{data: this.rentalIncomesMICROFONCIER, label: 'rentalIncomes MICROFONCIER'},
                        {data: this.rentalIncomesFONCIERREEL, label: 'rentalIncomes FONCIERREEL'},
                        {data: this.rentalIncomesMICROBIC, label: 'rentalIncomes MICROBIC'},
                        {data: this.rentalIncomesLMNPBICREEL, label: 'rentalIncomes LMNPBICREEL'},
                        {data: this.propertyIncomes, label: 'property Incomes'}];

        this.pureIncomeDataSet = [{data: this.pureIncomesMICROFONCIER, label: 'pureIncomes MICROFONCIER'},
                        {data: this.pureIncomesFONCIERREEL, label: 'pureIncomes FONCIERREEL'},
                        {data: this.pureIncomesMICROBIC, label: 'pureIncomes MICROBIC'},
                        {data: this.pureIncomesLMNPBICREEL, label: 'pureIncomes LMNPBICREEL'}];
    }

    getAxe() {
        for (let i = 0; i < this.year; i ++) {
            this.axe.push(i);
        }
        console.log(this.axe);
    }

    getInvestmentCapital() {
        this.investmentCapital = this.estateCapital - this.principalG + this.renovationCosts;
    }

    MICROFONCIER() {
        for (let i = 0; i < this.year; i ++) {
            const tax = this.rent * this.month * (1 - 0.3) * (0.155 + Number(this.taxRate));
            this.taxMICROFONCIER.push(tax);
        }
    }

    FONCIERREEL() {
        let interest = 0;
        if (this.amortization.length > 0) {
            interest = this.amortization[0].interestY;
            console.log(interest);
        }
        let tax = (this.rentWithFee * this.month - this.renovationCosts - this.otherCosts - interest - this.propertyTax) * (0.155 + Number(this.taxRate));
        this.taxFONCIERREEL.push(tax);
        for (let i = 1; i < this.year; i ++) {
            if (i < this.amortization.length) {
                interest = this.amortization[i].interestY;
            } else {
                interest = 0
            }
            tax = (this.rentWithFee * this.month - this.otherCosts - interest) * (0.155 + Number(this.taxRate));
            this.taxFONCIERREEL.push(tax);
        }
        console.log(this.taxFONCIERREEL);
    }

    MICROBIC() {
        for (let i = 0; i < this.year; i ++) {
            const tax = this.rentWithFee * this.month * (1 - 0.5) * (0.155 + Number(this.taxRate));
            this.taxMICROBIC.push(tax);
        }
    }

    LMNPBICREEL() {
        for (let i = 0; i < this.year; i ++) {
            const tax = 0;
            const fee = 550;
            this.taxLMNPBICREEL.push(fee);
        }
    }

    getRentalIncome() {
        let income: number = this.rentWithFee * this.month - this.renovationCosts - this.otherCosts - this.monthlyPayment * 12 - this.propertyTax - this.fee * 12;
        this.rentalIncomesMICROFONCIER.push(income - this.taxMICROFONCIER[0]);
        this.rentalIncomesFONCIERREEL.push(income - this.taxFONCIERREEL[0]);
        this.rentalIncomesMICROBIC.push(income - this.taxMICROBIC[0]);
        this.rentalIncomesLMNPBICREEL.push(income - this.taxLMNPBICREEL[0]);
        for (let i = 1; i < this.year; i ++) {
            income = this.rentWithFee * this.month - this.otherCosts - this.monthlyPayment * 12 - this.propertyTax - this.fee * 12;
            this.rentalIncomesMICROFONCIER.push(income - this.taxMICROFONCIER[i]);
            this.rentalIncomesFONCIERREEL.push(income - this.taxFONCIERREEL[i]);
            this.rentalIncomesMICROBIC.push(income - this.taxMICROBIC[i]);
            this.rentalIncomesLMNPBICREEL.push(income - this.taxLMNPBICREEL[i]);
        }
    }

    getPropertyIncome() {// !
        const years = Math.floor(this.monthes / 12);
        for (let i = 0; i < this.year; i ++) {
            let income = 0;
            if (i < years) {
                income = this.amortization[i].principalY + this.estateCapital * 0.92 * this.prediction;
            } else {
                income = this.estateCapital * 0.92 * this.prediction;
            }
            this.propertyIncomes.push(income);
        }
    }

    getPureIncome() {
        for (let i = 0; i < this.year; i ++) {
            this.pureIncomesMICROFONCIER.push(this.propertyIncomes[i] + this.rentalIncomesMICROFONCIER[i]);
            this.pureIncomesFONCIERREEL.push(this.propertyIncomes[i]  + this.rentalIncomesFONCIERREEL[i]);
            this.pureIncomesMICROBIC.push(this.propertyIncomes[i]  + this.rentalIncomesMICROBIC[i]);
            this.pureIncomesLMNPBICREEL.push(this.propertyIncomes[i]  + this.rentalIncomesLMNPBICREEL[i]);
        }
    }

    getPureIncomeAccumulated(n: number, type: string) {
        let value = 0;
        if (type === 'MICROFONCIER') {
            for (let i = 0; i <= n && i < this.axe.length; i ++) {
                value += this.pureIncomesMICROFONCIER[i];
            }
        }
        if (type === 'FONCIERREEL') {
            for (let i = 0; i <= n && i < this.axe.length; i ++) {
                value += this.pureIncomesFONCIERREEL[i];
            }
        }
        if (type === 'MICROBIC') {
            for (let i = 0; i <= n && i < this.axe.length; i ++) {
                value += this.pureIncomesMICROBIC[i];
            }
        }
        if (type === 'LMNPBICREEL') {
            for (let i = 0; i <= n && i < this.axe.length; i ++) {
                value += this.pureIncomesLMNPBICREEL[i];
            }
        }
        return value;
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
}
