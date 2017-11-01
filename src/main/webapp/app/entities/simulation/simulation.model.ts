import { BaseEntity } from './../../shared';

export class Simulation implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public email?: string,
        public telephone?: string,
        public taxLevel?: string,
        public condition?: string,
        public intention?: string,
        public ip?: string,
        public information?: string,
        public date?: any,
    ) {
    }
}
