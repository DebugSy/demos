import { Injectable } from '@angular/core';
import { Http }       from '@angular/http';

import { Observable }     from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import { Hero }           from './hero';

@Injectable()
export class HeroSearchService {

  constructor(private http: Http) {}

  search(term: string): Observable<Hero[]> {
    return this.http
               .get(`api/heroes/?name=${term}`)
               .map(response => response.json().data as Hero[]);
  }
}


/*
Copyright 2017 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/