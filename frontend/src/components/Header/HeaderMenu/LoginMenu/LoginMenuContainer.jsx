import React, {useState} from "react";
import {compose} from "redux";
import {connect} from "react-redux";
import {login} from "../../../../redux/reducers/authReducer";
import {reduxForm} from "redux-form";
import LoginMenu from "./LoginMenu";

const LoginMenuContainer = (props) => {

    const [loading, setLoading] = useState(false);
    const [errorText, setErrorText] = useState(props.errorText);
    const handleLogin = (formData) => {
        setLoading(true)
        props.login(formData.email, formData.password)
            .then((statusCode) => {
                setLoading(false)
                if (statusCode === 401) {
                    setErrorText("Bad credentials")
                } else if (Number(statusCode)) {
                    setErrorText("Server error " + statusCode)
                } else {
                    setErrorText(statusCode);
                    if (props.location) {
                        props.location.pathname === '/login' && props.history.goBack();
                    }
                }
            })
    }

    return <LoginMenu {...props} loading={loading} errorText={errorText} handleLogin={handleLogin}/>
}
export default compose(
    connect(null, {login}),
    reduxForm({form: 'loginForm'}))(LoginMenuContainer)
