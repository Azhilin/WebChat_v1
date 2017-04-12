import {Component}from '@angular/core';
import {Response}from '@angular/http';
import {Http, Headers} from '@angular/http';
import {User}from './user';
import {Injectable} from '@angular/core';
import {HttpService} from './httpservice';

@Component({
  selector: 'my-app',
  template: `<ul><li *ngFor="let user of users">{{user.name}} <input type="button" value="{{user.rel}}"/></li></ul>`,
 providers: [HttpService]
})

@Injectable()
export class AppComponent {
    users: User[] = undefined;
    resp: string = undefined;

    constructor(private httpservice: HttpService){}

    ngOnInit(){
        this.httpservice.getData().subscribe((data:Response) => this.resp = data.text());
        console.log("resp : " + this.resp);
/**        for(let element of this.json){
            let u = new User();
            u.login = element.login;
            u.userId = element.userId;
            u.rel = element.links.rel
            u.href = element.links.href;
            this.users.push(u);
        }*/
    }

}