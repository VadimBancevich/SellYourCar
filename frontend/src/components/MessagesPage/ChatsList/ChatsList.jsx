import React, {useEffect} from "react";
import Chat from "./Chat";
import {connect} from "react-redux";
import {getChats} from "../../../redux/reducers/chatsReducer";
import CircularProgress from "@material-ui/core/CircularProgress";
import s from './ChatsList.module.css'
import commonCss from '../../common/common.module.css'

const ChatsList = (props) => {

    const {isLoading, hasNext, getChats, chats} = props;

    useEffect(() => getChats(), []);

    const handleScroll = (event) => {
        let scrollHeight = event.target.scrollHeight;
        let scrollTop = event.target.scrollTop;
        if (!isLoading && hasNext && scrollTop / scrollHeight > 0.2) {
            getChats(event.target.childElementCount);
        }
    }

    return (
        <div onScroll={handleScroll} className={s.chatsList + ' ' + commonCss.scrollBar}>
            {isLoading ? <CircularProgress style={{margin: 'auto'}}/> : chats.map(chat => <Chat
                key={chat.id} {...chat}/>)}
            {!isLoading && chats.length === 0 && <span style={{margin: "10px auto"}}>No chats</span>}
        </div>
    )
}

const mapStateToProps = (state) => ({
    isLoading: state.chats.isLoading,
    hasNext: state.chats.hasNext,
    chats: state.chats.chats
})

export default connect(mapStateToProps, {getChats})(ChatsList);