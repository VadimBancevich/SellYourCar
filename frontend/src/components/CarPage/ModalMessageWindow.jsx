import React, {useState} from "react";
import Paper from "@material-ui/core/Paper";
import {messagesApi} from "../../api/api";
import {locales} from "../../locales";
import Button from "@material-ui/core/Button";

const ModalMessageWindow = (props) => {
    const {carId, onClose} = props;
    const [messageText, setMessageText] = useState()

    const handleSendMessage = () => {
        messagesApi.sendMessage(carId, messageText)
        onClose();
    }
    const handleChangeText = (event) => {
        setMessageText(event.target.value);
    }

    return (
        <Paper style={{padding: "20px 50px", display: "flex", flexDirection: "column"}}>
            <h2>Введите собщение</h2>
            <textarea style={{height: "120px", marginBottom: "15px", resize: "none"}} value={messageText}
                      onChange={handleChangeText}/>
            <Button variant={"contained"} onClick={handleSendMessage}>{locales.ru.send}</Button>
        </Paper>
    )
}

export default ModalMessageWindow;