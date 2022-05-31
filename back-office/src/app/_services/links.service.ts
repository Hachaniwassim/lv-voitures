
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as _ from 'lodash';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { LinksDTO } from '../models/dto/linksDTO';

@Injectable({
  providedIn: 'root'
})

/**
 * 
 * @author Tarchoun Abir
 * 
 */

export class LinksService {

/*********************************
 * 
 * ----------Api Backend---------
 * 
 *******************************/
  private base_url = environment.privateApi + "/liens";

  headers = new HttpHeaders().set('Content-Type', 'application/json');



/***************************************************
 * 
 * -------------- Handling Erros -----------------
 * 
 **************************************************/

  //http opttion
  httpOptions = {
    headers: new HttpHeaders({
      'content-type': 'application/json'

    })
  }
  //handel api  errors 
  handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      //a client-side or a neetwork error occurend .Handel it accordingly
      console.error('An Error occurend', error.error.message)

    }
    else {
      // the backend may returned an successfully response code 
      // the response body may contain clues as to what went wrong 
      console.error(`backend returned code ${error.status}, ` +
        `body was : ${error.error}`
      );
    }
    // return an observabel with a user-facing error message 
    return throwError('something bad happined , please try again later .');
  };
  
  constructor(private http: HttpClient) { }

/**************************
 * 
 * Get privacy by current Entreprise
 *
 */
  getLinksByEntreprise(): Observable<LinksDTO[]> {
    return this.http.get<LinksDTO[]>(this.base_url + '/list-liens/' + localStorage.getItem('idEntreprise')).pipe(retry(2), catchError(this.handleError));
  }

  
/**************************
 * 
 * Get privacy by Entreprise id
 *
 */
  getLinksByid(id: number): Observable<LinksDTO> {
    return this.http.get<LinksDTO>(this.base_url + '/' + id).pipe(retry(2), catchError(this.handleError));

  }

  
 /**************************
  * 
 * update privacy by Entreprise
 * 
 */
  updateLinksByEntreprise(request: any) {
    console.log('the request ====>',request)
    return this.http.post<LinksDTO>(`${this.base_url + '/post-liens' }`, request);
    
  }
  
  
}
