import { Injectable } from '@angular/core';

import * as SockJS from 'sockjs-client';
//import BaseEvent = __SockJSClient.BaseEvent;
//import SockJSClass = __SockJSClient.SockJSClass;

var SockJS = require('sockjs-client');

@Injectable()
export class WebSock{
    sockJS: SockJS = new SockJS('/sock');

    constructor(){
        console.log("WebSock constructor!!! sockJS: " + (this.sockJS == undefined));
    }

    public getSocket(){
        return this.sockJS;
    }

}