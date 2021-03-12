import * as SockJS from "sockjs-client";
import {Stomp} from "@stomp/stompjs/esm6/compatibility/stomp";
import {host} from "./backendInfo";

let client;

export const messagingApi = {
    connect(userId, callBack) {
        client = Stomp.over(() => new SockJS(host + '/ws'))
        client.connect({}, (frame) => {
            client.subscribe('user/' + userId + '/messages', callBack)
        });
    },
    sendMessage(chatId, content) {
        client.send('/api/v1/app/chat', {},
            JSON.stringify({content, chatId}))
    },
    readMessage(messageId) {
        client.send('/api/v1/app/read', {}, messageId)
    },
    disconnect() {
        client.disconnect();
    }
}