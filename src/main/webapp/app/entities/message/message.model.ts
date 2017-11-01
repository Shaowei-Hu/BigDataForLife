import { BaseEntity } from './../../shared';

export class Message implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public email?: string,
        public subject?: string,
        public message?: string,
        public ip?: string,
        public information?: string,
        public date?: any,
    ) {
    }
}
