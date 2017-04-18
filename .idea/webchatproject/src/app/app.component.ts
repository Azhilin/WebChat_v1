import {Component}from '@angular/core';
import {Response}from '@angular/http';
import {Http, Headers} from '@angular/http';
import {Injectable} from '@angular/core';
import {WebSock} from './websock';
import {Cookie} from './cookies';
import * as SockJS from 'sockjs-client';
//import BaseEvent = __SockJSClient.BaseEvent;
//import SockJSClass = __SockJSClient.SockJSClass;

var SockJS = require('sockjs-client');

@Component({
  selector: 'my-chat',
  template:
  `
    <table>
        <tr>
        <td>
            <form>
                <textarea>{{messages}}</textarea>
                <input type="text" [textContent] = "uMessage"/>
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

  `,
providers: [WebSock]
})

@Injectable()
export class AppComponent {
    flag: any = undefined;
    items: string[] = undefined;
    messages: string = undefined;
    uMessage: string = undefined;
    sockJS: SockJS = new SockJS('/sock');

    constructor(private websock: WebSock){
        console.log("AppComponent constructor!!! this.websock:" + (this.websock == undefined));
    }

    ngOnInit(){
        console.log("ngOnInit method!!! this.websock: " + (this.websock == undefined));
        this.sockJS.onmessage = this.onMessageHandler;
        this.sockJS.onerror = this.onErrorHandler;
        this.sockJS.onopen = this.onOpenHandler;
        this.sockJS.onclose = this.onCloseHandler;
    }

    onOpenHandler(){
        let sessionId = Cookie.getCookie("JSESSIONID");
        let jsonMessage = {};
        jsonMessage["sessionId"] = sessionId;
        this.sockJS.send(JSON.stringify(jsonMessage));
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

        this.sockJS.send(JSON.stringify(jsonMessage));
    }

    disconnect() {
        let jsonMessage = {};
        jsonMessage["disconnect"] = "";
        this.sockJS.send(JSON.stringify(jsonMessage));
    }

    sendList(){
        let jsonMessage = {};
        jsonMessage["list"] = "";
        this.sockJS.send(JSON.stringify(jsonMessage));
    }

/**    registrateUser(){
        console.log("This is registrateUser method!!!");
        let sessionId = this.getCookie("JSESSIONID");
        let jsonMessage = {};
        jsonMessage["sessionId"] = sessionId;
        this.sockJS.send(JSON.stringify(jsonMessage));
    }*/

    broadcast() {
        let mes = this.uMessage;;
        let jsonMessage = {};
        jsonMessage["broadcast"] = mes;
        this.sockJS.send(JSON.stringify(jsonMessage));
    }
}