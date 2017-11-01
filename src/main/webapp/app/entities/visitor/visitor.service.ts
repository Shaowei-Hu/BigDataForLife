import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Visitor } from './visitor.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VisitorService {

    private resourceUrl = SERVER_API_URL + 'api/visitors';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/visitors';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(visitor: Visitor): Observable<Visitor> {
        const copy = this.convert(visitor);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(visitor: Visitor): Observable<Visitor> {
        const copy = this.convert(visitor);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Visitor> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.arriveDate = this.dateUtils
            .convertDateTimeFromServer(entity.arriveDate);
        entity.leaveDate = this.dateUtils
            .convertDateTimeFromServer(entity.leaveDate);
    }

    private convert(visitor: Visitor): Visitor {
        const copy: Visitor = Object.assign({}, visitor);

        copy.arriveDate = this.dateUtils.toDate(visitor.arriveDate);

        copy.leaveDate = this.dateUtils.toDate(visitor.leaveDate);
        return copy;
    }
}
