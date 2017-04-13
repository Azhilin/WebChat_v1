import { Injectable } from '@angular/core';

import * as SockJS from 'sockjs-client';
import BaseEvent = __SockJSClient.BaseEvent;
import SockJSClass = __SockJSClient.SockJSClass;

@Injectable()
export class WebSock{
    sockJS: SockJSClass;

    constructor(){
        this.sockJS = new SockJSClass('/sock');
    }

    getSocket(){
        return this.sockJS;
    }

}