import {chatsApi, messagesApi} from "../../api/api";
import {defImg} from "./imagesReducer";

const INIT_CHATS = "INIT_CHATS";
const ADD_CHATS = "ADD_CHATS";
const PREPEND_CHAT = "PREPEND_CHAT";
const IS_CHATS_LOADING = "IS_CHATS_LOADING";
const HAS_CHATS_NEXT = "HAS_CHATS_NEXT";
const DECREMENT_UNREAD_COUNT_BY_CHAT_ID = "DECREMENT_UNREAD_COUNT_BY_CHAT_ID";
const INCREMENT_UNREAD_COUNT_BY_CHAT_ID = "INCREMENT_UNREAD_COUNT_BY_CHAT_ID";
const CHANGE_LAST_MESSAGE = "CHANGE_LAST_MESSAGE";
const RESET_CHATS = "RESET_CHATS";

let initialState = {
    isLoading: true,
    isConnected: false,
    hasNext: true,
    chats: [{
        id: '0',
        chatName: 'Car',
        imageUrl: 'http...',
        lastMessageContent: "eve",
        lastMessageFromUserName: "admin",
        unreadCount: 0,
        carId: 0
    }]
}

const chatsReducer = (state = initialState, action) => {
    if (action.chats) {
        action.chats.forEach(chat => {
            if (!chat.imageUrl) {
                chat.imageUrl = defImg;
            }
        });
    } else if (action.chat) {
        !action.chat.imageUrl && (action.chat.imageUrl = defImg);
    }
    switch (action.type) {
        case INIT_CHATS :
            return {
                ...state,
                chats: action.chats
            }
        case ADD_CHATS:
            return {
                ...state,
                chats: state.chats.concat(action.chats)
            }
        case PREPEND_CHAT:
            return {
                ...state,
                chats: [action.chat].concat(state.chats)
            }
        case IS_CHATS_LOADING:
            return {
                ...state,
                isLoading: action.isLoading
            }
        case HAS_CHATS_NEXT :
            return {
                ...state,
                hasNext: action.hasNext
            }
        case DECREMENT_UNREAD_COUNT_BY_CHAT_ID :
            return {
                ...state,
                chats: state.chats.map(chat => {
                    if (chat.id === action.id) {
                        chat.unreadCount -= 1;
                    }
                    return chat
                })
            }
        case INCREMENT_UNREAD_COUNT_BY_CHAT_ID:
            return {
                ...state,
                chats: state.chats.map(chat => {
                    if (chat.id === action.chatId) {
                        chat.unreadCount += 1;
                    }
                    return chat;
                })
            }
        case CHANGE_LAST_MESSAGE:
            return {
                ...state,
                chats: state.chats.map(chat => {
                    if (chat.id === action.message.chatId) {
                        chat.lastMessageContent = action.message.content;
                        chat.lastMessageFromUserName = action.message.fromUserName;
                    }
                    return chat;
                })
            }
        case RESET_CHATS:
            return initialState;
        default:
            return state;
    }
}

export const initChats = (chats) => ({type: INIT_CHATS, chats})
const addChats = (chats) => ({type: ADD_CHATS, chats})
const setLoading = (isLoading) => ({type: IS_CHATS_LOADING, isLoading})
const setHasNext = (hasNext) => ({type: HAS_CHATS_NEXT, hasNext});
export const decrementUnreadCount = (id) => ({type: DECREMENT_UNREAD_COUNT_BY_CHAT_ID, id})
export const incrementUnreadCount = (chatId) => ({type: INCREMENT_UNREAD_COUNT_BY_CHAT_ID, chatId})
export const prependChat = (chat) => ({type: PREPEND_CHAT, chat})
export const changeLastMessage = (message) => ({type: CHANGE_LAST_MESSAGE, message})
export const resetChats = () => ({type: RESET_CHATS})

export const getChats = (skip, size) => (dispatch) => {
    dispatch(setLoading(true));
    messagesApi.getChats(skip, size).then(response => {
        let data = response.data;

        if (skip || size) {
            if (data.length === 0) {
                dispatch(setHasNext(false))
            } else {
                dispatch(addChats(data))
            }
        } else {
            dispatch(initChats(data))
        }
        dispatch(setLoading(false));
    }).catch(() => dispatch(setLoading(false)));
}

export const prependChatThunk = chatId => dispatch => {
    chatsApi.getChatById(chatId).then(response => {
        const data = response.data;
        if (data) {
            dispatch(prependChat(data));
        }
    })
}

export default chatsReducer;
