import React from "react";
import {connect} from "react-redux";
import {initMessagesThunk, setOpenedChatId} from "../../../redux/reducers/messagesReducer";
import s from './Chat.module.css'

const Chat = (props) => {
    const {id, chatName, imageUrl, lastMessageContent, lastMessageFromUserName, unreadCount, openedChatId, carId} = props;

    const handleClick = (e) => {
        if (Number(id) !== Number(openedChatId)) {
            props.setOpenedChatId(id, carId);
        }
    }

    return (
        <div className={s.chat + ' ' + (openedChatId === id ? s.chatSelected : unreadCount > 0 ? s.chatHasUnread : '')}
             onClick={handleClick}>
            <img src={imageUrl} className={s.chatImage}/>
            <div className={s.chatInfo}>
                <div className={s.chatName + ' ' + s.hiddenText}>{chatName}</div>
                <div className={s.chatLastMessagesBlock}>
                    <div className={s.chatMessage}>
                        <div className={s.senderName + ' ' + s.hiddenText}>{lastMessageFromUserName}</div>
                        <div className={s.hiddenText}>{lastMessageContent}</div>
                    </div>
                    {unreadCount > 0 && <div className={s.unreadCount}>{unreadCount}</div>}
                </div>
            </div>
        </div>
    )
}

const mapStateToProps = (state) => ({
    openedChatId: state.messages.chatId
})

export default connect(mapStateToProps, {initMessagesThunk, setOpenedChatId})(Chat);