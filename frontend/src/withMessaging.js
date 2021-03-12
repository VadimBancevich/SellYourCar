import React, {useEffect} from "react";
import {useSelector, useStore} from "react-redux";
import {addMessageInStart, readMessage} from "./redux/reducers/messagesReducer";
import {
    changeLastMessage,
    decrementUnreadCount,
    incrementUnreadCount,
    prependChatThunk
} from "./redux/reducers/chatsReducer";
import {messagingApi as messagesApi} from "./api/messagingApi";

const withMessaging = Component => props => {
    const store = useStore();
    const userId = useSelector(state => state.auth.userId);

    useEffect(() => {
            if (userId) {
                messagesApi.connect(userId, (message) => {
                        let messagesState = store.getState().messages;
                        let chatsState = store.getState().chats;
                        let userId = store.getState().auth.userId;
                        let dispatch = store.dispatch;
                        let messageJson = JSON.parse(message.body);

                        // read message if update header presented
                        if (message.headers.updated && messagesState.chatId === messageJson.chatId) {
                            dispatch(readMessage(messageJson.id))
                            if (messageJson.toUserId === userId) {
                                dispatch(decrementUnreadCount(messageJson.chatId))
                            }
                        } else {
                            // add message to chat
                            if (messagesState.chatId === messageJson.chatId) {
                                dispatch(addMessageInStart(messageJson))
                            }
                            // change last message in chat presentation
                            if (chatsState.chats.find(chat => chat.id === messageJson.chatId)) {
                                dispatch(changeLastMessage(messageJson));
                                if (messageJson.toUserId === userId) {
                                    dispatch(incrementUnreadCount(messageJson.chatId))
                                }
                            } else {
                                dispatch(prependChatThunk(messageJson.chatId));
                            }
                        }
                    }
                );
            }
        },
        [userId]
    )
    return <Component/>
}

export default withMessaging;