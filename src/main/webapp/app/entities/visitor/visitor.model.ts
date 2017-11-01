import { BaseEntity } from './../../shared';

export class Visitor implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public ip?: string,
        public browser?: string,
        public information?: string,
        public arriveDate?: any,
        public leaveDate?: any,
    ) {
    }
}
