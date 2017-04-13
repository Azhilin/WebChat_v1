import {Component}from '@angular/core';
import {Response}from '@angular/http';
import {Http, Headers} from '@angular/http';
import {User}from './user';
import {Injectable} from '@angular/core';
import {WebSock} from './websock';

@Component({
  selector: 'my-chat',
  template:
  `
    <table>
        <tr>
        <td>
            <form>
                <textarea>{{messages}}</textarea>
                <input type="text" [userMessage] = "uMessage"/>
                <input type="button" value="send" (click)="send()"/>
                <input type="button" value="broadcast" (click)="broadcast()"/>
                <input type="button" value="logout" (click)="disconnect()"/>
            </form>
        </td>
        <td>
            <div>
                <ul>
                    <li *ngFor="let item of items" (click)="changeUser(item)">
                        {{item}}
                    </li>
                </ul>
            </div>
        </td>
        </tr>
    </table>

  `
})

@Injectable()
export class AppComponent {
    flag: any = undefined;
    items: string[] = undefined;
    messages: string = undefined;
    uMessage: string = undefined;

    constructor(private websock: WebSock){}

    ngOnInit(){
        websock.getSocket().onmessage = this.onMessageHandler;
        websock.getSocket().onerror = this.onErrorHandler;
        websock.getSocket().onopen = this.onOpenHandler;
        websock.getSocket().onclose = this.onCloseHandler;
    }

    onOpenHandler(){
        this.registerUser();
        this.flag = setInterval( () => {this.sendList},2000);
    }

    onCloseHandler(param){
        clearInterval(this.flag);
        if(param.wasClean){
            console.log("socket was closed");
        } else {
            console.log("socket was closed because of error");
        }
    }

    onMessageHandler(param){
        let jsonMessage = JSON.parse(param.data);
        if(jsonMessage.auth != undefined && jsonMessage.auth == "yes"){
            console.log("authorisation successful");
        }
        if(jsonMessage.auth != undefined && jsonMessage.auth == "no"){
            console.log("authorisation is fail");
            window.location.href = "/";
        }
        if(jsonMessage.list != undefined){
            this.items = jsonMessage.list;
        }
        if(jsonMessage.name != undefined){
            let sender = jsonMessage.name;
            let message = jsonMessage.message;

            this.messages += sender + ":" + message + "\n";
        }
        if(jsonMessage.disconnect != undefined){
            window.location.href = "/";
        }
    }

    onErrorHandler(param){
        console.log(param);
    }

    send(){
        let mes = this.uMessage;
        let jsonMessage = {};
        let mesArray = mes.split(":");
        jsonMessage["name"] = mesArray[0];
        jsonMessage["message"] = mesArray[1];

        websock.getSocket().send(JSON.stringify(jsonMessage));
    }

    disconnect() {
        let jsonMessage = {};
        jsonMessage["disconnect"] = "";
        websock.getSocket().send(JSON.stringify(jsonMessage));
    }

    sendList(){
        let jsonMessage = {};
        jsonMessage["list"] = "";
        websock.getSocket().send(JSON.stringify(jsonMessage));
    }

    registrateUser(){
        let sessionId = getCookie("JSESSIONID");
        let jsonMessage = {};
        jsonMessage["sessionId"] = sessionId;
        websock.getSocket().send(JSON.stringify(jsonMessage));
    }

    getCookie(name) {
        let matches = document.cookie.match(new RegExp(
           "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g,
                   '\\$1') + "=([^;]*)"));
        return matches ? decodeURIComponent(matches[1]) : undefined;
    }

    broadcast() {
        let mes = this.uMessage;;
        let jsonMessage = {};
        jsonMessage["broadcast"] = mes;
        websock.getSocket().send(JSON.stringify(jsonMessage));
    }
}