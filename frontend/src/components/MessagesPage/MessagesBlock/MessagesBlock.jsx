import React from "react";
import TypeAndSendBlock from "./TypeAndSendBlock";
import s from './MessagesBlock.module.css'
import MessagesList from "./MessagesList";
import {connect} from "react-redux";
import {Link} from "react-router-dom";
import {locales} from "../../../locales";

const MessagesBlock = (props) => {

    const {chatId, carId, chatName} = props;

    return (
        <div className={s.messagesBlock}>
            {chatId ?
                <>
                    {carId && <Link style={{margin:"auto"}} to={'/cars/' + carId}>{chatName}</Link>}
                    <MessagesList/>
                    <TypeAndSendBlock/>
                </>
                : <h3 style={{margin: "auto"}}>{locales.ru.selectChat}</h3>}
        </div>
    )
}

const mapStateToProps = state => ({
    chatId: state.messages.chatId,
    carId: state.messages.carId,
    chatName: state.messages.chatId ? state.chats.chats.find(chat => chat.id === state.messages.chatId).chatName : null
})

export default connect(mapStateToProps)(MessagesBlock);