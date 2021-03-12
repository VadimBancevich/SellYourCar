import React, {useEffect} from "react";
import ChatsList from "./ChatsList/ChatsList";
import s from './MessagesPage.module.css'
import MessagesBlock from "./MessagesBlock/MessagesBlock";
import {useDispatch} from "react-redux";
import {resetChats} from "../../redux/reducers/chatsReducer";
import {resetMessages} from "../../redux/reducers/messagesReducer";

const MessagesPage = (props) => {

    const dispatch = useDispatch()
    useEffect(() => {
        return () => {
            dispatch(resetChats())
            dispatch(resetMessages())
        }
    }, [])

    return (
        <div className={s.messagingBlock}>
            <div className={s.chat}>
                <ChatsList/>
                <MessagesBlock/>
            </div>
        </div>
    )
}

export default MessagesPage;
