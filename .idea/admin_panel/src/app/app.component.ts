import {Component}from '@angular/core';
import {Response}from '@angular/http';
import {Http, Headers} from '@angular/http';
import {User}from './user';
import {Injectable} from '@angular/core';
import {HttpService} from './httpservice';

@Component({
  selector: 'my-app',
  template: `<ul><li *ngFor="let user of users">{{user?.login}} <input type="button" value="{{user?.rel}}" (click)="send(user)"/></li></ul>`,
  providers: [HttpService]
})

@Injectable()
export class AppComponent {
    users: User[] = undefined;
    resp: any[] = undefined;

    constructor(private httpservice: HttpService){}

    ngOnInit(){
        this.httpservice.getData().subscribe((data:Response) => {
            this.users = new Array<User>();
            this.resp = data.json().users;
            for(let element of this.resp){
                let u = new User();
                u.login = element.login;
                u.userId = element.userId;
                u.rel = element.links[0].rel
                u.href = element.links[0].href;
                this.users.push(u);
            }
        });
    }

    send(user: User){
        if(user.rel == "add"){
            this.httpservice.postData(user).subscribe(() => {
                this.ngOnInit();
            });
        } else {
            this.httpservice.deleteData(user).subscribe(() => {
                this.ngOnInit();
            });
        }
    }

}