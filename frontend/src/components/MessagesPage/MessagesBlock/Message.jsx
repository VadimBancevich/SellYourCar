import React from "react";
import {connect} from "react-redux";
import {decrementUnreadCount} from "../../../redux/reducers/chatsReducer";
import {messagingApi} from "../../../api/messagingApi";
import s from './Message.module.css'

const Message = (props) => {
    const {id, content, fromUserId, fromUserName, toUserId, dateTime, read, userId} = props;

    const handleMouseEnter = () => {
        if (!read && toUserId === userId) {
            messagingApi.readMessage(id);
        }
    }

    return (
        <div className={s.messageContainer + ' ' + (read ? '' : s.unreadMessage)} onMouseEnter={handleMouseEnter}>
            <div className={s.message + ' ' + (userId === fromUserId ? s.fromMeMessage : s.toMeMessage)}>
                <div className={s.senderName}>{fromUserName}</div>
                <div className={s.content}>{content}</div>
                <div className={s.dateTime}>{new Date(dateTime + 'z').toLocaleString()}</div>
            </div>
        </div>
    )
}

const mapStateToProps = (state) => ({
    userId: state.auth.userId
})

export default connect(mapStateToProps, {decrementUnreadCount})(Message);