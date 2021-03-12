import React, {useEffect, useRef, useState} from "react";
import {connect} from "react-redux";
import {getMessages, initMessagesThunk} from "../../../redux/reducers/messagesReducer";
import Message from "./Message";
import CircularProgress from "@material-ui/core/CircularProgress";
import s from './MessagesList.module.css'
import commonCss from '../../common/common.module.css'

const MessagesList = (props) => {

    const {messages, hasNext, isLoading, chatId} = props;

    const ref = useRef(null);

    const [isInitialized, setIsInitialized] = useState(false);

    useEffect(() => {
        if (chatId) {
            props.initMessagesThunk(chatId);
        }
        setIsInitialized(false);
    }, [chatId])

    useEffect(() => {
        if (ref.current) {
            ref.current.scrollTop = ref.current.scrollHeight;
        }
        if (messages.length > 0 && !isInitialized) {
            setIsInitialized(true);
        }
    }, [messages.length > 0])

    useEffect(() => {
        if (ref.current) {
            let top = ref.current.scrollTop + ref.current.offsetHeight;
            if ((ref.current.scrollHeight - top) <= 100) {
                ref.current.scrollTop = ref.current.scrollHeight;
            }
        }
    }, [messages.length])

    const handleScroll = (event) => {
        if (event.target.scrollTop < 10 && hasNext && !isLoading) {
            props.getMessages(chatId, event.target.childElementCount);
        }
        if (isLoading && event.target.scrollTop < 10) {
            event.target.scrollTop = 10;
        }
    }
    return (
        <div ref={ref} style={isInitialized ? {scrollBehavior: "smooth"} : {}} onScroll={handleScroll}
             className={s.messagesListBlock + ' ' + commonCss.scrollBar}>
            {isLoading && <CircularProgress style={{margin: '10px auto'}}/>}
            {!hasNext && <div className={s.chatStart}>Chat start</div>}
            {messages.length > 0 ? messages.map(message => <Message key={message.id} {...message}/>).reverse() :
                isLoading ? '' :
                    <h4>No messages</h4>}

        </div>
    )
}

const mapStateToProps = (state) => ({
    hasNext: state.messages.hasNext,
    isLoading: state.messages.isLoading,
    chatId: state.messages.chatId,
    messages: state.messages.messages,
})

export default connect(mapStateToProps, {getMessages, initMessagesThunk})(MessagesList);




