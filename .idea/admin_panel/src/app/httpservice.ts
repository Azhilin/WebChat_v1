import{Response}from'@angular/http';
import {Http, Headers, RequestOptions}from '@angular/http';
import {Injectable}from '@angular/core';
import {User}from './user';


@Injectable()
export class HttpService{

constructor(private http:Http){}

getData(){
return this.http.get('http://localhost:8080/users');
}

postData(user: User){
    let jsonAnswer = {};
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    jsonAnswer["login"] = user.login;
    return this.http.post(user.href, JSON.stringify(jsonAnswer), options);
}

deleteData(user: User){
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this.http.delete(user.href, options);
}
}