import React, {useState} from "react";
import {messagingApi} from "../../../api/messagingApi";
import {connect} from "react-redux";
import s from './TypeAndSendBlock.module.css'
import Button from "@material-ui/core/Button";
import SendIcon from '@material-ui/icons/Send';
import {locales} from "../../../locales";

const TypeAndSendBlock = (props) => {

    const [content, setContent] = useState('');

    const handleSendMessage = (event) => {
        if (content) {
            messagingApi.sendMessage(props.openedChatId, content)
            setContent('');
        }
    }
    const handleChange = (event) => {
        setContent(event.target.value);
    }

    const onEnterDown = (event) => {
        if (event.keyCode === 13){
            event.preventDefault();
            handleSendMessage();
        }
    }

    return (
        <div className={s.block}>
            <textarea className={s.textarea} onKeyDown={onEnterDown} value={content} onChange={handleChange}
                      placeholder={locales.ru.typeMessage}/>
            <div className={s.sendButton} onClick={handleSendMessage}>
                <Button variant="contained" color="secondary" endIcon={<SendIcon/>}>{locales.ru.send}</Button>
            </div>
        </div>
    )
}

const mapStateToProps = (state) => ({
    openedChatId: state.messages.chatId
})

export default connect(mapStateToProps)(TypeAndSendBlock);