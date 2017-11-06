import { Injectable } from '@angular/core';
import { Hero } from './hero';
import 'rxjs/add/operator/toPromise';
import {Http, Headers} from "@angular/http";
import {HEROES} from "./mock-heroes";



@Injectable()
export class HeroService {
  private heroesUrl = 'api/heroes';

  constructor(private  http: Http){}

	// getHeroes(): Promise<Hero[]> {
    // return Promise.resolve(HEROES);
	// }

  getHeroes(): Promise<Hero[]> {
    return this.http.get(this.heroesUrl)
      .toPromise()
      .then(response => response.json().data as Hero[])
      .catch(this.handleError)
  }

	private handleError(error: any): Promise<any>{
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

	getHero(id: number): Promise<Hero> {
		return this.getHeroes()
            .then(heroes => heroes.find(hero => hero.id === id));
	}
}
