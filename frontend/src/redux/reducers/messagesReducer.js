import {messagesApi} from "../../api/api";

const INIT_MESSAGES = "INIT_MESSAGES";
const ADD_MESSAGES = "ADD_MESSAGES";
const IS_MESSAGES_LOADING = "IS_MESSAGES_LOADING";
const SET_OPENED_CHAT_ID = "SET_OPENED_CHAT_ID";
const READ_MESSAGE = "READ_MESSAGE";
const ADD_MESSAGE_IN_START = "ADD_MESSAGE_IN_START";
const RESET_MESSAGES = "RESET_MESSAGES"


let initialState = {
    isLoading: true,
    hasNext: true,
    chatId: null,
    carId: null,
    messages: [{
        id: 0,
        content: "message text",
        fromUserId: 0,
        fromUserName: "fromUserName",
        toUserId: 1,
        toUserName: "toUserName",
        chatId: 2,
        dateTime: "2020-10-16T11:04:26",
        read: false
    }]
}

const messagesReducer = (state = initialState, action) => {
    switch (action.type) {
        case INIT_MESSAGES:
            if (action.chatId === state.chatId && state.messages.length === 0) {
                return {
                    ...state,
                    isLoading: false,
                    hasNext: action.messages.length === 20,
                    messages: action.messages
                }
            } else return state
        case ADD_MESSAGES:
            return {
                ...state,
                isLoading: false,
                hasNext: action.hasNext,
                messages: state.messages.concat(action.messages === null ? [] : action.messages)
            }
        case ADD_MESSAGE_IN_START:
            state.messages.unshift(action.message)
            return {
                ...state,
                messages: state.messages.map(message => message)
            }
        case IS_MESSAGES_LOADING:
            return {
                ...state,
                isLoading: action.isLoading
            }
        case SET_OPENED_CHAT_ID:
            return {
                ...state,
                chatId: action.chatId,
                carId: action.carId,
                isLoading: true,
                hasNext: true,
                messages: []
            }
        case READ_MESSAGE:
            return {
                ...state,
                messages: state.messages.map(message => {
                    if (message.id === action.messageId) {
                        return {
                            ...message,
                            read: true
                        }
                    }
                    return message;
                })
            }
        case RESET_MESSAGES:
            return initialState;
        default:
            return state;
    }
}

const initMessages = (messages, chatId) => {
    return {type: INIT_MESSAGES, messages, chatId}
}
const addMessages = (hasNext, messages) => ({type: ADD_MESSAGES, hasNext, messages})
export const setLoading = (isLoading) => ({type: IS_MESSAGES_LOADING, isLoading})
export const setOpenedChatId = (chatId, carId) => ({type: SET_OPENED_CHAT_ID, chatId, carId})
export const readMessage = (messageId) => ({type: READ_MESSAGE, messageId})
export const addMessageInStart = (message) => ({type: ADD_MESSAGE_IN_START, message})
export const resetMessages = () => ({type: RESET_MESSAGES})

export const getMessages = (chatId, skip, size) => (dispatch) => {
    dispatch(setLoading(true));
    messagesApi.getMessages(chatId, skip, size).then(response => {
        let data = response.data;
        dispatch(addMessages(data.length > 0, data));
    }).catch(() => dispatch(setLoading(false)))
}

export const initMessagesThunk = (chatId) => (dispatch) => {
    dispatch(setLoading(true));
    messagesApi.getMessages(chatId).then(response => {
        dispatch(initMessages(response.data, chatId));
    }).catch((reason) => {
        console.log(reason)
        dispatch(setLoading(false))
    });
}

export default messagesReducer;