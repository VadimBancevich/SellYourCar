import React, {useEffect, useState} from "react";
import CentralCircularProgress from "../common/CentralCircularProgress";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";
import {authAPI} from "../../api/api";

const VerifyAccountPage = props => {

    const [isLoading, setIsLoading] = useState(true);
    const [verificationMessage, setVerificationMessage] = useState('');
    const [timeUntilRedirect, setTimeUntilRedirect] = useState(5);
    const token = props.match.params.token;
    useEffect(() => {
        if (token) {
            authAPI.verifyAccount(token).then(() => {
                setVerificationMessage('Ваш аккаунт был активирован')
            }).catch(() => {
                setVerificationMessage('Не удалось активировать аккаунт')
            }).finally(() => setIsLoading(false));
        }
    }, [token])

    const redirectTimeout = () => {
        setTimeout(() => {
            setTimeUntilRedirect(timeUntilRedirect - 1)
        }, 1000)
        if (timeUntilRedirect === 0) {
            props.history.push('/');
            document.location.reload();
        }
    }

    return (
        <div>
            {isLoading && <CentralCircularProgress/>}
            <Dialog open={!isLoading}>
                <DialogContent>
                    <DialogContentText>
                        {verificationMessage}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => props.history.push('/')}>
                        {'На главную (' + timeUntilRedirect + ')'}
                    </Button>
                    {!isLoading && redirectTimeout()}
                </DialogActions>
            </Dialog>
        </div>
    )
}

export {VerifyAccountPage};